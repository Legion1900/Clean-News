package com.legion1900.cleannews.utils

import org.mockito.Mockito

object TestUtils {
    fun <T> any() = Mockito.any<T>() as T
}