package fathi.reza.been_tech.Data


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banners(
    val id: String,
    val pic: String,
    val type: String
) : Parcelable