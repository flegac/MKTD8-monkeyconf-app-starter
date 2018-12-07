package io.monkeypatch.monkeyconf.app.talk

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.monkeypatch.monkeyconf.app.Talk
import kotlinx.serialization.list

interface TalkRepository {
    suspend fun getTalks(): List<Talk>
    suspend fun getTalk(id: String): Talk?
}

class TalkRepositoryImpl(
    private val url: String
) : TalkRepository {
    private val client
        get() = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer().apply {
                    register(Talk.serializer().list)
                }
            }
        }

    private var conferences: List<Talk>? = null

    private suspend fun loadTalks(): List<Talk> = client.get(url)

    override suspend fun getTalks(): List<Talk> =
        conferences ?: loadTalks().also { conferences = it }

    override suspend fun getTalk(id: String): Talk? =
        getTalks().first { it.id == id }
}