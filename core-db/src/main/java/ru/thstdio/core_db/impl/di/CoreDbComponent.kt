package ru.thstdio.core_db.impl.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.thstdio.core_db.api.di.CoreDbApi
import javax.inject.Singleton

@Component(modules = [DbModule::class])
@Singleton
abstract class CoreDbComponent : CoreDbApi {
    companion object {
        @Volatile
        private var coreDbComponent: CoreDbComponent? = null

        fun get(applicationContext: Context): CoreDbComponent {
            if (coreDbComponent == null) {
                synchronized(CoreDbComponent::class.java) {
                    if (coreDbComponent == null) {
                        coreDbComponent = DaggerCoreDbComponent
                            .builder()
                            .setContext(applicationContext)
                            .build()
                    }
                }
            }
            return coreDbComponent!!
        }
    }

    @Component.Builder
    interface CoreDbComponentBuilder {
        fun build(): CoreDbComponent

        @BindsInstance
        fun setContext(applicationContext: Context): CoreDbComponentBuilder

    }
}