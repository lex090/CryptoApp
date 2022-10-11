package com.github.lex090.basefavoriteimpl.domain.usecases

import com.github.lex090.basefavoriteimpl.domain.IFavoritesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
class ClearFavoritesUseCaseImplTest {

    private val favoritesRepository: IFavoritesRepository = mock()

    @Test
    fun `clear favorite test UseCase`() = runTest {
        val executionCount = 1

        val useCase = ClearFavoritesUseCaseImpl(favoritesRepository)
        useCase.execute()

        verify(favoritesRepository, times(executionCount))
    }
}
