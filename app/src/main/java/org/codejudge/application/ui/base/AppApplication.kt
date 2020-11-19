package org.codejudge.application.ui.base

import android.app.Application
import org.codejudge.application.di.dependency
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class AppApplication  : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(LineNumberDebugTree())
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@AppApplication)
            koin.loadModules(dependency())
            koin.createRootScope()
        }
    }
}
class LineNumberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.fileName}:${element.lineNumber}:${element.methodName}"
    }
}