package com.reelsblock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val pad = (24 * resources.displayMetrics.density).toInt()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(pad, pad, pad, pad)
            fitsSystemWindows = true
        }

        status = TextView(this).apply { textSize = 18f; setPadding(0, pad, 0, pad) }
        root.addView(status)

        root.addView(Button(this).apply {
            text = "অ্যাক্সেসিবিলিটি সেটিংস খুলুন"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        })

        fun toggle(label: String, key: String) = Switch(this).apply {
            text = label
            textSize = 16f
            isChecked = prefs.getBoolean(key, true)
            setPadding(0, pad / 2, 0, pad / 2)
            setOnCheckedChangeListener { _, on -> prefs.edit().putBoolean(key, on).apply() }
        }
        root.addView(toggle("ইনস্টাগ্রাম রিলস ব্লক", "ig"))
        root.addView(toggle("ইউটিউব শর্টস ব্লক", "yt"))

        setContentView(root)
    }

    override fun onResume() {
        super.onResume()
        val enabled = Settings.Secure.getString(contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            ?.contains(packageName) == true
        status.text = if (enabled) "✅ ব্লকার চালু আছে" else "⚠️ ব্লকার বন্ধ — নিচের বাটনে গিয়ে চালু করুন"
    }
}
