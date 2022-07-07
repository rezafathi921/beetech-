package fathi.reza.been_tech.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fathi.reza.been_tech.Custom.MyImageView
import fathi.reza.been_tech.Data.Banners
import fathi.reza.been_tech.R
import org.koin.android.ext.android.inject

class FragmentSlider : Fragment() {
    val imageLoading:ImageLoading by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val banner= arguments?.getParcelable<Banners>("banner")
        val view= inflater.inflate(R.layout.fragment_slider, container, false)
        imageLoading.load(view as MyImageView,banner!!.pic)
        return view

    }

companion object{
    fun newInstance(banner:Banners):FragmentSlider{
     return  FragmentSlider().apply {
            arguments=Bundle().apply {
                putParcelable("banner",banner)
            }
        }
    }

}

}