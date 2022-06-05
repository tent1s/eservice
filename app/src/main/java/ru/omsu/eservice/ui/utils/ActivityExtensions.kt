package ru.omsu.eservice.ui.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Browser
import android.view.inputmethod.InputMethodManager
import androidx.browser.customtabs.CustomTabsIntent
import ru.omsu.eservice.R

fun Activity.hideKeyboard() {
    val inputManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        inputManager.hideSoftInputFromWindow(
            it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
        )
        it.clearFocus()
    }
}

fun Activity.launchUrl(uri: Uri, accessToken: String? = null) {
    val color = color(R.color.white)
    val builder = CustomTabsIntent.Builder().apply {
        setToolbarColor(color)
        setShowTitle(true)
    }
    val customTabsIntent = builder.build()

    val packageName = CustomTabsHelper.getPackageNameToUse(this, uri)
    val intent = customTabsIntent.intent.apply {
        data = uri
        if (packageName != null) setPackage(packageName)
        if (accessToken != null) includeAuthToken(accessToken)
    }
    startActivity(intent)
}

private fun <T : Intent> T.includeAuthToken(accessToken: String) {
    val headers = Bundle()
    //headers.putString(AuthHeaderKeys.HEADER_KEY, "${AuthHeaderKeys.TOKEN_TYPE} $accessToken")
    putExtra(Browser.EXTRA_HEADERS, headers)
}
