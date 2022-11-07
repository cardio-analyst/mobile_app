package `is`.ulstu.cardioanalyst.app

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Singletons.init(appContext = baseContext)
    }
}