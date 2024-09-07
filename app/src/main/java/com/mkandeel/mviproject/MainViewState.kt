package com.mkandeel.mviproject

sealed class MainViewState {
    // idle state (counting not started yet)
    object Idle: MainViewState()
    // number (counting started)
    data class Counting(val number: Int): MainViewState()
    // error occurred
    data class Error(val error:String): MainViewState()
}