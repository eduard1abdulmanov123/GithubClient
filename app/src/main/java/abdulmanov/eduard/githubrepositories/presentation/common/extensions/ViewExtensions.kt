package abdulmanov.eduard.githubrepositories.presentation.common.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imageUrl: String, placeholderRes: Int? = null, errorRes: Int? = null, callback: Callback? = null) {
    if (imageUrl.isEmpty()) {
        this.setImageResource(errorRes ?: 0)
        return
    }

    Picasso.get().load(imageUrl).apply {
        fit()
        centerCrop()
        placeholderRes?.let { placeholder(it) }
        errorRes?.let { error(it) }
        callback?.let { into(this@loadImg, callback) } ?: into(this@loadImg)
    }
}