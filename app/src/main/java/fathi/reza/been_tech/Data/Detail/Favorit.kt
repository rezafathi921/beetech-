package fathi.reza.been_tech.Data.Detail


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorit(
    val id :String,
    val idproduct:String,
    val price: String,
    val title: String
): Parcelable