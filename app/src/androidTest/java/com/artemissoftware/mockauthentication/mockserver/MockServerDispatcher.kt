package com.artemissoftware.mockauthentication.mockserver

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockServerDispatcher {
    fun successDispatcher(map: Map<String, String>): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    AUTH_LOGIN -> {
                        var json = ""
                        if (map.containsKey(AUTH_LOGIN)) {
                            json = map[AUTH_LOGIN]!!
                        }
                        MockResponse().setResponseCode(200).setBody(getJsonContent(json))
                    }
                    else -> MockResponse().setResponseCode(200).setBody("")
                }
            }
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }

    companion object Path {
        const val AUTH_LOGIN = "/auth/login"

        val serviceMap: Map<String, String> = mapOf(
            Pair(AUTH_LOGIN, "auth_login_success.json"),
        )
    }
}
