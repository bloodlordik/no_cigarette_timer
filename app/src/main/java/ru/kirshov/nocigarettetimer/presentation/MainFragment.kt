package ru.kirshov.nocigarettetimer.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kirshov.nocigarettetimer.R
import ru.kirshov.nocigarettetimer.data.TimerViewModelFactory
import ru.kirshov.nocigarettetimer.databinding.FragmentMainBinding
import ru.kirshov.nocigarettetimer.domain.DownCountTimer
import ru.kirshov.nocigarettetimer.domain.MainViewModel
import kotlin.time.Duration

class MainFragment : Fragment(R.layout.fragment_main) {
    lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
        TimerViewModelFactory(context = requireContext())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.update().collect{
                onViewStateBinding(it)
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.startButton.setOnClickListener {
            viewModel.startTimer()
        }
        binding.stopButton.setOnClickListener {
            viewModel.stopTimer()
        }
    }

    private fun onViewStateBinding(state:UiState){
        when(state){
            is UiState.Loading -> {
                with(binding){
                    lastTime.isVisible = false
                    isProcess.isVisible = false
                    total.isVisible = false
                    stopButton.isVisible = false
                    startButton.isVisible = false
                    update.isVisible = false
                    clean.isVisible = false
                }
            }
            is UiState.EmptyUi ->{
                with(binding){
                    total.isVisible = true
                    total.text = "Empty state"
                    startButton.isVisible = true
                }
            }
            is UiState.IsFinished ->{
                with(binding){

                }
            }
            is UiState.IsProcess ->{
                with(binding){
                    total.isVisible = true
                    total.text = state.durationText
                    startButton.isVisible = false
                    stopButton.isVisible = true
                }
            }
            is UiState.IsStopped ->{
                with(binding){
                    total.isVisible = true
                    total.text = state.stoppedText
                    startButton.isVisible = true
                    stopButton.isVisible = false
                }
            }
        }
    }

}