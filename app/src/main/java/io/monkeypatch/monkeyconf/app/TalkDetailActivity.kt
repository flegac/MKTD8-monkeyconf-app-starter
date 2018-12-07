package io.monkeypatch.monkeyconf.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import io.monkeypatch.monkeyconf.app.talk.TalkDetailView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

class TalkDetailActivity : AppCompatActivity(), TalkDetailView {
    private val presenter = Container.talkDetailPresenter(this)

    override fun displayError(e: Exception) {

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        presenter.onCreate()
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        val talkId = intent.getStringExtra("talkId")
        presenter.display(talkId)
    }


    override fun exampleDisplayData(talk: Talk) {
        supportActionBar?.title = talk.title
        speakersTextView.text = talk.speakers.toString()
        hourTextView.text = talk.startTime
        roomTextView.text = talk.room.toString()
        descriptionTextView.text = talk.description
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }
}