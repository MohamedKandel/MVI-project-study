package com.mkandeel.mviproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumberViewModel: ViewModel() {
    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val viewState: StateFlow<MainViewState> get() = _viewState
    private var number = -1

    init {
        process()
    }

    private fun process() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect{
                when(it) {
                    is MainIntent.AddNumber -> {
                        addNumber()
                    }
                    is MainIntent.MinusNumber -> {
                        minusNumber()
                    }
                }
            }
        }
    }

    private fun addNumber() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Counting(++number)
                }catch (ex: Exception) {
                    MainViewState.Error(ex.message!!)
                }
        }
    }

    private fun minusNumber() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Counting(--number)
                }catch (ex: Exception) {
                    MainViewState.Error(ex.message!!)
                }
        }
    }
}