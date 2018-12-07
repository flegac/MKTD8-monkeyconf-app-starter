package io.monkeypatch.monkeyconf.app

import io.monkeypatch.monkeyconf.app.talk.*
import io.monkeypatch.monkeyconf.app.utils.CommonDispatcher
import io.monkeypatch.monkeyconf.app.utils.ThreadLocal
import kotlinx.coroutines.CoroutineDispatcher

@ThreadLocal
object Container {
    private val uiDispatcher: CoroutineDispatcher = CommonDispatcher.ui

    private val talkRepository = TalkRepositoryImpl("https://monkeyconf.herokuapp.com/")


    fun talkPresenter(talkView: TalkListView) =
        TalkListPresenter(talkView, talkRepository, uiDispatcher)


    fun talkDetailPresenter(talkView: TalkDetailView) =
        TalkDetailPresenter(talkView, talkRepository, uiDispatcher)
}