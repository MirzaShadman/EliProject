package com.demo.eliproject

import android.app.Application
import android.content.Context

class MainApplication: Application() {
    
    init {
        instance = this
    }
    
    
    override fun onCreate() {
        super.onCreate()
    }
    
    companion object {
        private var instance: MainApplication? = null
        @JvmStatic
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}