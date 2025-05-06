package com.yunnext.pad.app.repo.uart

class SerialProtocol {

    companion object {


        const val VERSION = 1

        const val FLAG = 0

        private const val ZC_BAUD_RATE = 9600

        const val UART = "/dev/qqq"
        const val BAUD_RATE = ZC_BAUD_RATE

        // 帧头
        val HEADER = byteArrayOf(0xAA.toByte(), 0x55.toByte())

        // 帧尾
        val TAIL = byteArrayOf(0x55.toByte(), 0xBB.toByte())

        private fun ByteArray.sum2(): Int {
            var sum = 0
            for (element in this) {
                sum += element.toUByte().toInt()
            }
            return sum
        }

        fun calCRC(byteArray: ByteArray): Byte {

            return (byteArray.sum()).toByte()
        }

        fun calCRC2(byteArray: ByteArray): Byte {

            return (byteArray.sum2()).toByte()
        }

        private const val MIN_DATA_SIZE = 8

        internal fun checkSize(data: ByteArray): Boolean {
            val total = parseTotalLength(data = data)
            return data.isNotEmpty() && data.size >= MIN_DATA_SIZE && data[2].toInt() == total
        }

        internal fun checkCMD(data: ByteArray): Boolean {
            val cmd = parseCmd(data)
            return true
        }

        internal fun parseCmdAndPayload(data: ByteArray): ByteArray {
            val fromIndex = 3
            val toIndex = fromIndex +
                    1 +// cmd
                    parsePayloadLength(data = data) // payload

            return data.copyOfRange(fromIndex = fromIndex, toIndex = toIndex)
        }


        internal fun checkCRC(data: ByteArray): Boolean {
            if (data.isEmpty()) return false
            val cmdAndPayload = parseCmdAndPayload(data = data)
            return calCRC(cmdAndPayload) == parseCRC(data)
        }

        internal fun checkHeaderAndTail(data: ByteArray): Boolean {
            if (data.isEmpty() || data.size < MIN_DATA_SIZE) return false

            return data[0] == HEADER[0]
                    && data[1] == HEADER[1]
                    && data[data.size - 2] == TAIL[0]
                    && data[data.size - 1] == TAIL[1]

        }

        //internal fun parseHeader(data: ByteArray) = data[0]
        //internal fun parseVersion(data: ByteArray) = data[1]
        internal fun parseTotalLength(data: ByteArray): Int = data[2].toInt()
        internal fun parsePayloadLength(data: ByteArray): Int = parseTotalLength(data = data) - 7
        internal fun parseCmd(data: ByteArray): Int = data[3].toInt()

        //internal fun parseTail(data: ByteArray) = data[data.size - 2]
        internal fun parseCRC(data: ByteArray) = data[data.size - 3]

        internal fun parsePayload(data: ByteArray) =
            data.copyOfRange(4, parsePayloadLength(data = data))
    }
}

internal fun Byte.toArray() = byteArrayOf(this)








