package ru.thstdio.core_network.impl.di

import dagger.Component
import ru.thstdio.core_network.api.di.CoreNetwork
import javax.inject.Singleton


@Component(modules = [NetworkModule::class])
@Singleton
abstract class CoreNetworkComponent : CoreNetwork {
    companion object {
        @Volatile
        private var coreNetworkComponent: CoreNetworkComponent? = null

        fun get(): CoreNetworkComponent {
            if (coreNetworkComponent == null) {
                synchronized(CoreNetworkComponent::class.java) {
                    if (coreNetworkComponent == null) {
                        coreNetworkComponent = DaggerCoreNetworkComponent
                            .builder()
                            .build()
                    }
                }
            }
            return coreNetworkComponent!!
        }
    }
}