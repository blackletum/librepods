/*
    LibrePods - AirPods liberated from Apple’s ecosystem
    Copyright (C) 2025 LibrePods contributors

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package me.kavishdevar.librepods.services

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("AccessibilityPolicy")
class LibrePodsAccessibilityService: AccessibilityService() {
    companion object {
        val instance = MutableStateFlow(null as LibrePodsAccessibilityService?)

        val isCameraOpen = MutableStateFlow(false)

        val cameraPackages = mutableSetOf(
            "com.google.android.GoogleCamera",
            "com.sec.android.app.camera",
            "com.android.camera",
            "com.oppo.camera",
            "com.motorola.camera2",
            "org.codeaurora.snapcam"
        )
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance.value = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        try {
            if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                val packageName = event.packageName?.toString() ?: return
                val isCamera = cameraPackages.contains(packageName) || packageName.contains("camera", ignoreCase = true)
                isCameraOpen.value = isCamera
            }
        } catch (e: Exception) {
            Log.e("OptionalAccessibility", "Error handling accessibility event", e)
        }
    }

    override fun onInterrupt() {}

    override fun onUnbind(intent: Intent?): Boolean {
        instance.value = null
        isCameraOpen.value = false
        return super.onUnbind(intent)
    }
}
