# Reels Block

ইনস্টাগ্রাম রিলস ও ইউটিউব শর্টস খুললেই অটো "ব্যাক" করে দেয় (Accessibility Service)। কোনো ইন্টারনেট/ব্যাটারি-আনরেস্ট্রিক্টেড পারমিশন লাগে না।

## GitHub থেকে APK বানানো
1. এই ফাইলগুলো আপনার GitHub রিপোজিটরির রুটে আপলোড করুন (`.github/workflows/build.yml` সহ)।
2. রিপোজিটরির **Actions** ট্যাবে যান → **Build APK** → ডানে **Run workflow** (বা কমিট করলেই নিজে চলবে)।
3. ৩-৫ মিনিট পর সবুজ টিক এলে রানটা খুলুন → নিচে **Artifacts** থেকে `ReelsBlock-apk` ডাউনলোড করুন।
4. zip খুলে `app-debug.apk` ফোনে ইনস্টাল করুন।

## ফোনে চালু করা
1. Settings → Apps → Reels Block → ⋮ → **Allow restricted settings**
2. অ্যাপ খুলে বাটনে চাপ দিয়ে Settings → Accessibility → **Reels Block** চালু করুন।
