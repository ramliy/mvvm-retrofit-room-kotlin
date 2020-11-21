package id.ramli.nytimes_android.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import id.ramli.nytimes_android.R
import id.ramli.nytimes_android.RecyclerViewClickListener
import id.ramli.nytimes_android.db.Favorite
import kotlinx.android.synthetic.main.activity_main.*

class FavoriteActivity : AppCompatActivity(), RecyclerViewClickListener {

    private lateinit var viewModel: FavoriteViewModel
    lateinit var adapter: FavoriteAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setSupportActionBar(toolbar)
        title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)

        layoutManager = LinearLayoutManager(this)

        setupRecyclerview()
        getData()
        adapter.listener = this

        viewModel.setFavoriteData().observe(this, Observer { t ->
            t?.let {
                if (it.isEmpty()) {
                    text_status.visibility = View.VISIBLE
                } else
                    adapter.addList(it as ArrayList<Favorite>)
                adapter.notifyDataSetChanged()

            }
        })

    }

    private fun getData() {
        viewModel.getFavoriteleData()

    }

    private fun setupRecyclerview() {
        list.setHasFixedSize(true)
        list.layoutManager = layoutManager
        adapter = FavoriteAdapter()
        list.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(view: View?, data: Any?) {
        data as Favorite

        if (view?.id == R.id.tv_remove){
            viewModel.deleteFavoriteData(data._id)
            finish()
            startActivity(intent);
        }
    }

}