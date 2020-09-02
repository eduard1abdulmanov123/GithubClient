package abdulmanov.eduard.githubrepositories.presentation

import abdulmanov.eduard.githubrepositories.dagger.components.AppComponent
import abdulmanov.eduard.githubrepositories.dagger.components.DaggerAppComponent
import android.app.Application

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}