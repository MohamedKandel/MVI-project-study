package com.mkandeel.mviproject


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.mkandeel.mviproject.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: AddNumberViewModel by lazy {
        ViewModelProvider(this)[AddNumberViewModel::class]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rendering()
        binding.btnInc.setOnClickListener {
            // send
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.AddNumber)
            }
        }

        binding.btnDec.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intentChannel.send(MainIntent.MinusNumber)
            }
        }
    }

    private fun rendering() {
        lifecycleScope.launch {
            viewModel.viewState.collect{
                when(it) {
                    is MainViewState.Idle -> {
                        binding.textView.text = "Start"
                    }
                    is MainViewState.Counting -> {
                        binding.textView.text = it.number.toString()
                    }
                    is MainViewState.Error -> {
                        binding.textView.text = it.error
                    }
                }
            }
        }
    }
}