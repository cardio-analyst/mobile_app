package `is`.ulstu.foundation

import `is`.ulstu.foundation.model.Repository

interface BaseApplication {
    val repositories: List<Repository>
}