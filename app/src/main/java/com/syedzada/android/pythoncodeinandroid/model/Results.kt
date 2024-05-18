package com.syedzada.android.pythoncodeinandroid.model

import com.chaquo.python.PyObject


/**
 * Data class to hold the results of the Python operations.
 *
 * @param num The value of the Python variable 'number'.
 * @param text The value of the Python variable 'text'.
 * @param fact The result of calling the Python function 'factorial'.
 * @param sortedList The result of calling the Python function 'simple_sort' on a list.
 */
data class Results(
    val num: Int?,
    val text: String?,
    val fact: PyObject?,
    val sortedList: List<PyObject>?
)
