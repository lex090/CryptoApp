package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import com.github.lex090.basefavoriteimpl.domain.coin1
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class AddCoinToFavoritesUseCaseImplTest {

    private val favoritesRepository: IFavoritesRepository = mock()

    @Test
    fun `add to favorite test UseCase`() = runTest {
        val expected = coin1
        val executionCount = 1

        val useCase = AddCoinToFavoritesUseCaseImpl(favoritesRepository)
        useCase.execute(expected)

        verify(favoritesRepository, times(executionCount))
            .addCoinToFavorites(expected)
    }
}

