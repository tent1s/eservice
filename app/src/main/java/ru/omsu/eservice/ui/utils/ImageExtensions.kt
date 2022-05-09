package ru.omsu.eservice.ui.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: Uri) {
    Glide
        .with(this.context)
        .load(url)
        .dontAnimate()
        .into(this)
}

fun ImageView.load(drawable: Drawable) {
    Glide
        .with(this.context)
        .load(drawable)
        .into(this)
}