package com.github.lex090.basecoins.domain.usecases


import com.github.lex090.basecoins.domain.ICoinsRepository
import com.github.lex090.basecoins.testCoins
import com.github.lex090.coreapi.ResultOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetCoinListFlowUseCaseImplTest {

    private val repository: ICoinsRepository = mock()

    @Test
    fun `get coin list flow result success`() = runTest {
        val expected = ResultOf.Success(testCoins)
        val expectedCount = 1
        val testFlow = flow {
            emit(expected)
        }

        whenever(repository.getCoinsListFlow()).thenReturn(testFlow)
        val useCase = GetCoinListFlowUseCaseImpl(repository)

        val items = useCase.execute().toList()
        val actual = items.first()
        val actualCount = items.size
        Assert.assertEquals(expected, actual)
        Assert.assertEquals(expectedCount, actualCount)
    }

    @Test
    fun `get coin list flow result error`() = runTest {
        val expectedItem = ResultOf.Error(RuntimeException(), "Error")
        val expectedCount = 1
        val testFlow = flow {
            emit(expectedItem)
        }

        whenever(repository.getCoinsListFlow()).thenReturn(testFlow)
        val useCase = GetCoinListFlowUseCaseImpl(repository)

        val items = useCase.execute().toList()
        val actualItem = items.first()
        val actualCount = items.size
        Assert.assertEquals(expectedItem, actualItem)
        Assert.assertEquals(expectedCount, actualCount)
    }
}

