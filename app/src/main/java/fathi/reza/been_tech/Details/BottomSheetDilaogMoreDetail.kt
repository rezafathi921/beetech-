package fathi.reza.been_tech.Details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.CHART
import fathi.reza.been_tech.Utills.COMPARE
import fathi.reza.been_tech.Utills.SHARE

class BottomSheetDilaogMoreDetail : BottomSheetDialogFragment() {
    var myView: View? = null
    lateinit var setOnMenuItemClickListenerr: SetOnMenuItemClickListenerr
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (myView == null) {
            myView = inflater.inflate(R.layout.bottom_sheet_dialog_detail_more, null)
        }
        return myView
    }

    fun setOnItemClick(setOnMenuItemClickListenerr: SetOnMenuItemClickListenerr) {
        this.setOnMenuItemClickListenerr=setOnMenuItemClickListenerr
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chart = view.findViewById<TextView>(R.id.txt_moreDetail_chart)
        val share = view.findViewById<TextView>(R.id.txt_moreDetail_share)
        val compare = view.findViewById<TextView>(R.id.txt_moreDetail_compare)

        chart.setOnClickListener {
            setOnMenuItemClickListenerr.onMenuClick(CHART)
            dismiss()
        }
        share.setOnClickListener {
            setOnMenuItemClickListenerr.onMenuClick(SHARE)
            dismiss()
        }
        compare.setOnClickListener {
            setOnMenuItemClickListenerr.onMenuClick(COMPARE)
            dismiss()
        }
    }

    interface SetOnMenuItemClickListenerr {
        fun onMenuClick(itemType: String)
    }

}