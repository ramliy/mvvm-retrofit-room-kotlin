package id.ramli.nytimes_android.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by ramliy10 on 17/11/20.
 */

data class Document (
    val abstract: String? = null,
    @SerializedName("web_url")
    val webUrl: String? = null,
    @SerializedName("lead_paragraph")
    val leadParagraph: String? = null,
    val multimedia: List<Multimedia>? = null,
    val snippet: String? = null,
    @SerializedName("pub_date")
    val pubDate: String? = null,
    val headline: Headline? = null,
    @SerializedName("_id")
    val idDoc: String? = null
)