package com.yunnext.pad.app

import com.yunnext.pad.app.repo.uart.SerialProtocol
import com.yunnext.pad.app.repo.uart.UartUpCmd
import com.yunnext.pad.app.repo.uart.toArray
import com.yunnext.pad.app.repo.uart.toByteArray2
import org.junit.Test

class TestSerialProtocol {


    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun `parseTotalLength`() {
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
        assert("0001"==parseCmdAndPayload.toHexString())
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
        assert("01"==parseCRC.toHexString())
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
        assert(SerialProtocol.parseCRC(dataByteArray).toHexString()==calCRC.toHexString())
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


}