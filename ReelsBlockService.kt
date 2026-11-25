package com.reelsblock

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

class ReelsBlockService : AccessibilityService() {

    private var lastBlockAt = 0L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val pkg = event?.packageName?.toString() ?: return
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val root = rootInActiveWindow ?: return

        val inReels = when (pkg) {
            "com.instagram.android" -> prefs.getBoolean("ig", true) && isInstagramReels(root)
            "com.google.android.youtube" -> prefs.getBoolean("yt", true) && isYoutubeShorts(root)
            else -> false
        }
        if (inReels) block()
    }

    // ইনস্টাগ্রামের Reels viewer বা নিচের Reels ট্যাব সিলেক্টেড থাকলে true
    private fun isInstagramReels(root: AccessibilityNodeInfo): Boolean {
        val viewerIds = listOf(
            "com.instagram.android:id/clips_viewer_view_pager",
            "com.instagram.android:id/clips_viewer_container",
            "com.instagram.android:id/root_clips_layout"
        )
        if (viewerIds.any { hasNode(root, it) }) return true
        return root.findAccessibilityNodeInfosByViewId("com.instagram.android:id/clips_tab")
            .any { it.isSelected }
    }

    private fun isYoutubeShorts(root: AccessibilityNodeInfo): Boolean {
        val ids = listOf(
            "com.google.android.youtube:id/reel_recycler",
            "com.google.android.youtube:id/reel_player_page_container"
        )
        return ids.any { hasNode(root, it) }
    }

    private fun hasNode(root: AccessibilityNodeInfo, id: String) =
        root.findAccessibilityNodeInfosByViewId(id).isNotEmpty()

    private fun block() {
        val now = System.currentTimeMillis()
        if (now - lastBlockAt < 1000) return
        lastBlockAt = now
        performGlobalAction(GLOBAL_ACTION_BACK)
        Toast.makeText(this, "রিলস ব্লক করা হয়েছে", Toast.LENGTH_SHORT).show()
    }

    override fun onInterrupt() {}
}
