package id.ramli.nytimes_android.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by ramliy10 on 20/11/20.
 */
//Entity annotation to specify the table's name
@Entity(tableName = "history")
//Parcelable annotation to make parcelable object
@Parcelize
data class History(
    //PrimaryKey annotation to declare primary key
    //ColumnInfo annotation to specify the column's name
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "keyword") var keyword: String = ""
): Parcelable {

}