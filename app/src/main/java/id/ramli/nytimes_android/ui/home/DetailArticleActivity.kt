package id.ramli.nytimes_android.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import id.ramli.nytimes_android.R
import id.ramli.nytimes_android.db.Favorite
import id.ramli.nytimes_android.db.FavoriteDao
import id.ramli.nytimes_android.db.MyRoomDatabase
import id.ramli.nytimes_android.utils.Constans
import kotlinx.android.synthetic.main.activity_detail_article.*
import kotlinx.android.synthetic.main.activity_detail_article.iv_back
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var database: MyRoomDatabase
    private lateinit var dao: FavoriteDao
    var isFavoritAdded: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        database = MyRoomDatabase.getDatabase(applicationContext)
        dao = database.getFavoriteDao()

        val articleTitle = intent.getStringExtra(Constans.ARTICLE_TITLE)
        tv_title.text = articleTitle
        val articleDate = intent.getStringExtra(Constans.ARTICLE_DATE)
        tv_date.text = articleDate
        val articleSnippet = intent.getStringExtra(Constans.ARTICLE_SNIPPET)
        val articleUrl = intent.getStringExtra(Constans.ARTICLE_URL)
        var articleId = intent.getStringExtra(Constans.ARTICLE_ID)

        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val output = SimpleDateFormat("dd/MM/yyyy HH:mm")

        var d: Date? = null
        try {
            d = input.parse(articleDate?.subSequence(0, (articleDate?.length ?: 5) -5) as String)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val formatted: String = output.format(d)
        tv_snippet.text = "$articleSnippet $formatted"

        val articleLeadPar = intent.getStringExtra(Constans.ARTICLE_LEAD_PAR)
        tv_lead_par.text = articleLeadPar
        val articleImage = intent.getStringExtra(Constans.ARTICLE_IMAGE)

        if (articleImage!=null){
            Picasso.get().load(Constans.BASE_URL + articleImage)
                .into(iv_article)
        }

        iv_back.setOnClickListener {
            onBackPressed()
        }

        if (dao.getById(articleId.toString()).isNotEmpty()){
            iv_favorite.setBackgroundResource(R.drawable.ic_baseline_bookmark_24)
            isFavoritAdded = true
        }

        iv_favorite.setOnClickListener{
            if (!isFavoritAdded){
                iv_favorite.setBackgroundResource(R.drawable.ic_baseline_bookmark_24)
                addFavorite(Favorite(0,articleUrl!!,articleLeadPar!!,articleImage!!,formatted,articleTitle!!,articleSnippet!!,articleId!!))
                isFavoritAdded = true
            } else {
                iv_favorite.setBackgroundResource(R.drawable.ic_baseline_bookmark_border_24)
                deleteFavorite(articleId!!)
                isFavoritAdded = false

            }

        }

    }

    private fun addFavorite(favorite: Favorite){
        if (dao.getById(favorite._id).isEmpty()){
            dao.insert(favorite)
        }
        Toast.makeText(applicationContext, "Favorite added", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavorite(id: String){
        dao.deleteById(id)
        Toast.makeText(applicationContext, "Favorite removed", Toast.LENGTH_SHORT).show()
    }
}