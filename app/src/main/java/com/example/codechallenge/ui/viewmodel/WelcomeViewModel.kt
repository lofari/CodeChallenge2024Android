package com.example.codechallenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codechallenge.api.ContentRepository
import com.example.codechallenge.common.Constants
import com.example.codechallenge.usecase.Every10thCharacterUseCase
import com.example.codechallenge.usecase.WordCounterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val every10thCharacterUseCase: Every10thCharacterUseCase,
    private val wordCounterUseCase: WordCounterUseCase,
    private val repository: ContentRepository
) : ViewModel() {

    private val _every10thCharacter = MutableStateFlow("")
    val every10thCharacter: StateFlow<String> = _every10thCharacter.asStateFlow()

    private val _wordCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val wordCounts: StateFlow<Map<String, Int>> = _wordCounts.asStateFlow()

    private val _isDataAvailable = MutableStateFlow(false)
    val isDataAvailable: StateFlow<Boolean> = _isDataAvailable

    init {
        checkCache()
    }

    private fun checkCache() {
        val isAvailable = repository.hasData(Constants.CACHE_KEY1) &&
            repository.hasData(Constants.CACHE_KEY2)

        _isDataAvailable.value = isAvailable
    }
    private val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }

    fun fetchSimultaneousRequest() = viewModelScope.launch(handler) {
        val every10thCharacterFlow = every10thCharacterUseCase()
        val wordCounterFlow = wordCounterUseCase()

        val every10thCharacterDeferred = async { every10thCharacterFlow.first() }
        val wordCounterDeferred = async { wordCounterFlow.first() }

        _every10thCharacter.value = every10thCharacterDeferred.await()
        _wordCounts.value = wordCounterDeferred.await()
    }
}
