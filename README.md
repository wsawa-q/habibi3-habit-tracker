# Habibi 3 - Habits Tracker App

## Project Overview

**Name:** Habibi 3  
**Platform:** Android  
**Programming Language:** Kotlin  
**UI Framework:** Jetpack Compose  
**Local Data Storage:** Room Database  
**Background Tasks:** WorkManager  
**Asynchronous Operations:** Kotlin Coroutines  
**Creators:** Mariia Chinkova, Yana Hrynevich, Valeriia Kurinna

Habibi 3 is a simple yet effective habit tracking app designed to help users monitor and maintain their daily and weekly habits. It features a user-friendly interface for adding, editing, and deleting habits, along with a calendar view for tracking progress over time. The app ensures consistency by allowing habits to reset daily or weekly.

## Main Features

- **Habit Management:** Add, edit, and delete habits with ease.
- **Calendar View:** Visualize your habit progress over time.
- **Reset Functionality:** Daily and weekly reset options to keep habits consistent.
- **Notifications:** Schedule reminders for habit resets using WorkManager.

## Application Structure

The project is structured into several packages, each serving a specific purpose:

- **Main Package:** `com.example.habibi_3`
- **Database Package:** Contains all files related to the Room Database.
- **UI Package:** Manages the user interface and design elements.

## Setup Instructions

### Environment Setup

1. Ensure you have the latest Android Studio installed with Kotlin support.
2. Open Android Studio, select "Open an Existing Project," and navigate to the project directory.
3. Ensure all dependencies are correctly specified in your `build.gradle` files.
4. Select an Android emulator or connect a device, then build and run the project.

## Key Components

### MainActivity
The entry point of the app, setting up the main user interface and periodic work requests for resetting daily and weekly habits.

### DAO Interfaces
Defines the Data Access Object (DAO) interfaces using Room Persistence Library for database operations.

- **HabitDao:** Manages CRUD operations for Habit entities.
- **HabitLogDao:** Manages CRUD operations for HabitLog entities.

### Database
Defines the Room database with entity definitions and type converters for handling custom types.

### MyApp
Sets up the navigation architecture, allowing transitions between different screens within the app.

### Repositories
Abstracts the data source layer, providing a clean API for the ViewModel to interact with the underlying data.

### CalendarUiModel
Provides calendar-related features, such as displaying a calendar view and showing habit logs tied to specific dates.

### UIAddHabitScreen
Defines the Add Habit screen for creating new habits with details such as name and frequency.

### UIEditHabitScreen
Defines the Edit Habit screen for modifying existing habits and provides options to save changes or delete habits.

### UIMainScreen
Displays a list of habits categorized by their frequency and provides navigation options to other parts of the app.

### UISettingsScreen
Defines the Settings screen with options to reset daily or weekly habits.

### ViewModels
Manages UI-related data and business logic, interacting with repositories to perform database operations.

### Workers
Defines worker classes for performing background tasks such as resetting the completion status of habits.

## Usage Instructions

- **Main Screen:** View and mark your daily and weekly habits as done or undone.
- **Add Habit:** Create a new habit by specifying its name and frequency.
- **Edit Habit:** Modify existing habits by tapping on them.
- **Calendar View:** Access the calendar to view or add habit logs.
- **Settings:** Reset all daily or weekly habits through the settings screen.

---

For more detailed information about each component and their functionalities, refer to the specific sections in the project documentation.
