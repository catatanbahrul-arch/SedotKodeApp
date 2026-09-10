package com.sedotkode.app
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textMasuk = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        if (textMasuk != null) {
            val prefs = getSharedPreferences("BrankasKode", Context.MODE_PRIVATE)
            prefs.edit().putString("kode_tersimpan", textMasuk.toString()).apply()
            Toast.makeText(this, "✅ Ribuan baris kode berhasil diamankan!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        mintaIzin()
    }
    private fun mintaIzin() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "Izinkan 'Tampil di atas aplikasi lain'", Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
            return
        }
        Toast.makeText(this, "Aktifkan Layanan Aksesibilitas", Toast.LENGTH_LONG).show()
        startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        finish()
    }
}
