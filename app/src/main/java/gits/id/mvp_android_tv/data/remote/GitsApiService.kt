package gits.id.mvp_android_tv.data.remote

import gits.id.mvp_android_tv.base.BaseApiModel
import gits.id.mvp_android_tv.data.remote.dao.Movie
import gits.id.mvp_android_tv.util.Network

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsApiService {

    /**
     * Do get movie list with this path
     */
    @GET("3/discover/movie?api_key=1b2f29d43bf2e4f3142530bc6929d341&sort_by=popularity.desc")
    fun getMovies(): Observable<BaseApiModel<List<Movie>>>



    companion object Factory {

        fun create(): GitsApiService {

            val mLoggingInterceptor = HttpLoggingInterceptor()
            mLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val mClient = OkHttpClient.Builder()
                    .addInterceptor(mLoggingInterceptor)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build()

            /**
             * Un-comment when unit test
             */
//            val resource = OkHttp3IdlingResource.create("OkHttp", client)
//            IdlingRegistry.getInstance().register(resource)

            val mRetrofit = Retrofit.Builder()
                    .baseUrl(Network.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mClient) //Todo comment if app release
                    .build()

            return mRetrofit.create(GitsApiService::class.java)
        }
    }
}