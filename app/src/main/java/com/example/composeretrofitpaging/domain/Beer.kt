package com.example.composeretrofitpaging.domain

data class Beer(
    val id:Int,
    val name:String,
    val tagline:String,
    val description:String,
    val first_brewed:String,
    val image_url:String?
)
