package `is`.ulstu.cardioanalyst.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Singletons.init(appContext = baseContext)
    }

}