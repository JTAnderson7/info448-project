package jtander7.uw.edu.info448_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create Singleton
//        val recentNewsUrl = accessRecentNews()
//        startResponse(recentNewsUrl)
        startResponse("https://services.campbells.com/api/Recipes//recipe")

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startResponse(url: String) {
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                var recipesObjects: List<RecipeObject> = parseRecipeAPI(response)
//                setupRecyclerView(item_list, newsObjects, imageLoader)
                // DO SOMETHING WITH NEWS OBJECTS
                var something = "ahh"
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, "There was an error: $error" , Toast.LENGTH_SHORT).show()
            }
        )
        VolleyService.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun listRecipes(v: View){
        val intent = Intent(this, ListView::class.java)
        startActivity(intent)
    }
//    fun accessRecentNews() : String {
//        val buildUri = Uri.parse("https://services.campbells.com/api/Recipes//recipe?q=chicken")
//            .buildUpon()
//            .appendQueryParameter("q", "chicken")
//            .appendQueryParameter("apiKey", NEWS_API_KEY)
//            .build()
//        val urlString = buildUri.toString()
//
//        return urlString
//    }


}
