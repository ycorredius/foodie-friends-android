package com.example.foodiefriends.network

import android.content.Context
import com.example.foodiefriends.data.RecipeResponse
import com.example.foodiefriends.headerValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor() {
	companion object {
		private const val HOST_LOCAL = "http://10.0.2.2:3000/api/v1/"
		private val DISPATCHER = Dispatcher().apply { maxRequests = 1 }
	}

	private val gson: Gson = GsonBuilder()
		.registerTypeAdapter(RecipeResponse::class.java, RecipeResponseSerializer())
		.create()

	fun getClient(context: Context, includeHeaders: Boolean = true): Retrofit {
		val clientBuilder = OkHttpClient.Builder()
			.dispatcher(DISPATCHER)

		if (includeHeaders) {
			clientBuilder.addInterceptor(AuthorizationInteceptor(context))
		}
		return Retrofit.Builder()
			.baseUrl(HOST_LOCAL)
			.addConverterFactory(GsonConverterFactory.create(gson))
			.client(clientBuilder.build())
			.build()
	}

	class AuthorizationInteceptor(
		private val context: Context
	) : Interceptor {
		override fun intercept(chain: Interceptor.Chain): Response {
			val request = chain.request()
			val authenticatedRequest = request.newBuilder()
				.header("Authorization", headerValue(context)).build()
			return chain.proceed(authenticatedRequest)
		}
	}
}

class RecipeResponseSerializer : JsonSerializer<RecipeResponse> {
	override fun serialize(
		recipe: RecipeResponse,
		type: Type,
		context: JsonSerializationContext
	): JsonElement {
		return Gson().toJsonTree(recipe, type).asJsonObject
	}
}