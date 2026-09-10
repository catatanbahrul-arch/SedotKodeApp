package com.sedotkode.app

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.Toast

class PasteService : AccessibilityService() {
    private lateinit var windowManager: WindowManager
    private lateinit var btnPaste: Button

    override fun onServiceConnected() {
        super.onServiceConnected()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        btnPaste = Button(this).apply {
            text = "📋 Paste"
            setBackgroundColor(Color.parseColor("#16A34A"))
            setTextColor(Color.WHITE)
            textSize = 16f
            setPadding(30, 20, 30, 20)
            setOnClickListener { suntikkanTeks() }
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.END
            x = 50
            y = 500
        }
        try { windowManager.addView(btnPaste, params) } catch (e: Exception) {}
    }

    private fun suntikkanTeks() {
        val prefs = getSharedPreferences("BrankasKode", Context.MODE_PRIVATE)
        val teksTersimpan = prefs.getString("kode_tersimpan", "")
        if (teksTersimpan.isNullOrEmpty()) {
            Toast.makeText(this, "Brankas kosong! Copy teks dulu.", Toast.LENGTH_SHORT).show()
            return
        }
        val rootNode = rootInActiveWindow
        val kotakKetik = rootNode?.findFocus(AccessibilityNodeInfo.FOCUS_INPUT)
        if (kotakKetik != null && kotakKetik.isEditable) {
            val argument = Bundle()
            argument.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, teksTersimpan)
            kotakKetik.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, argument)
            Toast.makeText(this, "✅ Boom! Teks ter-paste", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sentuh area kotak ketik terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}
    override fun onDestroy() {
        super.onDestroy()
        if (::btnPaste.isInitialized) {
            try { windowManager.removeView(btnPaste) } catch (e: Exception) {}
        }
    }
}
