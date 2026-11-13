# 🧩 Color Link - Dot Connect Logic Puzzle

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)](LICENSE)

**Color Link** is a modern, high-performance Android logic puzzle game built entirely with **Kotlin** and **Jetpack Compose**. Players connect matching color dots across various grid sizes without crossing lines, aiming to achieve 100% board coverage for a 3-star victory!

---

## 📱 Features

- 🎮 **40 Handcrafted Solvable Levels**: Divided into 4 difficulty tiers:
  - **Beginner (5x5)**: 10 levels (4–5 color pairs)
  - **Intermediate (6x6)**: 10 levels (5–6 color pairs)
  - **Advanced (7x7)**: 10 levels (6–7 color pairs)
  - **Expert (8x8)**: 10 levels (7–8 color pairs)
- 🌓 **Dynamic Light & Dark Themes**: Persistent theme preference with seamless UI adaptation.
- ✋ **Tactile Haptic Feedback**: Vibrations on cell dragging and level victory.
- 💡 **Smart Ground-Truth Hint System**: Displays exact solution paths while dynamically trimming conflicting lines.
- 🔄 **Undo & Reset Controls**: Unlimited backtrack undo history and board reset.
- 🚀 **Performance & Size Optimized**: Configured with R8 code shrinking and resource minification for production releases.
- 🔒 **Privacy First & 100% Offline**: Zero analytics, zero data collection, no ads.

---

## 🛠️ Tech Stack & Architecture

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture:** Unidirectional Data Flow (UDF) with MVVM (`StateFlow`, `GameViewModel`)
- **Graphics & Rendering:** Custom Compose Canvas with smooth pipe line glow effects and rounded tiles
- **Persistence:** Local `SharedPreferences` via `ProgressRepository`
- **Testing:** JUnit 4 unit tests covering 100% level solvability and board coverage algorithms

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio:** Ladybug (2024.2.1) or newer
- **JDK:** 11 or higher
- **Android SDK:** Compile SDK 37, Min SDK 24

### Building & Running

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/color-link.git
   cd color-link
   ```

2. **Open in Android Studio:**
   - Open Android Studio and select `Open an existing project`.
   - Select the cloned `color-link` directory.

3. **Run Unit Tests:**
   ```bash
   ./gradlew testDebugUnitTest
   ```

4. **Build Release Android App Bundle (.aab):**
   ```bash
   ./gradlew bundleRelease
   ```

---

## 📂 Project Structure

```text
com.tariqnaeemdeveloper.colorlink
├── data/
│   ├── LevelRepository.kt      # 40 handcrafted level definitions & ground-truth paths
│   └── ProgressRepository.kt   # Local star ratings, progress, and theme settings
├── engine/
│   └── ColorLinkEngine.kt      # Core game logic, pathfinder, collision & hint solver
├── model/
│   ├── ColorPair.kt
│   ├── Difficulty.kt           # EASY (5x5), MEDIUM (6x6), HARD (7x7), EXPERT (8x8)
│   ├── DotColor.kt
│   ├── Level.kt
│   ├── PathState.kt
│   └── Position.kt
├── ui/
│   ├── components/
│   │   ├── ColorLinkBoard.kt   # Canvas rendering for grid, glow pipes, and dots
│   │   ├── HeaderBar.kt        # Moves, coverage, timer, undo, hint, reset controls
│   │   └── VictoryDialog.kt    # Animated star victory dialog
│   ├── screens/
│   │   ├── GameScreen.kt
│   │   ├── HomeScreen.kt        # Main screen, difficulty picker, theme toggle, dialogs
│   │   ├── LevelSelectScreen.kt
│   │   └── SplashScreen.kt     # Animated launch splash screen with logo
│   ├── theme/                  # Material 3 light/dark color schemes & typography
│   └── viewmodel/
│       └── GameViewModel.kt    # Game state management
└── MainActivity.kt             # Navigation host & edge-to-edge container
```

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
