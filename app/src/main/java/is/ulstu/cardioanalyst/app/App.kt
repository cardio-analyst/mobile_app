package `is`.ulstu.cardioanalyst.app

import android.app.Application
import com.example.common.Singletons
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Singletons.init(appContext = baseContext)
    }

}