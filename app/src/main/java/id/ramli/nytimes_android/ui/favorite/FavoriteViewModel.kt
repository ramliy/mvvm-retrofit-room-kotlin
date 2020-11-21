package id.ramli.nytimes_android.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ramli.nytimes_android.MyApps
import id.ramli.nytimes_android.db.Favorite
import id.ramli.nytimes_android.db.MyRoomDatabase

/**
 * Created by ramliy10 on 19/11/20.
 */
class FavoriteViewModel : ViewModel() {

    private var favoriteData = MutableLiveData<List<Favorite>>()
    private val database = MyRoomDatabase.getDatabase(MyApps.applicationContext())
    private val dao = database.getFavoriteDao()
    private val listItems = arrayListOf<Favorite>()

    fun getFavoriteleData() {
        listItems.addAll(dao.getAll())
        favoriteData.value = listItems

    }

    fun deleteFavoriteData(id:String){
        dao.deleteById(id)
    }

    fun setFavoriteData() : MutableLiveData<List<Favorite>> {
        return favoriteData
    }



}