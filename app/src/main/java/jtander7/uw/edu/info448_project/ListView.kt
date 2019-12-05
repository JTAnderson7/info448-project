package jtander7.uw.edu.info448_project

import android.annotation.TargetApi
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.squareup.picasso.Picasso
import jtander7.uw.edu.info448_project.Recipe.parseRecipeAPI
import kotlinx.android.synthetic.main.activity_list_view.toolbar
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*

class ListView : AppCompatActivity() {

    private lateinit var mSensorManager : SensorManager
    private lateinit var mAccelerometer : Sensor
    private lateinit var mMotionSensor : MotionSensor
    private var recipeCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Recipes")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //MotionSensor initialization for shake detection
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mMotionSensor = MotionSensor()
        mMotionSensor.setOnShakeListener(object : MotionSensor.OnShakeListener {
            override fun onShake(count: Int) {
                //count -> number of shakes
                randomRecipe(count)
            }
        })

        startResponse("https://services.campbells.com/api/Recipes//recipe?q=")
    }

    public override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(
            mMotionSensor,
            mAccelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    public override fun onPause() {
        mSensorManager.unregisterListener(mMotionSensor)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuCreator = menuInflater
        menuCreator.inflate(R.menu.menu, menu)
        val mySearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as android.widget.SearchView).apply {
            setSearchableInfo(mySearchManager.getSearchableInfo(componentName))
            isIconifiedByDefault = false
        }
        return true
    }

    override fun onNewIntent(intent: Intent) {
        val query = intent.getStringExtra(SearchManager.QUERY)
        val searchUrl = "https://services.campbells.com/api/Recipes//recipe?q=" + query
        startResponse(searchUrl)
    }

    fun randomRecipe(count: Int) {
        if (count == 1) {
            val recipeNum = (0 until recipeCount).random()
            startResponse("https://services.campbells.com/api/Recipes//recipe?q=", recipeNum)
            Toast.makeText(this, "Random recipe selected!", Toast.LENGTH_LONG).show()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startResponse(url: String, number: Int = -1) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                var recipesObjects: List<Recipe.RecipeObject> = parseRecipeAPI(response)
                //random recipe select
                if(number >= 0) {
                    recipesObjects = listOf(recipesObjects.get(number))
                } else {
                    recipeCount = recipesObjects.count()
                }

                // put in the map
                for(recipe in recipesObjects){
                    Recipe.recipeMap.put(recipe.name, recipe)
                }

                setupRecyclerView(recycler_view, recipesObjects)

            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, "There was an error: $error" , Toast.LENGTH_SHORT).show()
            }
        )
        VolleyService.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }


    private fun setupRecyclerView(recyclerView: RecyclerView, list: List<Recipe.RecipeObject>){

        recyclerView.adapter = MyRecyclerViewAdapter(list, this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

    }

    class MyRecyclerViewAdapter(
        private val values: List<Recipe.RecipeObject>,
        private val context: Context
    ): RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Recipe.RecipeObject

//                Toast.makeText(context, item.name , Toast.LENGTH_SHORT).show()

                val intent = Intent(v.context, RecipeDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.name)
                }
                v.context.startActivity(intent)
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return values.size
        }

        override fun onBindViewHolder(holder: MyRecyclerViewAdapter.ViewHolder, position: Int) {
            val item = values[position]
            holder.titleView.text = item.name

            if (item.imageUrl.isNullOrBlank()){
            }else {
                Picasso.with(context).load(item.imageUrl).fit().centerCrop().into(holder.imageView)
            }

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }



        inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val titleView: TextView = view.title_view
            val imageView: ImageView = view.image_view
        }


    }




}
