package edu.ucne.tarea4apiplanets.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.squareup.moshi.KotlinJsonAdapterFactory
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.DragonBallApi
import edu.ucne.tarea4apiplanets.data.apiplanets.remote.remotedatasource.PlanetRemoteDataSource
import edu.ucne.tarea4apiplanets.data.apiplanets.repository.PlanetRepositoryImpl
import edu.ucne.tarea4apiplanets.domain.apiplanets.repository.PlanetRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi{
        return Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    @Provides
    @Singleton
    fun provideApi(moshi: Moshi): DragonBallApi{
        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DragonBallApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: DragonBallApi): PlanetRepository{
        return PlanetRepositoryImpl(PlanetRemoteDataSource(api))
    }
}