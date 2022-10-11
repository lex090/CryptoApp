package com.github.lex090.fullcoininfoimpl.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinInfoUseCaseImplTest {

    private val fullCoinInfoRepository: IFullCoinInfoRepository = mock()

    @Test
    fun `test get full coin info`() = runTest {
        val expected = "bitcoin"
        val executionCount = 1

        val useCase = GetCoinInfoUseCaseImpl(fullCoinInfoRepository)
        useCase.execute(expected)

        verify(fullCoinInfoRepository, times(executionCount))
            .getFullCoinInfo(expected)
    }
}
