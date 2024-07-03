package com.stan.checker.usage

// TODO: Move to Firebase or something like this
class NamingHelperImpl : NamingHelper {

    override fun resolvePackageName(packageName: String): String? {
        return when(packageName) {
            "com.android.chrome" -> "Chrome"
            "com.google.android.youtube" -> "Youtube"
            "com.google.android.youtube.music" -> "Youtube Music"
            "com.google.android.gm" -> "Gmail"
            else -> null
        }
    }
}