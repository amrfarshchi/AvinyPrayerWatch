# AvinyPrayerWatch (Wear OS) – Scaffold

این اسکفولد حداقلی است تا بدون Android Studio و حتی بدون Gradle Wrapper،
در GitHub Actions یک APK ریلیز امضاشده بگیرید.

## مراحل سریع
1) این پروژه را در یک ریپو Private بگذارید.
2) در GitHub → Settings → Secrets → Actions چهار Secret بسازید:
   - `KEYSTORE_BASE64`، `KEYSTORE_PASSWORD`، `KEY_ALIAS`، `KEY_PASSWORD`
3) از تب Actions ورک‌فلو **WearOS Release APK (No-Wrapper)** را Run کنید.
4) Artifact → `app-release-apk` را دانلود کنید (داخلش `app-release.apk`).

## نصب روی گلکسی واچ ۷
```bash
adb pair WATCH_IP:PAIRING_PORT
adb connect WATCH_IP:DEBUG_PORT
adb install app/build/outputs/apk/release/app-release.apk
```

> اگر signature conflict یا downgrade گرفتید:
```bash
adb uninstall ir.meshkat.avinywatch
adb install app/build/outputs/apk/release/app-release.apk
```
