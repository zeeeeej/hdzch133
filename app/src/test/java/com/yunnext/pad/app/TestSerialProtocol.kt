package com.yunnext.pad.app

import com.yunnext.pad.app.repo.uart.SerialProtocol
import com.yunnext.pad.app.repo.uart.UartUp
import com.yunnext.pad.app.repo.uart.UartUpCmd
import com.yunnext.pad.app.repo.uart.encode
import com.yunnext.pad.app.repo.uart.toArray
import com.yunnext.pad.app.repo.uart.toByteArray2
import com.yunnext.pad.app.ui.screen.vo.Level
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TestSerialProtocol {


    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `parseTotalLength`() {

        val now: Int = (System.currentTimeMillis() / 1000).toInt()
        println("now : ${now}")
        println("now : ${now.toHexString()}")

        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")
        val parseTotalLength = SerialProtocol.parseTotalLength(data = dataByteArray)
        println("parseTotalLength:${parseTotalLength}")
        assert(parseTotalLength == 8)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `parsePayloadLength`() {
        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")
        val parsePayloadLength = SerialProtocol.parsePayloadLength(data = dataByteArray)
        println("parsePayloadLength:${parsePayloadLength}")
        assert(parsePayloadLength == 1)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `parseCmdAndPayload`() {
        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")

        val parseCmdAndPayload = SerialProtocol.parseCmdAndPayload(dataByteArray)
        println("parseCmdAndPayload:${parseCmdAndPayload.toHexString()}")
        assert("0001" == parseCmdAndPayload.toHexString())
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `parseCrc`() {
        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")
        val parseCRC = SerialProtocol.parseCRC(dataByteArray)
        println("parseCRC:${parseCRC.toHexString()}")
        assert("01" == parseCRC.toHexString())
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `checkHeaderAndTail`() {
        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")
        assert(SerialProtocol.checkHeaderAndTail(dataByteArray))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `checkCRC`() {
        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")
        assert(SerialProtocol.checkCRC(dataByteArray))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `checkSize`() {
        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")
        assert(SerialProtocol.checkSize(dataByteArray))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `calCRC`() {
        // AA55 08 0001 01 55BB
        val data = "AA550800010155BB"
        val dataByteArray = data.hexToByteArray()
        println("data:${dataByteArray.toHexString()}")
        val calCRC = SerialProtocol.calCRC(SerialProtocol.parseCmdAndPayload(dataByteArray))
        println("calCRC:${calCRC.toHexString()}")
        assert(SerialProtocol.parseCRC(dataByteArray).toHexString() == calCRC.toHexString())
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `create`() {
        val data = SerialProtocol.create(
            UartUpCmd.JieNengCmd.value, 0x01.toByte().toArray()
        )
        println("create:${data.toHexString()}")
        assert(SerialProtocol.checkSize(data = data))
        assert(SerialProtocol.checkCMD(data = data))
        assert(SerialProtocol.checkCRC(data = data))
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `parsePayload`() {
        // aa55 08 0701 08 55bb
        val data = "aa550807010855bb".hexToByteArray()
        println("data:${data.toHexString()}")
        val payload = SerialProtocol.parsePayload(
            data = data
        )
        println("parsePayload:${payload.toHexString()}")
        assert("01" == payload.toHexString())
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `toByteArray2`() {
        // aa55 08 0701 08 55bb
        val raw = 0x1234
        println("raw:${raw.toHexString()}")
        val payload = raw.toByteArray2()
        println("payload:${payload.toHexString()}")
        assert("1234" == payload.toHexString())
    }


    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `DateUpTest`() {
        // aa55 0d 0f 07e905080a27 3d 55bb
        val data = "07e905080a27"
        val up = UartUp.DateUp.from(data.hexToByteArray())
        println(up)
        assert(up.year == 2025)
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `QuShuiCountUp`() {
        // aa55 0b 10 00000063 73 55bb
        val data = UartUp.QuShuiCountUp(99).encode()
        println(data.toHexString())
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `WifiUp`() {
        // aa55 0b 10 00000063 73 55bb
        val data = UartUp.WifiUp(Level.Signal(0x13,0x13)).encode()
        println(data.toHexString())
        // aa55 08 01 13 14 55bb
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `QuShuiVolumeUp`() {
        // aa55 0b 10 00000063 73 55bb
        val data = UartUp.QuShuiVolumeUp(0x0000015c).encode()
        println(data.toHexString())
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `GetAllUp_from`() {
        // aa55 0b 10 00000063 73 55bb
        val data = "0101010201000F0607E808091028"
        // 010101
        // 020100
        // 0F0607E808091028
        println(data.hexToByteArray().copyOfRange(3, 3 + 2).toHexString())
        val up = UartUp.GetAllUp.from(data.hexToByteArray())
        println(up)
        assert(up.value.size == 3)
    }

    @Test
    @OptIn(ExperimentalStdlibApi::class)
    fun `splitDataByMarkers`() {

        val time = 0xffffffff

        // 创建 SimpleDateFormat 对象，指定目标时间格式
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        // 将时间戳转换为 Date 对象
        val date = Date(time * 1000) // 时间戳需要转换为毫秒

        // 格式化 Date 对象
        val formattedDate = dateFormat.format(date)

        // 输出格式化后的时间
        println("Formatted Date: $formattedDate")

//        val data = "aa550807010855bbaa550907010855bb121313".hexToByteArray()
        val data = "aa550801141555bbaa550b120000002b3d55bb".hexToByteArray()
        val splitDataByMarkers = SerialProtocol.splitDataByMarkers(input = data)
        if (splitDataByMarkers.isEmpty()) return
        println("->size = " + splitDataByMarkers.size)
        splitDataByMarkers.forEach {
            println("->" + it.toHexString())
        }


    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `splitDataByMarkersAndDevoce`() {

        val time = 0xffffffff

        // 创建 SimpleDateFormat 对象，指定目标时间格式
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        // 将时间戳转换为 Date 对象
        val date = Date(time * 1000) // 时间戳需要转换为毫秒

        // 格式化 Date 对象
        val formattedDate = dateFormat.format(date)

        // 输出格式化后的时间
        println("Formatted Date: $formattedDate")

//        val data = "aa550807010855bbaa550907010855bb121313".hexToByteArray()
//        val data = "aa550801141555bbaa550b120000002b3d55bb".hexToByteArray()
        val data =
            "aa550d0f07e9051a0d1f4a55bbaa550801131455bbaa550b12000002b9cd55bb".hexToByteArray()
        val splitDataByMarkers = SerialProtocol.splitDataByMarkers(input = data)
        if (splitDataByMarkers.isEmpty()) return
        println("开始解析size = " + splitDataByMarkers.size)
        splitDataByMarkers.forEachIndexed { index, bytes ->
            println("---------$index---------------")
            println("->" + bytes.toHexString())
            val up = UartUp.decode(data = bytes)
            println("->up:$up")
        }

        // aa550801131455bb
    }


}