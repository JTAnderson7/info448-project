package jtander7.uw.edu.info448_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list_view.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*

class ListView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        setupRecyclerView(recycler_view)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        val myList = listOf<String>("one", "two", "three", "four", "five")

        recyclerView.adapter = MyRecyclerViewAdapter(myList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

    }

    class MyRecyclerViewAdapter(private val values: List<String>): RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: MyRecyclerViewAdapter.ViewHolder, position: Int) {
            val item = values[position]
            holder.titleView.text = item
        }



        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val titleView: TextView = view.title_view
        }


    }




}
