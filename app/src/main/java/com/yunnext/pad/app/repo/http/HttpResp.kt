package com.yunnext.pad.app.repo.http

import com.yunnext.pad.app.MyApp
import kotlinx.coroutines.channels.Channel
import kotlinx.serialization.Serializable

@Serializable
class HttpResp<T>(
    val code: Int,
    val msg: String,
    val data: T,
    val success: Boolean
)

val HttpResp<*>.tokenExpired: Boolean
    get() = this.code == 103

private val channel: Channel<Unit> = Channel()
val tokenExpiredChannel: Channel<Unit>
    get() = channel

fun onTokenExpired() {
    MyApp.holder.clear()
    channel.trySend(Unit)
}