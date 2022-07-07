package fathi.reza.been_tech.Data.Detail

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class ParentAndChild(
    val id: String,
    val title: String,
    val idcategory: String,
    val parent: String,
    val filter: String,
    val filter_right: String,
    val child:@RawValue List<Child>

):Parcelable