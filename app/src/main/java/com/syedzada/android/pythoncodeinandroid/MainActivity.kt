package com.syedzada.android.pythoncodeinandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.syedzada.android.pythoncodeinandroid.databinding.ActivityMainBinding
import com.syedzada.android.pythoncodeinandroid.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
*

*
* */

/**
 * MainActivity demonstrates executing Python code within an Android application
 * using the Chaquopy library.
 *
 * @see [Chaquopy Documentation] https://chaquo.com/chaquopy/
 * @see [Chaquopy Link 2] https://chaquo.com/chaquopy/doc/current/
 * @see [Chaquopy Link 3] https://chaquo.com/chaquopy/doc/current/versions.html
 * @see [Chaquopy Link 4] https://chaquo.com/chaquopy/doc/current/android.html
 *
 * The activity initializes the Python interpreter, invokes Python functions,
 * and displays the results on the UI.
 */

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // "context" must be an Activity, Service or Application object from your app.
        // Initialize the Python environment if not already started.
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        val py = Python.getInstance()

        // Launch a coroutine to handle Python function calls off the main thread.
        lifecycleScope.launch {
            try {
                val results = fetchPythonResults(py)
                displayResults(results)
            } catch (e: Exception) {
                // Handle exceptions appropriately
                binding.tvPrint.text = getString(R.string.error_message, e.localizedMessage)
            }
        }
    }

    private suspend fun fetchPythonResults(py: Python): Results = withContext(Dispatchers.IO) {
        val module = py.getModule("script")
        val num = module["number"]?.toInt()
        val text = module["text"]?.toString()
        val fact = module["factorial"]?.call(5)
        val sortFun = module["simple_sort"]
        val list = arrayOf(1, 3, 2, 9, 5)
        val sortedList = sortFun?.call(list)?.asList()

        Results(num, text, fact, sortedList)
    }

    private fun displayResults(results: Results) {
        // Update the UI with the results of the Python calls.
        val resultText = """
            The value of num: ${results.num}
            The value of text: ${results.text}
            The value of factorial: ${results.fact}
            The sorted list is: ${results.sortedList}
        """.trimIndent()
        binding.tvPrint.text = resultText
    }
}