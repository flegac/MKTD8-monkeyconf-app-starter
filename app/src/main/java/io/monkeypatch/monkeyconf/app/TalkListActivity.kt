package io.monkeypatch.monkeyconf.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import io.monkeypatch.monkeyconf.app.talk.TalkListView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_talk.view.*

class TalkListActivity : AppCompatActivity(), TalkListView {
    private val presenter = Container.talkPresenter(this)


    override fun displayTalks(talks: List<Talk>) {
        recyclerView.swapAdapter(
            TalkListAdapter(
                talks,
                this::openTalk
            ),
            false
        )
    }


    override fun displayError(e: Exception) {

    }

    override fun showLoading(visible: Boolean) {
        progressBar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TalkListActivity)
            setHasFixedSize(true)
        }
        presenter.loadTalks()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as? SearchView
        searchView?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { filterUpdated(it) }
                    return true
                }
            })
        }
        return true
    }

    private fun filterUpdated(text: String) {
    }


    fun View.dismissKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    override fun onDestroy() {
        super.onDestroy()
    }


    private fun openTalk(talkId: String) {
        startActivity(
            Intent(this, TalkDetailActivity::class.java).putExtra("talkId", talkId)
        )
    }
}


class TalkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun displayTalk(summary: Talk, action: (String) -> Unit) {
        itemView.talkHourTextView.text = summary.date
        itemView.talkTitleTextView.text = summary.title + " - " + summary.id
        itemView.talkSubTitleTextView.text = "${summary.speakers} - ${summary.room}"
        itemView.setOnClickListener { action(summary.id) }
    }
}

class TalkListAdapter(val talks: List<Talk>, val action: (String) -> Unit) : RecyclerView.Adapter<TalkViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): TalkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_talk, parent, false)
        return TalkViewHolder(view)
    }

    override fun getItemCount() = talks.size

    override fun onBindViewHolder(viewHolder: TalkViewHolder, pos: Int) {
        viewHolder.displayTalk(talks[pos], action)
    }

}