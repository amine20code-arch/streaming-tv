package com.streamtv.iptv

import android.app.Application

class StreamApp : Application() {
    companion object {
        lateinit var db: AppDb
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = AppDb.get(this)
    }
}
