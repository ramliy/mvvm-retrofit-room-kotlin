package id.ramli.nytimes_android.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ramli.nytimes_android.R
import id.ramli.nytimes_android.RecyclerViewClickListener
import id.ramli.nytimes_android.SecondRecyclerViewClickListener
import id.ramli.nytimes_android.db.History
import kotlinx.android.synthetic.main.article_item.view.*

/**
 * Created by ramliy10 on 20/11/20.
 */
class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.MyHolder>(){
    private var data = ArrayList<History>()

    var listener: SecondRecyclerViewClickListener? = null

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: History) {
            itemView.tv_title.text = get.keyword
        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int)
            = MyHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.history_item, p0, false)
    )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: MyHolder, p1: Int) {
        p0.bindView(data.get(p1))
        p0.itemView.setOnClickListener {
            listener?.onSecondItemClick(it,data[p1])
        }

    }

    fun addList(items : ArrayList<History>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

}