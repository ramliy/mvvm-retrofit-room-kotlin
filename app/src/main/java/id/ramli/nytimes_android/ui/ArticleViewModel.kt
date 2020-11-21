package id.ramli.nytimes_android.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ramli.nytimes_android.model.Article
import id.ramli.nytimes_android.model.ResponseArticle
import id.ramli.nytimes_android.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ramliy10 on 17/11/20.
 */
class ArticleViewModel : ViewModel() {

    private var status = MutableLiveData<Boolean>()

    private var docsData = MutableLiveData<Article>()

    fun getArticleData(page: Int) {
        status.value = true

        NetworkConfig().api().article("API_KEY","indonesia", page).enqueue(object :Callback<ResponseArticle>{
            override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
                status.value = true
                docsData.value = null
            }

            override fun onResponse(call: Call<ResponseArticle>, response: Response<ResponseArticle>) {
                status.value = false

                if(response.isSuccessful){
                    docsData.value = response.body()?.response
                }
                else{
                    status.value = true
                }

            }
        })

    }

    fun getSearchArticleData(keyword: String) {
        status.value = true

        NetworkConfig().api().searchArticle(keyword,"API_KEY").enqueue(object :Callback<ResponseArticle>{
            override fun onFailure(call: Call<ResponseArticle>, t: Throwable) {
                status.value = true
                docsData.value = null
            }

            override fun onResponse(call: Call<ResponseArticle>, response: Response<ResponseArticle>) {
                status.value = false

                if(response.isSuccessful){
                    docsData.value = response.body()?.response
                }
                else{
                    status.value = true
                }



            }
        })

    }

    fun setArticleData() : MutableLiveData<Article> {
        return docsData
    }

    fun getStatus():MutableLiveData<Boolean>{
        status.value = true
        return status
    }

}
