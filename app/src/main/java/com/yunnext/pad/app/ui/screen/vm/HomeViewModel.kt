package com.yunnext.pad.app.ui.screen.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunnext.pad.app.ui.screen.vo.TempInfoVo
import com.yunnext.pad.app.ui.screen.vo.DateTimeInfoVo
import com.yunnext.pad.app.ui.screen.vo.LeftInfoVo
import com.yunnext.pad.app.ui.screen.vo.Level
import com.yunnext.pad.app.ui.screen.vo.RightInfoVo
import com.yunnext.pad.app.ui.screen.vo.Status
import com.yunnext.pad.app.ui.screen.vo.Status.*
import com.yunnext.pad.app.ui.screen.vo.StatusVo
import com.yunnext.pad.app.ui.screen.vo.WifiInfoVo
import com.yunnext.pad.app.ui.screen.vo.res
import com.yunnext.pad.app.ui.screen.vo.text
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

data class HomeState(
    val leftInfo: LeftInfoVo,
    val rightInfo: RightInfoVo,
    val tempInfo: TempInfoVo,
    val dateTimeInfo: DateTimeInfoVo,
    val statusListInfo: List<StatusVo>,
    val wifiInfo: WifiInfoVo
) {
    companion object {
        internal val DEFAULT = HomeState(
            leftInfo = LeftInfoVo(0),
            rightInfo = RightInfoVo(0, "0"),
            tempInfo = TempInfoVo(0),
            dateTimeInfo = DateTimeInfoVo("2000", "1", "1", "00", "00"),
            statusListInfo = emptyList(),
            wifiInfo = WifiInfoVo(Level.NaN)
        )
    }
}

private typealias StatusValue = Pair<Status, Boolean>

class HomeViewModel : ViewModel() {

    private val serviceDays: MutableStateFlow<Int> = MutableStateFlow(0)
    private val savedBottles: MutableStateFlow<Int> = MutableStateFlow(0)
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
        }.take(3)
    }

    val state: Flow<HomeState> =
        combine(serviceDays, savedBottles, currentTemperature, dateTimeInfo, list, wifi) { inputs ->
            val savedBottlesValue = inputs[1] as Int
            val currentTemperatureValue = inputs[2] as Int
            val dateTimeInfoValue = inputs[3] as Long
            val listValue = inputs[4] as List<StatusVo>
            val wifiValue = inputs[5] as Level
            HomeState(
                leftInfo = LeftInfoVo(inputs[0] as Int),
                rightInfo = RightInfoVo(savedBottlesValue, savedBottlesValue.formatBottlesNumber()),
                tempInfo = TempInfoVo(currentTemperatureValue),
                dateTimeInfo = dateTimeInfoValue.timestamp2str(),
                statusListInfo = listValue,
                wifiInfo = WifiInfoVo(wifiValue)
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
        mock()
    }

    private fun mock() {
        viewModelScope.launch {
            launch {
                while (isActive) {
                    delay(1000)
                    changServiceDays(Random.nextInt(9999))
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    changSavedBottles(Random.nextInt(9999))
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    changCurrentTemperature(Random.nextInt(100))
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    changDateTimeInfo(Clock.System.now().toEpochMilliseconds())
                }
            }

            launch {
                while (isActive) {
                    delay(2000)
                    changWifi(Level.random())
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    changStatus(JiaRe, Random.nextBoolean())
                }
            }

            launch {
                while (isActive) {
                    delay(1000)
                    changStatus(ZhiShui, Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    changStatus(QueShui, Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    changStatus(JieNeng, Random.nextBoolean())

                }
            }
            launch {
                while (isActive) {
                    delay(1000)
                    changStatus(HuanXin, Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)

                    changStatus(ShaJun, Random.nextBoolean())
                }
            }
            launch {
                while (isActive) {
                    delay(1000)

                    changStatus(YinYong, Random.nextBoolean())
                }
            }
        }
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