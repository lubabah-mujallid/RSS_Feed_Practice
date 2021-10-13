package com.example.rss_feed_practice

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var myList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MAIN", "Begin main")
        myList = ArrayList()
        FetchTopQuestions().execute()
    }
    private inner class FetchTopQuestions : AsyncTask<Void, Void, ArrayList<Question>>() {
        val parser = XMLParser()
        override fun doInBackground(vararg params: Void?): ArrayList<Question> {
            val url = URL("https://stackoverflow.com/feeds")
            val urlConnection = url.openConnection() as HttpURLConnection
            myList =
                urlConnection.getInputStream()?.let {
                    parser.parse(it)
                }
                        as ArrayList<Question>
            return myList
        }

        override fun onPostExecute(result: ArrayList<Question>?) {
            super.onPostExecute(result)
            val adapter = RecyclerAdapter(this@MainActivity, myList)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }
}