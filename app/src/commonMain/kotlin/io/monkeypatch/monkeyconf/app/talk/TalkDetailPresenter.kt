package io.monkeypatch.monkeyconf.app.talk

import io.monkeypatch.monkeyconf.app.BasePresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class TalkDetailPresenter(
    private val talkView: TalkDetailView,
    private val talkRepository: TalkRepository,
    private val uiDispatcher: CoroutineDispatcher
) : BasePresenter(talkView) {

    override fun onCreate() {
        super.onCreate()
    }

    fun display(talkId: String) {
        launch(uiDispatcher) {
            try {
                val talk = talkRepository.getTalk(talkId)
                talk?.let {
                    talkView.exampleDisplayData(it)
                }
            } catch (e: Exception) {
                talkView.displayError(e)
            }

        }
    }

}