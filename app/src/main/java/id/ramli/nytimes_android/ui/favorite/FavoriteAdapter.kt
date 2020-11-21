package id.ramli.nytimes_android.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.ramli.nytimes_android.R
import id.ramli.nytimes_android.RecyclerViewClickListener
import id.ramli.nytimes_android.db.Favorite
import id.ramli.nytimes_android.utils.Constans
import kotlinx.android.synthetic.main.article_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ramliy10 on 19/11/20.
 */
class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.MyHolder>(){
    private var data = ArrayList<Favorite>()

    var listener: RecyclerViewClickListener? = null

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: Favorite) {
            itemView.tv_title.text = get.title_news
            itemView.tv_snippet.text = get.snippet

            itemView.tv_date.text = get.pub_date
            itemView.tv_remove.visibility = View.VISIBLE

            if (get.img_url!=null){

                Picasso.get().load(Constans.BASE_URL + get.img_url)
                    .placeholder(R.color.colorUltraLightGrey)
                    .error(R.color.colorUltraLightGrey)
                    .into(itemView.iv_image)
            }

        }

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int)
            = MyHolder(
        LayoutInflater.from(p0.context).inflate(R.layout.article_item, p0, false)
    )


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: MyHolder, p1: Int) {
        p0.bindView(data.get(p1))
        p0.itemView.tv_remove.setOnClickListener {
            listener?.onItemClick(it,data[p1])
        }

    }

    fun addList(items : ArrayList<Favorite>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

}