package com.example.foodiefriends.network

import com.example.foodiefriends.data.RecipeListResponse
import com.example.foodiefriends.data.RecipeResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
	@GET("user/recipes")
	suspend fun getUserRecipes(@Query("name") name: String): Response<RecipeListResponse>

	@GET("recipes")
	suspend fun getRecipes(@Query("name") name: String): Response<RecipeListResponse>

	@GET("recipes/{id}")
	suspend fun getRecipeDetails(@Path("id") id: Int): Response<RecipeResponse>

	companion object {
		fun create(retrofit: Retrofit): RecipeService = retrofit.create(RecipeService::class.java)
	}
}