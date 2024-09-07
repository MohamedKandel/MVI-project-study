package com.mkandeel.mviproject

sealed class MainIntent {
    object AddNumber: MainIntent()
    object MinusNumber: MainIntent()
}