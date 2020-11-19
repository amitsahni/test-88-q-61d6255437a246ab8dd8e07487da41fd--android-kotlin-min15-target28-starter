@file:JvmName("StringUtils")

package nishan.softient.domain.extension

import android.text.Editable
import android.util.Patterns
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import java.util.*


object G {
    val gson = GsonBuilder()
        .disableHtmlEscaping()
        .setPrettyPrinting()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()
    val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        isLenient = true
    }
}

/**
 * Converts string to integer safely otherwise returns zero
 */
fun String.asInt(): Int = toInt().or(-1)

fun String.asBoolean(): Boolean = toBoolean().or(false)

val Int.isNegative get() = this < 0

val Boolean.intValue get() = if (this) 1 else 0


val String.containsLetters get() = matches(".*[a-zA-Z].*".toRegex())

val String.containsNumbers get() = matches(".*[0-9].*".toRegex())

val String.isAlphanumeric get() = matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$".toRegex())

val String.isAlphabetic get() = matches("^[a-zA-Z]*$".toRegex())

fun String.isEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

fun join(vararg params: Any?) = params.joinToString()

fun Any.toJson(): String = try {
    G.gson.toJson(this)
} catch (e: Exception) {
    e.printStackTrace()
    ""
}

inline fun <reified T : Any> String?.fromJson() = G.gson.fromJson<T>(this, T::class.java)

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun <T> String?.fromTypedJson(): T? {
    val type = object : TypeToken<T>() {

    }.type
    return try {
        G.gson.fromJson<T>(this, type)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun <T> Any?.toTypedJson(): String {
    val type = object : TypeToken<T>() {

    }.type
    return G.gson.toJson(this, type)
}

inline fun <reified T> T.deepCopy(): T {
    val stringProject = G.gson.toJson(this, T::class.java)
    return G.gson.fromJson(stringProject, T::class.java)
}

fun String.upperCase(locale: Locale = Locale.getDefault()) = toUpperCase(locale)



