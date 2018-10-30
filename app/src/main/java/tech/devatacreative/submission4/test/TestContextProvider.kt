package tech.devatacreative.submission4.test

import kotlinx.coroutines.experimental.Unconfined
import tech.devatacreative.submission4.CoroutineContextProvider
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}