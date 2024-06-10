package com.example.codechallenge.usecase

import com.example.codechallenge.api.ContentRepository
import com.example.codechallenge.common.Constants
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WordCounterUseCase @Inject constructor(private val repository: ContentRepository) {
    operator fun invoke(): Flow<Map<String, Int>> = flow {
        val content = repository.fetchAbout() ?: return@flow
        val wordCounts = content
            .split(
                "\\s+"
                    .toRegex()
            )
            .groupingBy { it.toLowerCase() }
            .eachCount()
        repository.saveData(
            Constants.CACHE_KEY1,
            wordCounts
                .toList()
                .sortedByDescending { it.second }
                .toString()
        )
        emit(wordCounts.toList().sortedByDescending { it.second }.toMap())
    }
}
