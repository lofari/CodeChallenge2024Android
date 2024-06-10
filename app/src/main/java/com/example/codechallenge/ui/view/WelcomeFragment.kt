package com.example.codechallenge.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.codechallenge.api.ContentRepository
import com.example.codechallenge.databinding.FragmentWelcomeBinding
import com.example.codechallenge.ui.viewmodel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<WelcomeViewModel>()
    @Inject
    lateinit var repository: ContentRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
        setResponse1Observer()
        setResponse2Observer()
        setCacheObserver()
    }

    private fun setResponse1Observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.every10thCharacter.collect {
                binding.welcomeTitle.text = "Every 10th Character string: \n $it"
            }
        }
    }
    private fun setResponse2Observer() {
        lifecycleScope.launchWhenStarted {
            viewModel.wordCounts.collect { result ->
                binding.title.text = "Word Count: \n" + result.entries.joinToString { item ->
                    "${item.key}: ${item.value} \n"
                }
            }
        }
    }

    private fun setCacheObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.isDataAvailable.collect { isAvailable ->
                Log.d("isAvailable", "setCacheObserver() called with: isAvailable = $isAvailable")
                binding.cacheButton.isVisible = isAvailable
            }
        }
    }

    private fun setButtonClickListener() {
        binding.startButton.setOnClickListener {
            viewModel.fetchSimultaneousRequest()
//            findNavController().navigate(R.id.action_mainFragment_to_listFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
