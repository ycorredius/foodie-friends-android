package com.example.foodiefriends.network

import android.content.Context
import com.example.foodiefriends.data.RecipeListResponse
import com.example.foodiefriends.headerValue
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor() {
	companion object {
//		private const val HOST_LOCAL = "http://192.168.1.149:3000/api/v1/"
		private const val HOST_LOCAL = "http://10.0.2.2:3000/api/v1/"

		private val DISPATCHER = Dispatcher().apply { maxRequests = 21 }
	}

	private val gson: Gson = GsonBuilder()
		.registerTypeAdapter(RecipeListResponse::class.java, RecipeResponseSerializer())
		.create()

	fun getClient(context: Context, includeHeaders: Boolean = true): Retrofit {
		val cacheSize = (10*1024*1024).toLong()
		val cache = Cache(context.cacheDir, cacheSize)
		val clientBuilder = OkHttpClient.Builder()
			.addInterceptor(CachedInterceptor())
			.dispatcher(DISPATCHER)
			.cache(cache)

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
			request.newBuilder().header("Cache-Control", "public, max-age" + 60 * 60 * 24 * 7)
				.build()
			val authenticatedRequest = request.newBuilder()
				.header("Authorization", headerValue(context)).build()
			return chain.proceed(authenticatedRequest)
		}
	}

	class CachedInterceptor: Interceptor{
		override fun intercept(chain: Interceptor.Chain): Response {
			val response: Response = chain.proceed(chain.request())
			val cacheControl = CacheControl.Builder()
				.maxAge(1, TimeUnit.HOURS)
				.build()
			return response.newBuilder()
				.header("Cache-Control", cacheControl.toString())
				.build()
		}
	}
}

class RecipeResponseSerializer : JsonSerializer<RecipeListResponse> {
	override fun serialize(
		recipe: RecipeListResponse,
		type: Type,
		context: JsonSerializationContext
	): JsonElement {
		return Gson().toJsonTree(recipe, type).asJsonObject
	}
}