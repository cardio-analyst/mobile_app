package `is`.ulstu.cardioanalyst

import `is`.ulstu.foundation.BaseApplication
import `is`.ulstu.foundation.model.Repository
import android.app.Application

class App : Application(), BaseApplication {
    override val repositories: List<Repository> = listOf<Repository>(

    )
}