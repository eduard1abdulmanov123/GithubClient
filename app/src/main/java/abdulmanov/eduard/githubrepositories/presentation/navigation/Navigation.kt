package abdulmanov.eduard.githubrepositories.presentation.navigation

import abdulmanov.eduard.githubrepositories.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Navigation {

    val fragmentManager: FragmentManager

    fun replaceFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.containerForFragments, fragment)
        }.commit()
    }

    fun forwardFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            replace(R.id.containerForFragments, fragment)
            addToBackStack(fragment.toString())
        }.commit()
    }

    fun fragmentBack() {
        fragmentManager.popBackStack()
    }
}