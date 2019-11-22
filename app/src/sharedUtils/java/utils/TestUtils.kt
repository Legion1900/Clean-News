package utils

import org.mockito.Mockito

object TestUtils {
    fun <T> any() = Mockito.any<T>() as T
}