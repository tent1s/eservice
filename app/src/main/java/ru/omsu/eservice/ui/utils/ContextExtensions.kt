package ru.omsu.eservice.ui.utils

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.core.content.ContextCompat

fun Context.getAnimation(@AnimRes res: Int): Animation = AnimationUtils.loadAnimation(this, res)

fun Context.color(resId: Int) = ContextCompat.getColor(this, resId)