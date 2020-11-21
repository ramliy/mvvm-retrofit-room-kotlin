package id.ramli.nytimes_android.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by ramliy10 on 19/11/20.
 */
//Entity annotation to specify the table's name
@Entity(tableName = "favorite")
//Parcelable annotation to make parcelable object
@Parcelize
data class Favorite (

    //PrimaryKey annotation to declare primary key
    //ColumnInfo annotation to specify the column's name
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "web_url") var web_url: String = "",
    @ColumnInfo(name = "lead_paragraph") var lead_paragraph: String = "",
    @ColumnInfo(name = "img_url") var img_url: String = "",
    @ColumnInfo(name = "pub_date") var pub_date: String = "",
    @ColumnInfo(name = "title_news") var title_news: String = "",
    @ColumnInfo(name = "snippet") var snippet: String = "",
    @ColumnInfo(name = "_id") var _id: String = ""



    ) : Parcelable {
}