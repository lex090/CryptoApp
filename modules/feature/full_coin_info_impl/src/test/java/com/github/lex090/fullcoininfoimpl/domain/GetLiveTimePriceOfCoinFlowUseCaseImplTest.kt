package com.github.lex090.fullcoininfoimpl.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetLiveTimePriceOfCoinFlowUseCaseImplTest {

    private val fullCoinInfoRepository: IFullCoinInfoRepository = mock()

    @Test
    fun `get real time price success`() = runTest {
        val expected = "bitcoin"
        val expectedItems = listOf(2.0, 4.0, 5.0)
        val executionCount = 1
        val expectedFlow = expectedItems.asFlow()

        whenever(fullCoinInfoRepository.getRealTimePriceOfCoin(expected))
            .thenReturn(expectedFlow)

        val useCase = GetLiveTimePriceOfCoinFlowUseCaseImpl(fullCoinInfoRepository)
        val actualItems = useCase.execute(expected).toList()

        Assert.assertEquals(expectedItems, actualItems)
        verify(fullCoinInfoRepository, times(executionCount))
            .getRealTimePriceOfCoin(expected)
    }
}
