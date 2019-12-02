package jtander7.uw.edu.info448_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun listRecipes(v: View){
        val intent = Intent(this, ListView::class.java)
        startActivity(intent)
    }
}
