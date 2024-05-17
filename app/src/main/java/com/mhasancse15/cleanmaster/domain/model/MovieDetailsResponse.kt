package com.mhasancse15.cleanmaster.domain.model

data class MovieDetailsResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val homepage: String

)
