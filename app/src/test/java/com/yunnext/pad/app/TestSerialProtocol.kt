package com.yunnext.pad.app

import com.yunnext.pad.app.repo.uart.SerialProtocol
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
        val calCRC = SerialProtocol.calCRC2(SerialProtocol.parseCmdAndPayload(dataByteArray))
        println("calCRC:${calCRC.toHexString()}")
        assert(SerialProtocol.parseCRC(dataByteArray).toHexString()==calCRC.toHexString())
    }
}