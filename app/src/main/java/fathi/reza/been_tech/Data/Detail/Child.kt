package fathi.reza.been_tech.Data.Detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Child(
    val idproduct: String,
    val title: String,
    val `val`: String
):Parcelable