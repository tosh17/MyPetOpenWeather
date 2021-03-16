package ru.thstdio.feature_detail.impl.framework.di

import android.util.Log
import ru.thstdio.feature_detail.api.DetailFeatureApi
import ru.thstdio.module_injector.ComponentHolder

object DetailFeatureComponentHolder : ComponentHolder<DetailFeatureApi, DetailFeatureDependencies> {
    private var detailFeatureComponent: DetailFeatureComponent? = null
    private const val TAG = "DetailComponentHolder"

    override fun init(dependencies: DetailFeatureDependencies) {
        Log.i(TAG, "init()")
        if (detailFeatureComponent == null) {
            synchronized(DetailFeatureComponentHolder::class.java) {
                if (detailFeatureComponent == null) {
                    Log.i(TAG, "initAndGet()")
                    detailFeatureComponent = DetailFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): DetailFeatureApi = getComponent()

    internal fun getComponent(): DetailFeatureComponent {
        checkNotNull(DetailFeatureComponent) { "DetailFeatureComponent was not initialized!" }
        return detailFeatureComponent!!
    }

    override fun reset() {
        Log.i(TAG, "reset()")
        detailFeatureComponent = null
    }

}