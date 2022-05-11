package ru.omsu.eservice.ui.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun View.hide(collapse: Boolean = true) {
    visibility = if (collapse) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setVisible(isVisible: Boolean) {
    if (isVisible) show()
    else hide()
}

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