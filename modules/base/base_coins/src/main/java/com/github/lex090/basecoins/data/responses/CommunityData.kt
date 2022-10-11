package com.github.lex090.basecoins.data.responses

import com.squareup.moshi.Json

data class CommunityData(
    @Json(name = "twitter_followers")
    val twitterFollowers: Double = 0.0,
    @Json(name = "reddit_subscribers")
    val redditSubscribers: Double = 0.0,
    @Json(name = "reddit_average_posts_48h")
    val redditAveragePostsH: Double = 0.0,
    @Json(name = "reddit_average_comments_48h")
    val redditAverageCommentsH: Double = 0.0,
    @Json(name = "reddit_accounts_active_48h")
    val redditAccountsActiveH: Double = 0.0
)