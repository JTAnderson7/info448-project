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

//        Toast.makeText(this, "here", Toast.LENGTH_LONG).show()

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        ItemDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(ItemDetailFragment.ARG_ITEM_ID)

                    )

                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }

    }

}
