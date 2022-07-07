package fathi.reza.been_tech.Details


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Data.Detail.Child
import fathi.reza.been_tech.Data.Detail.ParentAndChild
import fathi.reza.been_tech.Details.adapter.PropertyAdapter
import fathi.reza.been_tech.Details.viewModel.DetailViewModel
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.MyActivity
import fathi.reza.been_tech.Utills.mixParentAndChild
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*
import kotlin.collections.ArrayList


class PropertyActivity : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poperty)
            val back=findViewById<ImageView>(R.id.img_property_backward)
            val rv_property=findViewById<RecyclerView>(R.id.rv_property)
            rv_property.layoutManager= LinearLayoutManager(rv_property.context, LinearLayoutManager.VERTICAL,false)

            val intent=intent
            if (intent!=null) {
                val arrylistParentAndChild = intent.extras?.getParcelableArrayList<ParentAndChild>("listparentAndChild")
                if (arrylistParentAndChild != null) {
                    val final_array=mixParentAndChild(arrylistParentAndChild)
                    val propertyAdapter:PropertyAdapter by inject{ parametersOf(final_array)}
                    rv_property.adapter=propertyAdapter
                }

            }
                back.setOnClickListener{
                    finish()
                }
            }

        }

