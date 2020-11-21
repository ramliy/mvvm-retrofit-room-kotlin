package id.ramli.nytimes_android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by ramliy10 on 19/11/20.
 */
@Database(entities = [Favorite::class,History::class], version = 2, exportSchema = false)
abstract class MyRoomDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(context: Context): MyRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
                    "my_db"
                )
                    .allowMainThreadQueries() //allows Room to executing task in main thread
                    .fallbackToDestructiveMigration() //allows Room to recreate table if no migrations found
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getFavoriteDao() : FavoriteDao
    abstract fun getHistoryDao() : HistoryDao
}