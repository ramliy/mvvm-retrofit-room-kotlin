package id.ramli.nytimes_android.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.ramli.nytimes_android.R
import id.ramli.nytimes_android.RecyclerViewClickListener
import id.ramli.nytimes_android.model.Document
import id.ramli.nytimes_android.model.Multimedia
import id.ramli.nytimes_android.network.isOnline
import id.ramli.nytimes_android.ui.ArticleAdapter
import id.ramli.nytimes_android.ui.ArticleViewModel
import id.ramli.nytimes_android.ui.favorite.FavoriteActivity
import id.ramli.nytimes_android.ui.search.SearchActivity
import id.ramli.nytimes_android.utils.Constans
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    RecyclerViewClickListener {

    private val TAG = "MainActivity"
    var page = 0
    private lateinit var viewModel: ArticleViewModel
    lateinit var adapter: ArticleAdapter
    lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (this.getResources()
                .getConfiguration().orientation === Configuration.ORIENTATION_PORTRAIT
        ) {
                    layoutManager = GridLayoutManager(this, 1)

        } else {
                    layoutManager = GridLayoutManager(this, 4)


        }

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        setSupportActionBar(toolbar)

        setupRecyclerview()
        getData(page)

        adapter.listener = this
        viewModel.getStatus().observe(this, Observer { t ->
            if (t ?: true) {
                swipeRefresh.isRefreshing = true
            } else {
                list.visibility = View.VISIBLE
                swipeRefresh.isRefreshing = false

            }

/*
            if (this.getResources()
                    .getConfiguration().orientation === Configuration.ORIENTATION_PORTRAIT
            ) {
                list.setLayoutManager(GridLayoutManager(this, 1))
            } else {
                list.setLayoutManager(GridLayoutManager(this, 4))
            }
*/


        })

        viewModel.setArticleData().observe(this, Observer { t ->
            t?.response?.let {
                if (it.isEmpty()) {
                    text_status.visibility = View.VISIBLE
                } else
                /*if (this.getResources()
                        .getConfiguration().orientation === Configuration.ORIENTATION_PORTRAIT
                ) {
                    list.setLayoutManager(GridLayoutManager(this, 1))
                } else {
                    list.setLayoutManager(GridLayoutManager(this, 4))
                }*/

                    adapter.addList(it as ArrayList<Document>)
                adapter.notifyDataSetChanged()

            }
        })

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) { // only when scrolling up
                    val visibleThreshold = 2
                    val layoutManager =
                        list.getLayoutManager() as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        //show your loading view
                        page++
                        if (isOnline(this@MainActivity)) {
                            getData(page)
                        } else {
                            Toast.makeText(
                                baseContext,
                                R.string.connection_failed, Toast.LENGTH_SHORT
                            ).show()
                        }
                        // load content in background
                    }
                }

                /*       val visibleItemCount = layoutManager.childCount
                       val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                       val total = adapter.itemCount
                       if (page < total) {
                           if (visibleItemCount + pastVisibleItem >= total) {
                               page++
                               if (isOnline(this@MainActivity)) {
                                   getData(page)
                               } else {
                                   Toast.makeText(
                                       baseContext,
                                       R.string.connection_failed, Toast.LENGTH_SHORT
                                   ).show()
                               }
                           }
                       }*/

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        swipeRefresh.setOnRefreshListener(this)

    }

    private fun getData(page: Int) {
        viewModel.getArticleData(page)
    }

    private fun setupRecyclerview() {
        list.setHasFixedSize(true)
        list.layoutManager = layoutManager
        adapter = ArticleAdapter()
        list.adapter = adapter
    }

    override fun onRefresh() {
        adapter.clear()
        page = 0
        getData(page)

    }

    override fun onItemClick(view: View?, data: Any?) {
        val document: Document = data as Document
        var listMultimedia: List<Multimedia>? = document.multimedia
        var imgUrl = ""
        if (listMultimedia != null) {
            if (listMultimedia.size > 0)
                imgUrl = document.multimedia?.get(0)?.url.toString()
        }

        val intent = Intent(this, DetailArticleActivity::class.java)
        intent.putExtra(Constans.ARTICLE_DATE, document.pubDate)
        intent.putExtra(Constans.ARTICLE_TITLE, document.headline?.newsTitle)
        intent.putExtra(Constans.ARTICLE_SNIPPET, document.snippet)
        intent.putExtra(Constans.ARTICLE_LEAD_PAR, document.leadParagraph)
        intent.putExtra(Constans.ARTICLE_IMAGE, imgUrl)
        intent.putExtra(Constans.ARTICLE_URL, document.abstract)
        intent.putExtra(Constans.ARTICLE_ID, document.idDoc)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(baseContext, "Landscape Mode", Toast.LENGTH_SHORT).show()
            list.setLayoutManager(GridLayoutManager(this, 4))


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(baseContext, "Portrait Mode", Toast.LENGTH_SHORT).show()
            list.setLayoutManager(GridLayoutManager(this, 1))


        }
    }


}