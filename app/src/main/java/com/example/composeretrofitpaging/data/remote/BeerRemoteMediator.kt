package com.example.composeretrofitpaging.data.remote

import androidx.paging.*
import androidx.paging.LoadType.*
import androidx.room.withTransaction
import com.example.composeretrofitpaging.data.db.BeerEntity
import com.example.composeretrofitpaging.data.db.MainDb
import com.example.composeretrofitpaging.data.mappers.toBeerEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(private val mainDb: MainDb,
                         private val apiService: ApiService):RemoteMediator<Int,BeerEntity>() {
    private val dao = mainDb.getDao()
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
       return  try{
         val loadKey = when(loadType){
           LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(
                endOfPaginationReached = true
            )
             LoadType.APPEND -> {
                 val lasItem = state.lastItemOrNull()
                 if (lasItem==null){
                     1
                 }else{
                     (lasItem.id/state.config.pageSize)+1
                 }
             }
         }
           val beers = apiService.getBeers(
               page = loadKey,
               pageCount = state.config.pageSize
           )
           mainDb.withTransaction {
               if (loadType == LoadType.REFRESH){
                 dao.clearAll()
               }
               val beerEntity = beers.map { it.toBeerEntity() }
               dao.upsertAll(beerEntity)
           }
           MediatorResult.Success(
               endOfPaginationReached = beers.isEmpty()
           )
       }catch (e:IOException){
           MediatorResult.Error(e)
       }catch (e:HttpException){
           MediatorResult.Error(e)
       }
    }


}