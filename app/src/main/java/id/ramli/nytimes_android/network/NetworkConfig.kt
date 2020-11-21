package id.ramli.nytimes_android.network

import android.os.Build
import androidx.annotation.RequiresApi
import id.ramli.nytimes_android.MyApps
import id.ramli.nytimes_android.utils.Constans
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ramliy10 on 17/11/20.
 */
class NetworkConfig {

    @RequiresApi(Build.VERSION_CODES.M)
    fun getInterceptor(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheSize = (5 * 1024 * 1024).toLong()
        val context = MyApps.applicationContext()
        val myCache = Cache(context.cacheDir, cacheSize)

        val okhttp = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (isOnline(context))
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                chain.proceed(request)
            }
            .build()

        return okhttp
    }

    fun getNetwork(): Retrofit {
        return Retrofit.Builder().baseUrl(Constans.BASE_API_URL)
            .client(getInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun api(): ApiService {
        return getNetwork().create(ApiService::class.java)
    }


}
