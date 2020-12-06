package exception

import org.junit.Assert

class TestException(message: String?) : RuntimeException(message) {
    init {
        Assert.fail(message)
    }
}