package `is`.ulstu.cardioanalyst

import `is`.ulstu.cardioanalyst.models.diseases.DiseasesRAMRepository
import `is`.ulstu.cardioanalyst.models.users.UserRAMRepository
import `is`.ulstu.foundation.BaseApplication
import `is`.ulstu.foundation.model.Repository
import android.app.Application
import android.content.res.Resources

class App : Application(), BaseApplication {

    override val repositories: List<Repository> = listOf(
        UserRAMRepository(),
        DiseasesRAMRepository()
    )

    override fun onCreate() {
        super.onCreate()
        appResources = resources
    }

    companion object {
        lateinit var appResources: Resources
    }
}