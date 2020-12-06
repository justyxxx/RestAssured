package utils

import exception.TestException
import java.io.InputStream

object Utils {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun getResourcesFile(fileName: String): InputStream {
        return try {
            Utils.javaClass.classLoader.getResourceAsStream(fileName)
        } catch (ex: Throwable) {
            throw TestException(
                "Файл $fileName отсутствует!\n${ex.message.toString()}"
            )
        }
    }
}