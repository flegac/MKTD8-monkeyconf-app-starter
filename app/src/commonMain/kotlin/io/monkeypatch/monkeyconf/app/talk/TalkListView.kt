package io.monkeypatch.monkeyconf.app.talk

import io.monkeypatch.monkeyconf.app.BaseView
import io.monkeypatch.monkeyconf.app.Talk

interface TalkListView : BaseView {
    fun displayTalks(talks: List<Talk>)
    fun showLoading(visible: Boolean)
}