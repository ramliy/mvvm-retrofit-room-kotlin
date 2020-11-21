package id.ramli.nytimes_android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.ramli.nytimes_android.R
import id.ramli.nytimes_android.RecyclerViewClickListener
import id.ramli.nytimes_android.model.Document
import id.ramli.nytimes_android.model.Multimedia
import id.ramli.nytimes_android.utils.Constans
import kotlinx.android.synthetic.main.article_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ramliy10 on 17/11/20.
 */
class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.MyHolder>(){
    private var data = ArrayList<Document>()

    var listener: RecyclerViewClickListener? = null

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(get: Document) {
            itemView.tv_title.text = get.headline?.newsTitle
            itemView.tv_snippet.text = get.snippet

            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val output = SimpleDateFormat("dd/MM/yyyy HH:mm")

            var d: Date? = null
            try {
                d = input.parse(get.pubDate?.subSequence(0, (get.pubDate?.length ?: 5) -5) as String)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val formatted: String = output.format(d)

            itemView.tv_date.text = formatted
            var listMultimedia : List<Multimedia>? = get.multimedia
            if (listMultimedia != null) {
                if (listMultimedia.size>0)
                Picasso.get().load(Constans.BASE_URL + get.multimedia?.get(0)?.url)
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
        p0.itemView.setOnClickListener {
            listener?.onItemClick(it,data[p1])
        }

    }

    fun addList(items : ArrayList<Document>){
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

}