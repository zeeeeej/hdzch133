package com.yunnext.pad.app.repo

import com.yunnext.pad.app.repo.uart.ChuSHuiType
import com.yunnext.pad.app.repo.uart.UartError
import com.yunnext.pad.app.repo.uart.UartManager
import com.yunnext.pad.app.repo.uart.UartUp
import com.yunnext.pad.app.ui.screen.vo.Level
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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


object DataManager {
    private const val TAG = "DataManager"

    private val coroutineScope: CoroutineScope =
        CoroutineScope(Dispatchers.Main.immediate + SupervisorJob() + CoroutineName(TAG))
    private var _uartManager: UartManager? = null

    private val _serviceDaysFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    private val _savedBottlesFlow: MutableStateFlow<Int> = MutableStateFlow(0)
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

    val serviceDays = _serviceDaysFlow.asStateFlow()
    val savedBottles = _savedBottlesFlow.asStateFlow()
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

    fun init() {
        val uartManager = UartManager()
        uartManager.start { data ->
            try {
                when (val uartUp = UartUp.parse(data = data)) {
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

                    UartUp.NaN -> {}
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
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _uartManager = uartManager

        mock()
    }

    fun cancel() {
        coroutineScope.cancel()
        _uartManager?.stop()
    }

    private fun mock() {
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