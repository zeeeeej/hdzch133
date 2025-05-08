package com.yunnext.pad.app.repo.uart

sealed interface UartDown : Uart {
    override val cmd: UartDownCmd

    data object GetAllDown : com.yunnext.pad.app.repo.uart.UartDown {
        override val cmd: UartDownCmd
            get() = UartDownCmd.GetAllCmd

    }


}

enum class UartDownCmd(override val value: Byte) : UartCmd {
    GetAllCmd(0xFE.toByte()),
    ;
}

fun UartDown.encode():ByteArray {
   return when (this) {
        UartDown.GetAllDown -> SerialProtocol.create(
            this.cmd.value, byteArrayOf(0)
        )
    }
}
