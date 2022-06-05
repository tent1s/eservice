package ru.omsu.eservice.ui.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.browser.customtabs.CustomTabsService


//https://stackoverflow.com/questions/34328814/android-open-chrome-custom-tab-from-fragment
class CustomTabsHelper {

    companion object {

        private const val STABLE_PACKAGE = "com.android.chrome"
        private const val BETA_PACKAGE = "com.chrome.beta"
        private const val DEV_PACKAGE = "com.chrome.dev"
        private const val LOCAL_PACKAGE = "com.google.android.apps.chrome"

        @JvmStatic
        fun getPackageNameToUse(context: Context, uri: Uri): String? {
            val packageManager = context.packageManager;
            // Get default VIEW intent handler.
            val activityIntent = Intent(Intent.ACTION_VIEW, uri)

            // Get all apps that can handle VIEW intents.
            val resolvedActivityList = packageManager.queryIntentActivities(activityIntent, PackageManager.MATCH_ALL);
            val packagesSupportingCustomTabs = mutableListOf<String>()
            resolvedActivityList.forEach {
                val serviceIntent = Intent()
                serviceIntent.action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION;
                serviceIntent.setPackage(it.activityInfo.packageName);
                if (packageManager.resolveService(serviceIntent, 0) != null) {
                    packagesSupportingCustomTabs.add(it.activityInfo.packageName);
                }
            }

            return when {
                packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> STABLE_PACKAGE
                packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> BETA_PACKAGE
                packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> DEV_PACKAGE
                packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> LOCAL_PACKAGE
                packagesSupportingCustomTabs.size == 1 -> packagesSupportingCustomTabs[0]
                else -> null
            }
        }
    }
}