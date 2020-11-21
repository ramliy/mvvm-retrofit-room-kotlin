package id.ramli.nytimes_android.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.ramli.nytimes_android.R
import id.ramli.nytimes_android.RecyclerViewClickListener
import id.ramli.nytimes_android.SecondRecyclerViewClickListener
import id.ramli.nytimes_android.db.History
import id.ramli.nytimes_android.model.Document
import id.ramli.nytimes_android.ui.ArticleAdapter
import id.ramli.nytimes_android.ui.ArticleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.list
import kotlinx.android.synthetic.main.activity_search.swipeRefresh


class SearchActivity : AppCompatActivity(), RecyclerViewClickListener,SecondRecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var searchViewModel: SearchViewModel
    lateinit var adapter: ArticleAdapter
    lateinit var historyAdapter: HistoryAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var historyLayoutManager: LinearLayoutManager
    var keywordChoosed: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        layoutManager = LinearLayoutManager(this)
        historyLayoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        setupRecyclerview()
        getDataHistory()
        adapter.listener = this
        historyAdapter.listener = this

        getLastData()

        search.setIconifiedByDefault(true);
        search.setFocusable(true)
        search.setIconified(false)
        search.requestFocusFromTouch()
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                swipeRefresh.isRefreshing = true
                getData(query)
                addHistory(History(0,query))
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                list_history.visibility = View.VISIBLE
                if (newText.isEmpty()){
                    list_history.visibility = View.GONE
                }
                return false
            }
        })

        iv_back.setOnClickListener {
            onBackPressed()
        }

        viewModel.getStatus().observe(this, Observer { t ->
            if (t ?: true) {
//                swipeRefresh.isRefreshing = true
            } else {
                list.visibility = View.VISIBLE
                swipeRefresh.isRefreshing = false

            }

        })

        viewModel.setArticleData().observe(this, Observer { t ->
            t?.response?.let {
                if (it.isEmpty()){
                    Toast.makeText(baseContext, R.string.empty_data, Toast.LENGTH_SHORT).show()
                } else
                    list_history.visibility = View.GONE
                    adapter.addList(it as ArrayList<Document>)
                adapter.notifyDataSetChanged()

            }
        })

        searchViewModel.setHistoryData().observe(this, Observer { t ->
            t?.let {
                if (it.isEmpty()) {
                    text_status.visibility = View.VISIBLE
                } else
                    if (it.size == 11){
                        searchViewModel.deleteData11(it[10].id)
                        swipeRefresh.isRefreshing = false

                    } else {
                        list_history.visibility = View.VISIBLE
                        historyAdapter.addList(it as ArrayList<History>)
                        historyAdapter.notifyDataSetChanged()
                    }



            }
        })

        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.isEnabled = false

    }

    private fun getLastData() {
        searchViewModel.getLastRecord()
    }

    private fun getDataHistory() {
        searchViewModel.getHistoryData()
    }

    private fun getData(keyword: String) {
        viewModel.getSearchArticleData(keyword)
    }

    private fun setupRecyclerview() {
        list.setHasFixedSize(true)
        list.layoutManager = layoutManager
        adapter = ArticleAdapter()
        list.adapter = adapter

        list_history.setHasFixedSize(true)
        list_history.layoutManager = historyLayoutManager
        historyAdapter = HistoryAdapter()
        list_history.adapter = historyAdapter
    }

    override fun onItemClick(view: View?, data: Any?) {
        val document: Document = data as Document
        Toast.makeText(baseContext, document.abstract, Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
    }

    private fun addHistory(history: History){
        searchViewModel.addHistory(history)

    }

    override fun onSecondItemClick(view: View?, data: Any?) {
        val history:History = data as History
        viewModel.getSearchArticleData(history.keyword)
        keywordChoosed = history.keyword
        search.setQuery(keywordChoosed, false)
        swipeRefresh.isRefreshing = true


    }

}