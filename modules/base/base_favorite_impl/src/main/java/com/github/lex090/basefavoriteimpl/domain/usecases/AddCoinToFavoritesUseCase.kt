package com.github.lex090.basefavoriteimpl.domain.usecases

//import com.github.lex090.coreapi.domain.IBaseUseCase
//import com.github.lex090.coredbapi.dao.FavoriteCoinsDao
//import javax.inject.Inject
//
//class AddCoinToFavoritesUseCase @Inject constructor(
//    private val favoriteCoinDao: FavoriteCoinsDao
//) : IBaseUseCase<String, Unit> {
//
//    override suspend fun execute(data: String?) {
//        requireNotNull(data)
//
//        favoriteCoinDao.addCoinToFavorites()
//    }
//}