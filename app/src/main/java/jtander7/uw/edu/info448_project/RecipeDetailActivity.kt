package jtander7.uw.edu.info448_project

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_recipe_detail.*

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Recipe")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Toast.makeText(this, "here", Toast.LENGTH_LONG).show()
    }

}
