package ru.omsu.eservice.ui.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.annotation.AnimRes
import androidx.core.content.ContextCompat

fun View.hide(collapse: Boolean = true) {
    visibility = if (collapse) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun Context.getAnimation(@AnimRes res: Int): Animation = AnimationUtils.loadAnimation(this, res)

fun Context.color(resId: Int) = ContextCompat.getColor(this, resId)

fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(trim()).matches()

fun EditText.afterTextChangedInFocus(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, start: Int, before: Int, after: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, after: Int) {
            if (hasFocus()) {
                afterTextChanged.invoke(charSequence.toString())
            }
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}