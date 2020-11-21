package id.ramli.nytimes_android.db

import androidx.room.*

/**
 * Created by ramliy10 on 19/11/20.
 */
@Dao
interface FavoriteDao {
    @Insert
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE _id = :id")
    fun deleteById(id: String)

    @Query("SELECT * FROM favorite")
    fun getAll() : List<Favorite>

    @Query("SELECT * FROM favorite WHERE _id = :id")
    fun getById(id: String) : List<Favorite>
}