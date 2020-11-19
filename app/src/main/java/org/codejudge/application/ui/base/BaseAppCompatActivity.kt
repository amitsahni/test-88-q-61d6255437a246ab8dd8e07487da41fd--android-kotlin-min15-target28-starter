package org.codejudge.application.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.codejudge.application.databinding.ActivityBaseBinding


abstract class BaseAppCompatActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBaseBinding.inflate(layoutInflater).apply {
            containerView.addView(layout())
        }
    }

    val bundle by lazy {
        intent.extras ?: Bundle()
    }

    /***
     *  Add UI View
     */
    abstract fun layout(): View

    /**
     *  Observe LiveData/Flow
     */
    open fun observeLiveData() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.root.hideKeyboard()
        observeLiveData()
    }

    override fun onPause() {
        super.onPause()
        binding.root.hideKeyboard()
    }

    fun showLoader() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        binding.progress.visible()
    }

    fun hideLoader() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding.progress.gone()
    }

    fun snackBar(msg: String) {
        binding.root.snackBar(msg)
    }

    fun snackBar(@StringRes resID: Int) {
        binding.root.snackBar(resID)
    }

}

fun AppCompatActivity.showLoader() {
    castAs<BaseAppCompatActivity> {
        showLoader()
    }
}

fun AppCompatActivity.hideLoader() {
    castAs<BaseAppCompatActivity> {
        hideLoader()
    }
}

fun AppCompatActivity.snackBar(msg: String) {
    castAs<BaseAppCompatActivity> {
        snackBar(msg)
    }
}

fun AppCompatActivity.snackBar(@StringRes resID: Int) {
    castAs<BaseAppCompatActivity> {
        snackBar(resID)
    }
}

inline fun <reified T> Any.castAs(f: T.() -> Unit) {
    if (this is T) {
        f(this)
    }
}

fun View.snackBar(@StringRes resId: Int) = Snackbar.make(this, resId, Snackbar.LENGTH_SHORT).show()
fun View.snackBar(message: String?) {
    message?.let {
        Snackbar.make(this, it, Snackbar.LENGTH_SHORT).show()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * Try to hide the keyboard and returns whether it worked
 */
fun View.hideKeyboard(): Boolean {
    return try {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
        false
    }

}