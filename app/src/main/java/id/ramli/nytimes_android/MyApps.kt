package id.ramli.nytimes_android

import androidx.multidex.MultiDexApplication

/**
 * Created by ramliy10 on 18/11/20.
 */
class MyApps : MultiDexApplication() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApps? = null

        fun applicationContext() : MyApps {
            return instance as MyApps
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}