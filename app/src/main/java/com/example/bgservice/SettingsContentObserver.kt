package com.example.bgservice

import android.content.Context
import android.database.ContentObserver
import android.media.AudioManager
import android.os.Handler
import android.widget.Toast


class SettingsContentObserver(private val context: Context, handler: Handler?) :
    ContentObserver(handler) {

    private var mAudioManager: AudioManager? = null
    private var previousVolume: Int

    init {
        mAudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        previousVolume = mAudioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        if (mAudioManager != null) {
            val currentVolume = mAudioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)
            val delta = previousVolume - currentVolume
            if (delta > 0) {
                log("Volume Decreased")
                Toast.makeText(context, "Volume Decreased", Toast.LENGTH_SHORT).show()
                previousVolume = currentVolume
            } else if (delta < 0) {
                log("Volume Increased")
                Toast.makeText(context, "Volume Increased", Toast.LENGTH_SHORT).show()
                previousVolume = currentVolume
            }
        }
    }
}