package common

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
open class BaseTest {
    companion object {
        const val BASE_API_URL = "https://reqres.in"
    }
}