package ru.omsu.eservice.ui.utils

import android.util.Patterns

fun String.isEmail() = Patterns.EMAIL_ADDRESS.matcher(trim()).matches()