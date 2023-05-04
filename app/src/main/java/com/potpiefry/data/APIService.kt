package com.potpiefry.data

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class Dish(
	val id: Int,
	val type: String,
	val name: String,
	val description: String,
	val img: String?,
	val ingredients: List<Ingredient>,
	val steps: List<Step>
)

class Ingredient(
	val amount: String,
	val unit: String,
	val ingredient: String
)

class Step(
	val description: String,
	val duration: Int?,
	val durationUnit: String?
)

class DishPreview(
	val id: Int,
	val type: String,
	val name: String,
	val description: String,
	val img: String?,
)


const val BASE_URL = "http://192.168.100.2:3001/api/"

interface APIService {
	@GET("dish")
	suspend fun getDishes(): List<DishPreview>

	@GET("dish/{id}")
	suspend fun getDish(@Path(value = "id") id: Int): Dish

	companion object {
		var apiService: APIService? = null
		fun getInstance(): APIService {
			if (apiService == null) {
				apiService = Retrofit.Builder()
					.baseUrl(BASE_URL)
					.addConverterFactory(GsonConverterFactory.create())
					.build().create(APIService::class.java)
			}
			return apiService!!
		}
	}
}