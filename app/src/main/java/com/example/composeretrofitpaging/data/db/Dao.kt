package com.example.composeretrofitpaging.data.db
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface Dao {
    @Upsert
    suspend fun upsertAll(beers:List<BeerEntity>)

    @Query("SELECT * FROM beerEntitty")
    fun pagingSourse():PagingSource<Int,BeerEntity>

    @Query("DELETE FROM beerEntitty")
    suspend fun clearAll()
}