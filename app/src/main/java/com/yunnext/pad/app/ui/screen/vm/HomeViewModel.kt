package com.yunnext.pad.app.ui.screen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunnext.pad.app.repo.DataManager
import com.yunnext.pad.app.repo.uart.i
import com.yunnext.pad.app.ui.screen.vo.TempInfoVo
import com.yunnext.pad.app.ui.screen.vo.DateTimeInfoVo
import com.yunnext.pad.app.ui.screen.vo.GServiceInfoVo
import com.yunnext.pad.app.ui.screen.vo.Level
import com.yunnext.pad.app.ui.screen.vo.BottlesInfoVo
import com.yunnext.pad.app.ui.screen.vo.DebugValue
import com.yunnext.pad.app.ui.screen.vo.DebugVo
import com.yunnext.pad.app.ui.screen.vo.QuShuiCountInfoVo
import com.yunnext.pad.app.ui.screen.vo.QuShuiVolumeInfoVo
import com.yunnext.pad.app.ui.screen.vo.Status
import com.yunnext.pad.app.ui.screen.vo.Status.*
import com.yunnext.pad.app.ui.screen.vo.StatusVo
import com.yunnext.pad.app.ui.screen.vo.UartDown
import com.yunnext.pad.app.ui.screen.vo.UartUp
import com.yunnext.pad.app.ui.screen.vo.UartVo
import com.yunnext.pad.app.ui.screen.vo.WifiInfoVo
import com.yunnext.pad.app.ui.screen.vo.debugList
import com.yunnext.pad.app.ui.screen.vo.res
import com.yunnext.pad.app.ui.screen.vo.text
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class HomeState(
    val gServiceInfo: GServiceInfoVo,
    val bottlesInfo: BottlesInfoVo,
    val quShuiCount: QuShuiCountInfoVo,
    val quShuiVolume: QuShuiVolumeInfoVo,
    val tempInfo: TempInfoVo,
    val dateTimeInfo: DateTimeInfoVo,
    val statusListInfo: List<StatusVo>,
    val wifiInfo: WifiInfoVo,
    val debug: List<DebugVo>,
    val inputList: List<UartVo>,
    val raw: String,
) {
    companion object {
        internal val DEFAULT = HomeState(
            gServiceInfo = GServiceInfoVo(0),
            bottlesInfo = BottlesInfoVo(0, "0"),
            tempInfo = TempInfoVo(0),
            quShuiCount = QuShuiCountInfoVo(0, "0"),
            quShuiVolume = QuShuiVolumeInfoVo(0, "0"),
            dateTimeInfo = DateTimeInfoVo("2000", "1", "1", "00", "00"),
            statusListInfo = emptyList(),
            wifiInfo = WifiInfoVo(Level.NaN), debug = emptyList(), inputList = emptyList(), raw = ""
        )
    }
}

private typealias StatusValue = Pair<Status, Boolean>

class HomeViewModel : ViewModel() {

    private val serviceDays: MutableStateFlow<Int> = MutableStateFlow(0)
    private val savedBottles: MutableStateFlow<Int> = MutableStateFlow(0)
    private val quShuiCount: MutableStateFlow<Int> = MutableStateFlow(0)
    private val quShuiVolume: MutableStateFlow<Int> = MutableStateFlow(0)
    private val currentTemperature: MutableStateFlow<Int> = MutableStateFlow(0)
    private val dateTimeInfo: MutableStateFlow<Long> = MutableStateFlow(0L)
    private val wifi: MutableStateFlow<Level> = MutableStateFlow(Level.NaN)
    private val jiaReStatus: MutableStateFlow<StatusValue> = MutableStateFlow(JiaRe to false)
    private val zhiShuiStatus: MutableStateFlow<StatusValue> = MutableStateFlow(ZhiShui to false)
    private val queShuiStatus: MutableStateFlow<StatusValue> = MutableStateFlow(QueShui to false)
    private val jieNengStatus: MutableStateFlow<StatusValue> = MutableStateFlow(JieNeng to false)
    private val huanXinStatus: MutableStateFlow<StatusValue> = MutableStateFlow(HuanXin to false)
    private val shaJunStatus: MutableStateFlow<StatusValue> = MutableStateFlow(ShaJun to false)
    private val yinYongStatus: MutableStateFlow<StatusValue> = MutableStateFlow(YinYong to false)
    private val list: Flow<List<StatusVo>> = combine(
        jiaReStatus,
        zhiShuiStatus,
        queShuiStatus,
        jieNengStatus,
        huanXinStatus,
        shaJunStatus,
        yinYongStatus
    ) { inputs ->
        inputs.mapNotNull { (status, value) ->
            if (value) StatusVo(status, status.text, status.res) else null
        }
    }

    private val inputList: MutableStateFlow<List<UartVo>> = MutableStateFlow(emptyList())
    private val raw: MutableStateFlow<String> = MutableStateFlow("")

    val state: Flow<HomeState> =
        combine(
            serviceDays,
            savedBottles,
            quShuiCount,
            quShuiVolume,
            currentTemperature,
            dateTimeInfo,
            list,
            wifi,
            inputList, raw
        ) { inputs ->
            val savedBottlesValue = inputs[1] as Int
            val currentTemperatureValue = inputs[4] as Int
            val quShuiCountValue = inputs[2] as Int
            val quShuiVolumeValue = inputs[3] as Int
            val dateTimeInfoValue = inputs[5] as Long
            val listValue = inputs[6] as List<StatusVo>
            val wifiValue = inputs[7] as Level
            val inputListValue = inputs[8] as List<UartVo>
            val rawValue = inputs[9] as String
            HomeState(
                gServiceInfo = GServiceInfoVo(inputs[0] as Int),
                bottlesInfo = BottlesInfoVo(
                    savedBottlesValue,
                    savedBottlesValue.formatBottlesNumber()
                ),
                quShuiCount = QuShuiCountInfoVo(quShuiCountValue, quShuiCountValue.formatBottlesNumber()),
                quShuiVolume = QuShuiVolumeInfoVo(quShuiVolumeValue, quShuiVolumeValue.formatBottlesNumber()),
                tempInfo = TempInfoVo(currentTemperatureValue),
                dateTimeInfo = dateTimeInfoValue.timestamp2str(),
                statusListInfo = listValue,
                wifiInfo = WifiInfoVo(wifiValue), debugList/* debug = listOf(
                    com.yunnext.pad.app.ui.screen.vo.DebugCase01Vo,
                    com.yunnext.pad.app.ui.screen.vo.DebugCase02Vo,
                    com.yunnext.pad.app.ui.screen.vo.DebugCase03Vo,
                    com.yunnext.pad.app.ui.screen.vo.DebugCase04Vo,
                )*/,
                inputList = inputListValue, raw = rawValue
            )
        }


    private fun changServiceDays(value: Int) {
        this.serviceDays.value = value
    }

    private fun changSavedBottles(value: Int) {
        this.savedBottles.value = value
    }

    private fun changCurrentTemperature(value: Int) {
        this.currentTemperature.value = value
    }

    private fun changDateTimeInfo(value: Long) {
        this.dateTimeInfo.value = value
    }

    private fun changWifi(value: Level) {
        this.wifi.value = value
    }

    private fun changStatus(status: Status, enable: Boolean) {
        val value = status to enable
        when (status) {
            JiaRe -> this.jiaReStatus.value = value
            ZhiShui -> this.zhiShuiStatus.value = value
            QueShui -> this.queShuiStatus.value = value
            JieNeng -> this.jieNengStatus.value = value
            HuanXin -> this.huanXinStatus.value = value
            ShaJun -> this.shaJunStatus.value = value
            YinYong -> this.yinYongStatus.value = value
        }
    }

    init {
        collectDataManager()
    }

    private fun collectDataManager() {
        viewModelScope.launch {
            launch {
                DataManager.serviceDays.collect {
                    changServiceDays(it)
                }
            }
            launch {
                DataManager.savedBottles.collect {
                    changSavedBottles(it)
                }
            }

            launch {
                DataManager.quShuiCountFlow.collect {
                    quShuiCount.value = it
                }
            }

            launch {
                DataManager.quShuiVolumeFlow.collect {
                    quShuiVolume.value = it / 1000
                    i("quShuiVolume $it ${it/1000}")
                }
            }

            launch {
                DataManager.currentTemperature.collect {
                    changCurrentTemperature(it)
                }
            }

            launch {
                DataManager.dateTimeInfo.collect {
                    changDateTimeInfo(it)
                }
            }

            launch {
                DataManager.wifi.collect {
                    changWifi(it)
                }
            }

            launch {
                DataManager.jiaReStatus.collect {
                    changStatus(JiaRe, it)
                }
            }

            launch {
                DataManager.zhiShuiStatus.collect {
                    changStatus(ZhiShui, it)
                }
            }

            launch {
                DataManager.queShuiStatus.collect {
                    changStatus(QueShui, it)
                }
            }
            launch {
                DataManager.jieNengStatus.collect {
                    changStatus(JieNeng, it)
                }
            }
            launch {
                DataManager.huanXinStatus.collect {
                    changStatus(HuanXin, it)
                }
            }

            launch {
                DataManager.shaJunStatus.collect {
                    changStatus(ShaJun, it)
                }
            }
            launch {
                DataManager.yinYongStatus.collect {
                    changStatus(YinYong, it)
                }
            }

            launch {
                DataManager.uartUpRaw.collect {
                    println("uartUpRaw :$it")
                    val newList = listOf(UartUp(it)) + inputList.value
                    inputList.value = newList
                }
            }

            launch {
                DataManager.uartDownRaw.collect {
                    val newList = listOf(UartDown(it)) + inputList.value
                    inputList.value = newList
                }
            }

            launch {
                DataManager.rawFlow.collect {
                    i("rawFlow:$it")
                    raw.value = it
                }
            }


        }
    }

    fun debug(debug: DebugValue) {
        i("debug = $debug")
        DataManager.debug(debug = debug)
    }

    fun clearUart() {
        this.inputList.value = emptyList()
    }
}


private fun Int.formatBottlesNumber(): String {
    return "%,d".format(this)
}

private fun Int.formatHourOrMinute(): String {
    return "%02d".format(this)
}

private fun Long.timestamp2str(): DateTimeInfoVo {
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val year = localDateTime.year
    val month = localDateTime.monthNumber // 月份从 1 开始
    val day = localDateTime.dayOfMonth
    val hour = localDateTime.hour
    val minute = localDateTime.minute
    return DateTimeInfoVo(
        year = year.toString(),
        month = month.toString(),
        day = day.toString(),
        hour = hour.formatHourOrMinute(),
        minute = minute.formatHourOrMinute()
    )
}