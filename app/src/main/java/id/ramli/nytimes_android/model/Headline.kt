package id.ramli.nytimes_android.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ramliy10 on 17/11/20.
 */
data class Headline (
    @SerializedName("main")
    val newsTitle: String? = null
)