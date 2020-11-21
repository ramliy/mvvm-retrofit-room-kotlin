package id.ramli.nytimes_android.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ramli.nytimes_android.MyApps
import id.ramli.nytimes_android.db.History
import id.ramli.nytimes_android.db.MyRoomDatabase

/**
 * Created by ramliy10 on 20/11/20.
 */
class SearchViewModel : ViewModel() {
    private var historyData = MutableLiveData<List<History>>()
    private var lastData = MutableLiveData<List<History>>()
    private val database = MyRoomDatabase.getDatabase(MyApps.applicationContext())
    private val dao = database.getHistoryDao()
    private val listItems = arrayListOf<History>()
    private val lastItem = arrayListOf<History>()


    fun getHistoryData() {
        listItems.addAll(dao.getAll())
        historyData.value = listItems

    }

    fun addHistory(history: History){
        if (dao.getById(history.id).isEmpty()){
            dao.insert(history)
        }
    }

    fun deleteData11(id:Int){
        dao.deleteById(id)
        listItems.clear()
        getHistoryData()
    }


    fun getLastRecord(){
        lastItem.addAll(dao.getLastRecord())
        lastData.value = lastItem
    }

    fun setHistoryData() : MutableLiveData<List<History>> {
        return historyData
    }

    fun setLastData() : MutableLiveData<List<History>>{
        return lastData
    }


}