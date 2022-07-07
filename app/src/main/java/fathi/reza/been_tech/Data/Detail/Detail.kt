package fathi.reza.been_tech.Data.Detail

import com.google.gson.annotations.SerializedName

data class Detail(

    val id: String,
    val title: String,
    val price: String,
    val cat: String,
    val introduction: String,
    val tedad_mojood: String,
    val discount: String,
    val special: String,
    val time_special: String,
    val onlyclicksite: String,
    val viewd: String,
    val colors: String,
    val garantee: String,
    val idcategory: String,
    val weight: String,
    val pic: String,
    val rating: String,
    val parent_and_child: List<ParentAndChild>,
    var rating_item:String,
    val favorit_for_user:List<Favorit>

)