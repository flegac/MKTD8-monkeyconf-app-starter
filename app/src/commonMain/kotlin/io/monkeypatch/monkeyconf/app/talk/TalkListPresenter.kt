package io.monkeypatch.monkeyconf.app.talk

import io.monkeypatch.monkeyconf.app.BasePresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class TalkListPresenter(
    private val talkView: TalkListView,
    private val talkRepository: TalkRepository,
    private val uiDispatcher: CoroutineDispatcher
) : BasePresenter(talkView) {

    override fun onCreate() {
        super.onCreate()
        loadTalks()
        talkView.showLoading(false)

    }

    fun loadTalks() {
        launch(uiDispatcher) {
            try {
                val talks = talkRepository.getTalks()
                talkView.displayTalks(talks)
            } catch (e: Exception) {
                talkView.displayError(e)
            }

        }
    }

}