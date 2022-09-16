package com.github.lex090.basecoins.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.lex090.basecoins.data.services.CoinsNetworkService
import com.github.lex090.basecoins.domain.entity.Coin
import kotlinx.coroutines.delay

class CoinsPagingSource constructor(
    private val coinsNetworkService: CoinsNetworkService
) : PagingSource<Int, Coin>() {

    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost(CoinsNetworkService.MAX_PAGE_SIZE)

        Log.i("myDebug", "load: page -> $page")
        Log.i("myDebug", "load: pageSize -> $pageSize")

        return try {
            val items = coinsNetworkService
                .getCoinsMarketsList(page = page, perPage = pageSize)
                .mapData { false }
            delay(1500)

            Log.i("myDebug", "load: item.size -> ${items.size}")

            val nextKey =  if (items.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(items, prevKey, nextKey)
        } catch (e: Exception) {
            Log.i("myDebug", "load: exception -> $e")
            LoadResult.Error(e)
        }

    }
}