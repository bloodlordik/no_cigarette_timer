package ru.kirshov.nocigarettetimer.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kirshov.nocigarettetimer.R
import ru.kirshov.nocigarettetimer.data.TimerViewModelFactory
import ru.kirshov.nocigarettetimer.databinding.FragmentMainBinding
import ru.kirshov.nocigarettetimer.domain.MainViewModel

class MainFragment:Fragment(R.layout.fragment_main) {
    lateinit var binding:FragmentMainBinding
    private val viewModel: MainViewModel by viewModels {
      TimerViewModelFactory(context = requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.update().collect{
                updateUi(it)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.startButton.setOnClickListener { viewModel.startTimer() }
        binding.stopButton.setOnClickListener { viewModel.stopTimer() }
        binding.clean.setOnClickListener { viewModel.clean() }
        binding.update.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main){
                viewModel.update().collect{
                    updateUi(it)
                }
            }
        }

    }
    private fun updateUi(state: UiState) = with(binding){
        total.text = state.last.toString()
        lastTime.text = state.time
        isProcess.text = state.status.toString()
    }
}