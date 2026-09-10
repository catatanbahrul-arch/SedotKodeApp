package com.sedotkode.app

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast

class SedotKeyboardService : InputMethodService() {
    override fun onCreateInputView(): View {
        val view = layoutInflater.inflate(R.layout.keyboard_view, null)

        val btnPaste = view.findViewById<Button>(R.id.btn_paste_keyboard)
        val btnSwitch = view.findViewById<Button>(R.id.btn_switch_keyboard)

        btnPaste.setOnClickListener {
            val prefs = getSharedPreferences("BrankasKode", Context.MODE_PRIVATE)
            val teksTersimpan = prefs.getString("kode_tersimpan", "")

            if (!teksTersimpan.isNullOrEmpty()) {
                // Mengetik teks seolah-olah pakai keyboard asli
                currentInputConnection?.commitText(teksTersimpan, 1)
            } else {
                Toast.makeText(this, "Brankas kosong! Salin teks dulu.", Toast.LENGTH_SHORT).show()
            }
        }

        btnSwitch.setOnClickListener {
            // Memunculkan pop-up untuk kembali ke Gboard / Keyboard asli
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showInputMethodPicker()
        }

        return view
    }
}
