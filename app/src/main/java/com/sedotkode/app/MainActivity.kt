package com.sedotkode.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var teksMasuk: String? = null

        // JALUR 1: Tangkap dari opsi Titik Tiga (Context Menu)
        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            teksMasuk = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()
        }
        // JALUR 2: Tangkap dari opsi Bagikan (Share)
        else if (intent.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            teksMasuk = intent.getStringExtra(Intent.EXTRA_TEXT)
        }

        // Simpan teks jika ada yang masuk
        if (!teksMasuk.isNullOrEmpty()) {
            val prefs = getSharedPreferences("BrankasKode", Context.MODE_PRIVATE)
            prefs.edit().putString("kode_tersimpan", teksMasuk).apply()
            Toast.makeText(this, "✅ Teks berhasil disedot!", Toast.LENGTH_SHORT).show()
        } else {
            // Jika aplikasi dibuka biasa dari ikon layar utama
            if (intent.action == Intent.ACTION_MAIN) {
                Toast.makeText(this, "Sedot Kode Aktif! Blok teks atau Bagikan teks ke aplikasi ini.", Toast.LENGTH_LONG).show()
            }
        }

        // Langsung tutup activity agar layar kembali ke aplikasi sebelumnya tanpa jeda
        finish()
    }
}
