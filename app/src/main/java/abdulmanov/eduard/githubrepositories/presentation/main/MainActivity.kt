package abdulmanov.eduard.githubrepositories.presentation.main

import abdulmanov.eduard.githubrepositories.R
import abdulmanov.eduard.githubrepositories.presentation.githubreps.GithubRepsFragment
import abdulmanov.eduard.githubrepositories.presentation.navigation.Navigation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity(), Navigation {

    override val fragmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setBackgroundDrawable(null)

        if (savedInstanceState == null) {
            val fragment = GithubRepsFragment.newInstance()
            replaceFragment(fragment)
        }
    }
}