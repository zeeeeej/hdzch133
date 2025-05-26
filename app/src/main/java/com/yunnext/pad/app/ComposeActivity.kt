package com.yunnext.pad.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.yunnext.pad.app.domain.ToastUtil
import com.yunnext.pad.app.ui.screen.HomeScreen
import com.yunnext.pad.app.ui.screen.vm.HomeViewModel
import com.yunnext.pad.app.ui.screen.vo.Loading
import com.yunnext.pad.app.ui.theme.HDText20
import com.yunnext.pad.app.ui.theme.HDText75

import com.yunnext.pad.app.ui.theme.MyAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ComposeActivity : FragmentActivity() {

    //private val vm : HomeViewModel by viewModels<HomeViewModel>()

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        checkPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //MyApp.reset(this.applicationContext)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {

                var loaded by remember {
                    mutableStateOf(false)
                }

                var splash by remember {
                    mutableStateOf(true)
                }

                var initMsg by remember {
                    mutableStateOf("准备初始化...")
                }

                val alpha by animateFloatAsState(
                    targetValue = if (loaded) 0f else 1f, animationSpec = TweenSpec(3000),
                    label = "alpha_"
                ) {
                    splash = false
                }


                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Red)
                ) { innerPadding ->
                    val rememberCoroutineScope = rememberCoroutineScope()
                    var loadingUi:Loading by remember { mutableStateOf(Loading.Start) }

                    LaunchedEffect(key1 = loadingUi, block = {
                        rememberCoroutineScope.launch {
                            val tmp = loadingUi
                            initMsg = when ( tmp) {
                                Loading.Completed -> "初始化完毕"
                                is Loading.Ing -> tmp.msg
                                Loading.Start -> "初始化开始"
                            }
                            if (tmp == Loading.Completed) {
                                delay(500)
                                loaded = true
                            }
                        }
                    })
                    LaunchedEffect (Unit) {
                        delay(1000)
                        loadingUi = Loading.Ing("加载中...")
                        delay(1000)
                        loadingUi = Loading.Completed
                    }
                    HomeScreen(Modifier.fillMaxSize()
                        ,innerPadding)

                }

                AnimatedVisibility(visible = splash) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .drawBehind {
                                drawRect(Color.Black.copy(alpha = alpha))
                            }, contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "", style = HDText75.copy(
                                    fontSize = 120.sp,
                                    fontWeight = FontWeight.Bold
                                ), modifier = Modifier.alpha(alpha)
                            )
                            Text(
                                text = initMsg, style = HDText20.copy(
                                    fontSize = 11.sp, textAlign = TextAlign.Center
                                ), modifier = Modifier.alpha(alpha)
                            )
                        }
                    }
                }

            }
        }
    }

    private fun loadPermissionNoGant() = listOfNotNull(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val has = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
            if (has) null else Manifest.permission.BLUETOOTH_CONNECT
        } else {
            null
        }, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val has = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED
            if (has) null else Manifest.permission.BLUETOOTH_SCAN
        } else {
            null
        }, run {
            val has = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            if (has) null else Manifest.permission.ACCESS_FINE_LOCATION
        }, run {
            val has = ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            if (has) null else Manifest.permission.CAMERA
        }
    )


    private fun requestPermission(): Boolean {
        val permissions = loadPermissionNoGant()
        if (permissions.isNotEmpty()) {
            launcher.launch(permissions.toTypedArray())
            return false
        }
        return true

    }

    private fun checkPermission() {
        val permissions = loadPermissionNoGant()
        if (permissions.isNotEmpty()) {
            ToastUtil.toast("请检查APP权限，以便正常使用。")
        }
    }

}
