package com.example.model

import android.content.ContentProvider
import android.content.Context
import com.example.mealzapp.MealApplication
import com.example.model.api.MealsWebService
import com.example.model.db.MealDatabase
import com.example.model.db.MealsDao
import com.example.model.di.AppModule
import com.example.model.response.MealResponse
import com.example.model.response.MealsCategoryResponse
import com.example.model.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.security.AccessController.getContext
import java.util.concurrent.Flow
import javax.inject.Inject


class MealsRepository @Inject constructor(private val webService: MealsWebService,
                                          private val mealsDao: MealsDao?,@ApplicationContext private val applicationContext: Context) {
    private var cachedMeals = listOf<MealResponse>()
    suspend fun getMeals(): List<MealResponse>? {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            val response = webService.getMeals()

            mealsDao?.addMeals(response.categories)

            return mealsDao?.getMeals()
        } else {
            return mealsDao?.getMeals()
        }


    }

    //    suspend fun getMealsDb(): List<MealResponse> = mealsDao?.getMeals()!!
    fun getMeal(id: String): MealResponse? {
        return cachedMeals.firstOrNull {
            it.id == id
        }
//    companion object{
//        @Volatile
//        private var instance: MealsRepository? = null
//        fun getInstance() = instance?: synchronized(this){
//
//            instance ?: MealsRepository().also { instance = it }
//        }
//    }
    }
}