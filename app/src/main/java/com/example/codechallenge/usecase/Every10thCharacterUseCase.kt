package com.example.codechallenge.usecase

import com.example.codechallenge.api.ContentRepository
import com.example.codechallenge.common.Constants
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// class Every10thCharacterUseCase @Inject constructor(private val repository: ContentRepository) {
//    operator fun invoke(): Flow<String> = flow {
//        val content = repository.fetchAbout() ?: return@flow
//        val result = mutableListOf<Char>()
//
// //        for (i in 0 until content.length) {
// //            if (i % 10 == 0) {
// //                result.add(content[i])
// //            }
// //        }
//        for (i in content.indices) {
//            if ((i + 1) % 10 == 0) {
//                result.add(content[i])
//            }
//        }
//
//        repository.saveData(Constants.CACHE_KEY2, result.toCharArray().joinToString(","))
//
// //        val result = content.filterIndexed { index, _ -> (index + 1) % 10 == 0 }
//        emit(result.toCharArray().joinToString(","))
//    }
// }

class Every10thCharacterUseCase @Inject constructor(private val repository: ContentRepository) {
    operator fun invoke(): Flow<String> = flow {
        val content = repository.fetchAbout() ?: return@flow
        val result = mutableListOf<Char>()

        for (i in content.indices) {
            if ((i + 1) % 10 == 0) {
                result.add(content[i])
            }
        }

        val resultString = result.joinToString(",")
        repository.saveData(Constants.CACHE_KEY2, resultString)
        emit(resultString)
    }
}
