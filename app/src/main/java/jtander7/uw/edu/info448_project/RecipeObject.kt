package jtander7.uw.edu.info448_project

import android.os.Parcelable
import android.util.Log
import android.webkit.URLUtil
import kotlinx.android.parcel.Parcelize
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup

/**
 * A class representing a single recipe item. Can be parsed for a recipe name, description,
 * serving size, image url, list of ingredients, and a list of steps to cook.
 */
@Parcelize
data class RecipeObject(
    val name:String,
    val description:String,
    val servings:String,
    val imageUrl:String?,
    val ingredients:MutableList<String>,
    val directions:MutableList<String>
) : Parcelable

fun parseRecipeAPI(response: JSONObject):List<RecipeObject> {

    val recipes = mutableListOf<RecipeObject>()

    try {
        val jsonRecipes = response.getJSONArray("Result") //response.Result

        for (i in 0 until Math.min(jsonRecipes.length(), 20)) { //stop at 20
            val recipeItemObj = jsonRecipes.getJSONObject(i)

            //handle image url
            val jsonMetaArray = recipeItemObj.getJSONArray("RecipeMetaRecords")
            val jsonImageObj = jsonMetaArray.getJSONObject(4)
            var imageUrl:String? = jsonImageObj.getString("Value")
            if (imageUrl == "null" || !URLUtil.isValidUrl(imageUrl)) {
                imageUrl = null //make actual null value
            }

            // handle ingredients array
            val jsonIngredArray = recipeItemObj.getJSONArray("Ingredients")
            val ingredients = mutableListOf<String>()
            for(item in 0 until jsonIngredArray.length()) {
                val ingredObj = jsonIngredArray.getJSONObject(item)
                try {
                    val ingredObjExternal = ingredObj.getJSONObject("ExternalProduct")
                    val ingredName = ingredObjExternal.getString("Name")
                    val ingredUnit = ingredObj.getString("Unit")
                    val ingredAmount = ingredObj.getString("Amount")
                    val ingredString = ingredAmount + " " + ingredUnit + " " + ingredName
                    ingredients.add(ingredString)
                } catch (e: JSONException) {
                    println("Discard malformed JSON.")
                }
            }

            // handle recipe array
            val jsonRecipeArray = recipeItemObj.getJSONArray("RecipeSteps")
            val directions = mutableListOf<String>()
            for(item in 0 until jsonRecipeArray.length()) {
                val recipeStepObj = jsonRecipeArray.getJSONObject(item)
                val recipeStepDesc = recipeStepObj.getString("Description")
                val parsed = Jsoup.parse(recipeStepDesc).text() //remove html from description strings
                directions.add(parsed)
            }

            // Create recipe object
            val finalRecipe = RecipeObject(
                name = recipeItemObj.getString("Name"),
                description = Jsoup.parse(recipeItemObj.getString("Description")).text(),
                servings = recipeItemObj.getString("Servings"),
                imageUrl = imageUrl,
                ingredients = ingredients,
                directions = directions
            )

            recipes.add(finalRecipe)
        } //end for loop
    } catch (e: JSONException) {
        Log.e("Parse-Error", "Error parsing json from API: ", e)
    }

    return recipes
}