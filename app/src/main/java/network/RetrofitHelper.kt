package network

import MockResponseInterceptor
import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    
    val baseUrl = "https://elililly.com/"
    
    
    fun getInstance(context: Context): Retrofit {
        
        val client = OkHttpClient.Builder()
                .addInterceptor(MockResponseInterceptor(context)).build()
        
        return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }
}