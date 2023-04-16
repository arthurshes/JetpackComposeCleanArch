package com.example.composeretrofitpaging.data.mappers

import com.example.composeretrofitpaging.data.db.BeerEntity
import com.example.composeretrofitpaging.data.remote.BeerDto
import com.example.composeretrofitpaging.domain.Beer

fun BeerDto.toBeerEntity():BeerEntity{
    return BeerEntity(
        id, name, tagline, description, first_brewed, image_url
    )
}

fun BeerEntity.toBeer(): Beer {
    return Beer(
        id, name, tagline, description, first_brewed, image_url
    )
}