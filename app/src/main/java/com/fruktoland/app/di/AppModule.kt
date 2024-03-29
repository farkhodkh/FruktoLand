package com.fruktoland.app.di

import android.content.Context
import androidx.room.Room
import com.fruktoland.app.data.api.FruktoLandApiService
import com.fruktoland.app.data.persistence.room.FruktoLandDataBase
import com.fruktoland.app.ui.view.ModuleInteractor
import com.fruktoland.app.ui.view.ModuleInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesMainFragmentInteractor(db: FruktoLandDataBase, service: FruktoLandApiService): ModuleInteractor =
        ModuleInteractorImpl(db, service)

    @Provides
    fun providesFruktoLandDataBase(@ApplicationContext appContext: Context): FruktoLandDataBase =
        Room.databaseBuilder(
            appContext,
            FruktoLandDataBase::class.java,
            FruktoLandDataBase.DATABASE_NAME
        )
            .build()

    @Provides
    fun providesFruktoLandApiService(): FruktoLandApiService = FruktoLandApiService()
}