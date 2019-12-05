package jtander7.uw.edu.info448_project

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.content_recipe_detail.view.*
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 */
class ItemDetailFragment : Fragment() {

    /**
     * RecipeObject this fragment is presenting.
     */
    private var item: Recipe.RecipeObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = Recipe.recipeMap[it.getString(ARG_ITEM_ID)]
//                activity?.toolbar_layout?.title = " "

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            Picasso.with(context).load(item?.imageUrl).fit().centerCrop().into(rootView.image_detail_view)
            rootView.text_detail_name.text= it.name
            rootView.text_detail_description.text = it.description
            rootView.text_detail_servings.text = "Servings: " + it.servings

            var ingredients = ""

            for (i in it.ingredients){
                ingredients += "- " + i + "\n"
            }
            rootView.text_detail_ingredients.text = ingredients

            var numb = 1
            var directions = ""
            for (d in it.directions){
                directions += numb.toString() + ")  " + d + " \n \n"
                numb++
            }
            rootView.text_detail_directions.text = directions

        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
