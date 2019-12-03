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
import android.R.attr.start
import android.content.Context
import android.hardware.SensorManager
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import jtander7.uw.edu.info448_project.MotionSensor.OnShakeListener
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.Sensor




class MainActivity : AppCompatActivity() {

    private lateinit var mSensorManager : SensorManager
    private lateinit var mAccelerometer : Sensor
    private lateinit var mMotionSensor : MotionSensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // MotionSensor initialization for shake detection
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager
            .getDefaultSensor(TYPE_ACCELEROMETER)
        mMotionSensor = MotionSensor()
        mMotionSensor.setOnShakeListener(object : OnShakeListener {

            override fun onShake(count: Int) {
                //count -> number of shakes
                randomRecipe(count)
            }
        })
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

    //select random recipe to show
    fun randomRecipe(count: Int) {
        if (count == 1) {
            //toast to see if shake works
            Toast.makeText(this, "Shake detected", Toast.LENGTH_LONG).show()
        }
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
