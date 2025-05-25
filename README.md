# Restaurant Reviews

## Table of contents
- [About](#about)
    - [Key features](#key-features)
    - [Backend](#backend)
- [Getting started](#getting-started)
    - [Dependencies](#dependencies)
    - [Dev setup step-by-step](#dev-setup-step-by-step)


## About

Restaurant Reviews is a mobile application developed using Android Studio and Jetpack Compose. This project was created as part of the Advanced Mobile Development course at Lapland University of Applied Sciences.

The application allows users to browse restaurants and their reviews, providing functionalities such as viewing restaurant details and deleting reviews. It serves as a practical implementation of modern Android development practices.

With this exercise we practiced:
- Building declarative UIs using **Jetpack Compose**
- Managing UI state and business logic with **ViewModels**
- Applying **MVVM (Model-View-ViewModel)** architectural pattern
- Implementing **Dependency Injection** using **Dagger Hilt**
- Consuming RESTful APIs with **Retrofit**
- Parsing JSON data using **Gson Converter**
- Displaying images asynchronously with **Coil for Compose**
- Handling lifecycle-aware state with **Lifecycle ViewModel and SavedState**
- Navigating between screens using **Jetpack Navigation for Compose**
- Writing unit tests and coroutine-based tests using **Kotlin Coroutines Test**

### Key Features
- **Restaurant card**: Fetch and display restaurant information from the backend as interactive cards
- **Reviews**: Retrieve and display restaurant reviews with the ability to delete individual reviews.
- **Navigation**: Seamless navigation between different screens using Jetpack Compose Navigation.

### Backend

The backend for this application is implemented in Python and is available at [here](https://github.com/Iinaus/restaurant_review_backend). The backend was provided as part of the course and it follows a modular architecture with clearly separated components. It uses SQLite as the database and includes the following utility scripts.

## Getting started

Follow these instructions to set up and run the project on your local machine.

### Dependencies

Ensure you have the following installed:

- **Android Studio**: Latest stable version.
- **Kotlin**: Used as the primary programming language.
- **Jetpack Compose**: Android’s modern toolkit for building native UI.
- **Dagger Hilt**: For dependency injection.
- **Retrofit**: For interacting with RESTful APIs.
- **Gson Converter**: To serialize and deserialize JSON data.
- **Coil (Coil-Compose)**: For image loading.
- **Kotlin Coroutines Test**: For testing asynchronous code.
- **Lifecycle ViewModel SavedState**: To handle UI-related data.
- **Hilt Navigation Compose**: To integrate Hilt with Jetpack Compose’s navigation component.

### Dev Setup Step-by-Step

1. Clone the project
2. Follow the instructions provided in the [repository](https://github.com/Iinaus/restaurant_review_backend) to set up and run the backend server.
3. Open in Android Studio:
   - Launch Android Studio.
   - Open the cloned restaurant_reviews project.
   - Allow Android Studio to sync and build the project. 
4. Run the Application:
   - Connect an Android device or start an emulator.
   - Click on the "Run" button in Android Studio to build and deploy the app.

