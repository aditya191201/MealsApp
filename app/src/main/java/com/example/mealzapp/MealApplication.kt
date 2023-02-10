package com.example.mealzapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext

@HiltAndroidApp
class MealApplication: Application() {
    public fun getContext(): Context{
        return applicationContext
    }
}