# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
./gradlew assembleDebug          # Build debug APK
./gradlew assembleRelease        # Build release APK
./gradlew test                   # Run unit tests (JVM)
./gradlew connectedAndroidTest   # Run instrumented tests (requires device/emulator)
./gradlew app:testDebugUnitTest --tests "com.rasugames.ExampleUnitTest"  # Run single test class
```

## Architecture

**Burning Fruits** is a fruit-matching puzzle game for Android.

- **Language:** Kotlin, **UI:** Jetpack Compose with Material 3
- **Package:** `com.rasugames`
- **Min SDK:** 28, **Target/Compile SDK:** 36

### Activity Structure

- **LoadingActivity** — Launcher/splash screen (entry point)
- **MainActivity** — Main game UI

Both activities use Compose (`setContent`). Theme is defined in `app/src/main/java/com/rasugames/ui/theme/` with dynamic color support (Android 12+).

### Key Paths

- Source: `app/src/main/java/com/rasugames/`
- Resources: `app/src/main/res/` (fruit sprites, UI buttons, backgrounds in `drawable/`)
- Version catalog: `gradle/libs.versions.toml`
