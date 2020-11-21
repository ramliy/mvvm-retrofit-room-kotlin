package id.ramli.nytimes_android.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Created by ramliy10 on 20/11/20.
 */
@Dao
interface HistoryDao {
    @Insert
    fun insert(history: History)

    @Update
    fun update(history: History)

    @Query("DELETE FROM history WHERE id = :id")
    fun deleteById(id: Int)

    @Query("SELECT * FROM history ORDER BY id DESC")
    fun getAll() : List<History>

    @Query("SELECT * FROM history WHERE id = :id")
    fun getById(id: Int) : List<History>

    @Query("SELECT * FROM history ORDER BY id DESC LIMIT 1")
    fun getLastRecord() : List<History>
}