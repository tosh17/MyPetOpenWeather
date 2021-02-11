package ru.thstdio.mypetopenweather.framework.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.thstdio.mypetopenweather.framework.di.module.ApiModule
import ru.thstdio.mypetopenweather.framework.di.module.BdModule
import ru.thstdio.mypetopenweather.presentation.city.CityFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApiModule::class, BdModule::class]
)
interface AppComponent {
     fun inject(fragment: CityFragment)

    @Component.Builder
    interface MyBuilder {
        fun build(): AppComponent

        @BindsInstance
        fun setContext(applicationContext: Context): MyBuilder

    }
}