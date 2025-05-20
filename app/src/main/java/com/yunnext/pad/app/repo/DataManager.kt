package com.yunnext.pad.app.repo

import com.yunnext.pad.app.BuildConfig
import com.yunnext.pad.app.repo.uart.i
import com.yunnext.pad.app.repo.uart.ChuSHuiType
import com.yunnext.pad.app.repo.uart.UartDown
import com.yunnext.pad.app.repo.uart.UartError
import com.yunnext.pad.app.repo.uart.UartManager
import com.yunnext.pad.app.repo.uart.UartUp
import com.yunnext.pad.app.repo.uart.encode
import com.yunnext.pad.app.repo.uart.toTimestamps
import com.yunnext.pad.app.ui.screen.vo.DebugCase01Vo
import com.yunnext.pad.app.ui.screen.vo.DebugCase02Vo
import com.yunnext.pad.app.ui.screen.vo.DebugCase03Vo
import com.yunnext.pad.app.ui.screen.vo.DebugCase04Vo
import com.yunnext.pad.app.ui.screen.vo.DebugVo
import com.yunnext.pad.app.ui.screen.vo.Level
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlin.random.Random

data class UartData(

    val serviceDays: Int,
    val savedBottles: Int,
    val currentTemperature: Int,
    val dateTimeInfo: Long,
    val wifi: Level,
    val jiaReStatus: Boolean,
    val zhiShuiStatus: Boolean,
    val queShuiStatus: Boolean,
    val jieNengStatus: Boolean,
    val huanXinStatus: Boolean,
    val shaJunStatus: Boolean,
    val yinYongStatus: Boolean,
    val error: UartError
) {
    companion object {
        val DEFAULT = UartData(
            serviceDays = 0,
            savedBottles = 0,
            currentTemperature = 0,
            dateTimeInfo = 0,
            jiaReStatus = false,
            zhiShuiStatus = false,
            queShuiStatus = false,
            huanXinStatus = false,
            shaJunStatus = false,
            yinYongStatus = false,
            jieNengStatus = false,
            wifi = Level.NaN, error = UartError.Normal
        )
    }
}


@OptIn(ExperimentalStdlibApi::class)
object DataManager {
    private const val TAG = "DataManager"

    private val coroutineScope: CoroutineScope =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + CoroutineName(TAG))

    //<editor-fold desc="Uart">
    private var _uartManager: UartManager? = null

    private val _serviceDaysFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _savedBottlesFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _quShuiCountFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _quShuiVolumeFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _currentTemperatureFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _dateTimeInfoFlow: MutableStateFlow<Long> = MutableStateFlow(0L)
    private val _wifiFlow: MutableStateFlow<Level> = MutableStateFlow(Level.NaN)
    private val _jiaReStatusFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _zhiShuiStatusFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _queShuiStatusFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _jieNengStatusFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _huanXinStatusFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _shaJunStatusFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _yinYongStatusFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _errorFlow: MutableStateFlow<UartError> = MutableStateFlow(UartError.Normal)
    private val _uartUpRawFlow: MutableSharedFlow<String> = MutableSharedFlow()
    private val _uartDownRawFlow: MutableSharedFlow<String> = MutableSharedFlow()

    val serviceDays = _serviceDaysFlow.asStateFlow()
    val savedBottles = _savedBottlesFlow.asStateFlow()
    val quShuiCountFlow = _quShuiCountFlow.asStateFlow()
    val quShuiVolumeFlow = _quShuiVolumeFlow.asStateFlow()
    val currentTemperature = _currentTemperatureFlow.asStateFlow()
    val dateTimeInfo = _dateTimeInfoFlow.asStateFlow()
    val wifi = _wifiFlow.asStateFlow()
    val jiaReStatus = _jiaReStatusFlow.asStateFlow()
    val zhiShuiStatus = _zhiShuiStatusFlow.asStateFlow()
    val queShuiStatus = _queShuiStatusFlow.asStateFlow()
    val jieNengStatus = _jieNengStatusFlow.asStateFlow()
    val huanXinStatus = _huanXinStatusFlow.asStateFlow()
    val shaJunStatus = _shaJunStatusFlow.asStateFlow()
    val yinYongStatus = _yinYongStatusFlow.asStateFlow()
    val error = _errorFlow.asStateFlow()
    val uartUpRaw = _uartUpRawFlow.asSharedFlow()
    val uartDownRaw = _uartDownRawFlow.asSharedFlow()

    private val _uartChannel = Channel<ByteArray>()

    init {
        coroutineScope.launch {
            _uartChannel.receiveAsFlow().collect { data ->
                if (BuildConfig.DEBUG){
                    _uartUpRawFlow.emit(data.toHexString())
                }

                parseData(data)
            }
        }
    }
    //</editor-fold>


    @OptIn(ExperimentalStdlibApi::class)
    fun debug(debug: DebugVo) {
        when (debug) {
            DebugCase03Vo -> doWrite("AA550811011255BB".hexToByteArray())
            DebugCase04Vo -> doWrite("AA550811021355BB".hexToByteArray())
            DebugCase01Vo -> doWrite("AA550802010355BB".hexToByteArray())
            DebugCase02Vo -> doWrite("AA550802000255BB".hexToByteArray())
        }
    }


    @OptIn(ExperimentalStdlibApi::class)
    internal fun doWrite(data: ByteArray){
        val manager = _uartManager?:return
        manager.write(data = data)
        coroutineScope.launch {
            _uartDownRawFlow.emit( data.toHexString())
        }

    }

    private fun writeUartForGetAll() {
        doWrite(UartDown.GetAllDown.encode())
    }

    private fun combineByteArrays(arrays: Array<ByteArray>): ByteArray {
        return arrays.fold(ByteArray(0)) { acc, byteArray ->
            acc + byteArray
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun init() {
        val uartManager = UartManager()
        val result = uartManager.start { data,_ ->
            if (data.isNotEmpty()) {
                data.forEach { item ->
                    _uartChannel.trySend(item)
                }

                coroutineScope.launch {
                    _uartUpRawFlow.emit(combineByteArrays(data).toHexString())
                }
            }
        }
        if (!result) {
            println("uart open fail ！！！")
            simpleByteArray()
            return
        }
        println("uart open success ！！!")
        _uartManager = uartManager

        coroutineScope.launch {
            delay(1000)
            writeUartForGetAll()
        }

//        coroutineScope.launch {
//            while (isActive){
//                delay(5000)
//                val data  = UartUp.SavedBottlesUp(Random.nextInt(9999)).encode()
//                uartManager.write(data)
//            }
//        }

//         simpleMock()
//         simpleByteArray()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun parseData(data: ByteArray) {
        try {
            i("[$TAG]<parseData>${data.toHexString()}")
            fun handle(uartUp: UartUp) {
                when (uartUp) {
                    is UartUp.ChildLockUp -> {}
                    is UartUp.ChuShuiUp -> {
                        _queShuiStatusFlow.value = uartUp.value != ChuSHuiType.NaN
                    }

                    is UartUp.ErrorUp -> {
                        _errorFlow.value = uartUp.value
                    }

                    is UartUp.FilterUp -> {

                    }

                    is UartUp.JiaReUp -> {
                        _jiaReStatusFlow.value = uartUp.value
                    }

                    is UartUp.JieNengUp -> {
                        _jieNengStatusFlow.value = uartUp.value
                    }

                    is UartUp.OnOffUp -> {

                    }

                    is UartUp.QueShuiUp -> {
                        _queShuiStatusFlow.value = uartUp.value
                    }

                    is UartUp.SavedBottlesUp -> {
                        _savedBottlesFlow.value = uartUp.value
                    }

                    is UartUp.ServiceDaysUp -> {
                        _serviceDaysFlow.value = uartUp.value
                    }

                    is UartUp.ShaJunUp -> {
                        _shaJunStatusFlow.value = uartUp.value
                    }

                    is UartUp.TemperatureUp -> {
                        _currentTemperatureFlow.value = uartUp.value
                    }

                    is UartUp.WifiUp -> {
                        _wifiFlow.value = uartUp.value
                    }

                    is UartUp.ZhiShuiUp -> {
                        _zhiShuiStatusFlow.value = uartUp.value
                    }

                    is UartUp.YinYongUp -> {
                        _yinYongStatusFlow.value = uartUp.value
                    }

                    is UartUp.DateUp -> {
                        _dateTimeInfoFlow.value = uartUp.toTimestamps()
                    }

                    is UartUp.QuShuiCountUp -> {
                        _quShuiCountFlow.value = uartUp.value
                    }

                    is UartUp.QuShuiVolumeUp -> {
                        _quShuiVolumeFlow.value = uartUp.value
                    }

                    is UartUp.GetAllUp -> {
                        uartUp.value.forEach { item ->
                            if (item !is UartUp.GetAllUp) {
                                handle(item)
                            }
                        }
                    }
                }
            }
            handle(UartUp.decode(data = data))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun cancel() {
        coroutineScope.cancel()
        _uartManager?.stop()
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun simpleByteArray() {
        coroutineScope.launch {

            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.SavedBottlesUp(Random.nextInt(9999)).encode()
                    //aa55 0b 09 bd100000 d6 55bb
                    println("SavedBottlesUp:${data.toHexString()}")
                    _uartChannel.trySend(data)
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.ServiceDaysUp(Random.nextInt(9999)).encode()
                    _uartChannel.trySend(data)
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.TemperatureUp(Random.nextInt(100)).encode()
                    _uartChannel.trySend(data)
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._dateTimeInfoFlow.value = (Clock.System.now().toEpochMilliseconds())
                    // todo
                }
            }

            launch {
                while (isActive) {
                    delay(500)
                    val data = UartUp.WifiUp(Level.random()).encode()

                    _uartChannel.trySend(data)
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.ZhiShuiUp(Random.nextBoolean()).encode()

                    _uartChannel.trySend(data)
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.YinYongUp(Random.nextBoolean()).encode()

                    _uartChannel.trySend(data)
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.JiaReUp(Random.nextBoolean()).encode()
                    _uartChannel.trySend(data)
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.ZhiShuiUp(Random.nextBoolean()).encode()
                    _uartChannel.trySend(data)
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.QueShuiUp(Random.nextBoolean()).encode()
                    _uartChannel.trySend(data)
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.JieNengUp(Random.nextBoolean()).encode()
                    _uartChannel.trySend(data)

                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    // todo 换芯
                    DataManager._huanXinStatusFlow.value = (Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    val data = UartUp.ShaJunUp(Random.nextBoolean()).encode()
                    _uartChannel.trySend(data)
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    // todo 饮用
                    DataManager._yinYongStatusFlow.value = (Random.nextBoolean())
                }
            }
        }
    }

    private fun simpleMock() {
        coroutineScope.launch {
            launch {
                while (isActive) {
                    delay(1000)
                    _serviceDaysFlow.value = (Random.nextInt(9999))
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    _savedBottlesFlow.value = (Random.nextInt(9999))
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._currentTemperatureFlow.value = (Random.nextInt(100))
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._dateTimeInfoFlow.value = (Clock.System.now().toEpochMilliseconds())
                }
            }

            launch {
                while (isActive) {
                    delay(2000)
                    DataManager._wifiFlow.value = (Level.random())
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._jiaReStatusFlow.value = (Random.nextBoolean())
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._zhiShuiStatusFlow.value = (Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._queShuiStatusFlow.value = (Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._jieNengStatusFlow.value = (Random.nextBoolean())

                }
            }
            launch {
                while (isActive) {
                    delay(1000)

                    DataManager._huanXinStatusFlow.value = (Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._shaJunStatusFlow.value = (Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    DataManager._yinYongStatusFlow.value = (Random.nextBoolean())
                }
            }
        }
    }
}