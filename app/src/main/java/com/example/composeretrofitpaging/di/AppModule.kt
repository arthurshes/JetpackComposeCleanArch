package com.example.composeretrofitpaging.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.composeretrofitpaging.data.db.BeerEntity
import com.example.composeretrofitpaging.data.db.MainDb
import com.example.composeretrofitpaging.data.remote.ApiService
import com.example.composeretrofitpaging.data.remote.ApiService.Companion.BASE_URL
import com.example.composeretrofitpaging.data.remote.BeerRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(@ApplicationContext context:Context):MainDb =
        Room.databaseBuilder(context,MainDb::class.java,"beers.db")
            .build()

    @Provides
    @Singleton
    fun provideApiService():ApiService=
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideBeerPager(mainDb: MainDb,apiService: ApiService):Pager<Int,BeerEntity>{
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BeerRemoteMediator(
                mainDb, apiService
            ),
            pagingSourceFactory = {
                mainDb.getDao().pagingSourse()
            }
        )
    }
}