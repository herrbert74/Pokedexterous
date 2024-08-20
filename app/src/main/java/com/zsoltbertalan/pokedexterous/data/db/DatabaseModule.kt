package com.zsoltbertalan.pokedexterous.data.db

import com.zsoltbertalan.pokedexterous.common.async.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

	@Provides
	@Singleton
	fun provideRealmConfiguration() = RealmConfiguration.Builder(
		schema = setOf(PokemonDbo::class, PokemonPageDbo::class, PokemonDetailsDbo::class)
	).build()

	@Provides
	@Singleton
	fun provideRealm(realmConfiguration: RealmConfiguration) = Realm.open(realmConfiguration)

	@Provides
	@Singleton
	internal fun providePokemonDataSource(
		realm: Realm,
		@IoDispatcher ioContext: CoroutineDispatcher,
	): PokemonDataSource {
		return PokemonDao(realm, ioContext)
	}

}
