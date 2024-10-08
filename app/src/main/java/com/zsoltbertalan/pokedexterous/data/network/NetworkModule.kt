package com.zsoltbertalan.pokedexterous.data.network

import com.zsoltbertalan.pokedexterous.data.repository.PokedexterousAccessor
import com.zsoltbertalan.pokedexterous.domain.api.PokedexterousRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL: String = "https://pokeapi.co/api/v2/"

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Provides
	@Singleton
	internal fun providePokedexRetrofit(): Retrofit {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY

		val httpClient = OkHttpClient.Builder()

		httpClient.addInterceptor(logging)
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(httpClient.build())
			.build()
	}

	@Provides
	@Singleton
	internal fun providePokedexService(retroFit: Retrofit): PokedexService {
		return retroFit.create(PokedexService::class.java)
	}

}

@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
interface BindingNetworkModule {

	@Binds
	fun bindPokedexterousRepository(pokedexterousAccessor: PokedexterousAccessor): PokedexterousRepository

}
