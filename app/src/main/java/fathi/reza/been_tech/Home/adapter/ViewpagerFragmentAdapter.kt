package fathi.reza.been_tech.Home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fathi.reza.been_tech.Data.Banners
import fathi.reza.been_tech.Home.FragmentSlider

class ViewpagerFragmentAdapter(val fragment: Fragment,val Banners:List<Banners>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =Banners.size


    override fun createFragment(position: Int): Fragment =
        FragmentSlider.newInstance(Banners[position])
}