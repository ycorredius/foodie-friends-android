package com.example.foodiefriends

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.io.File

private val prefsName = "foodie-friends-prefs"
private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
fun master() = masterKey
object Key{
	const val accessToken = "accessToken"
	const val userName = "userName"
	const val userEmail = "userEmail"
	const val userPhoto = "userPhoto"
}

fun sharedPrefs(context: Context): SharedPreferences {
	return try {
		createPrefs(context)
	} catch (e: Exception) {
		printMsg("Exception occurred attempting to get/create shared preferences: $e")

		// If there is an exception it is likely due to inability to decrypt the user prefs, which
		// means the app may have gotten backed up, uninstalled, then reinstalled, or updated.
		// Solution is to remove the old preferences file and let the user start over / logged out.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			printMsg("Attempting to work around it be deleting the encrypted shared preferences and the user can start over.")
			context.deleteSharedPreferences(prefsName)
		} else {
			printMsg("Manually deleting encrypted shared prefs to work around exception because the user is not yet on Android version 'N' v7.0.")
			printMsg("If this doesn't work, user must manually clear data for the FieldWare app.")
			try {
				val file = File(context.filesDir.parentFile, "shared_prefs/$prefsName.xml")
				printMsg("Attempting to delete shared preferences file at ${file.path}")
				if (file.exists()) {
					val result = file.delete()
					printMsg("Shared preferences manual delete successful? $result")
				}
			} catch (e1: Exception) {
				printMsg("Error occurred trying to manually delete encrypted shared preferences: $e1")
			}
		}
		printMsg("Recreating shared preferences")
		createPrefs(context)
	}
}

fun accessToken(context: Context) = sharedPrefs(context).string(Key.accessToken)

fun hasAccessToken(context: Context) = accessToken(context).isNotEmpty()

fun headerValue(context: Context) = "Bearer ${accessToken(context)}"

private fun createPrefs(context: Context): SharedPreferences{
	return EncryptedSharedPreferences.create(
		prefsName,
		master(),
		context,
		EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
		EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
	)
}

fun SharedPreferences.putString(key: String, value: String) = edit().putString(key,value).apply()

fun SharedPreferences.string(key: String) = getString(key,"").orEmpty()

const val TAG = "FF"

fun printMsg(message: String) {
	Log.d(TAG, message)
}