package io.monkeypatch.monkeyconf.app.talk

import io.monkeypatch.monkeyconf.app.BaseView
import io.monkeypatch.monkeyconf.app.Talk

interface TalkDetailView : BaseView {
    fun exampleDisplayData(talk: Talk)
}