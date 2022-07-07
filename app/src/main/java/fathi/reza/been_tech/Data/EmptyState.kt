package fathi.reza.been_tech.Data

import android.widget.Button
import androidx.annotation.StringRes

data class EmptyState(
    val show:Boolean,
    @StringRes var message:Int=0,
    val button: Boolean=false
)