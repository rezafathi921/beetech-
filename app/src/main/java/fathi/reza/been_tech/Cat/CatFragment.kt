package fathi.reza.been_tech.Cat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fathi.reza.been_tech.Cat.adapter.AllCatItemAdapter
import fathi.reza.been_tech.Cat.viewmodel.AllCatItemViewModel
import fathi.reza.been_tech.Data.CatFragment.SecondLevel
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.MyFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class CatFragment : MyFragment() {
        val allCatItemViewModel:AllCatItemViewModel by  viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val frameShowRv=view.findViewById<LinearLayout>(R.id.frame_cat_shoewRV)


        allCatItemViewModel.progressBarLiveData.observe(viewLifecycleOwner,{
                        showProgressBar(it)
        })

        allCatItemViewModel.allCatItemLiveData.observe(viewLifecycleOwner,{
            for (item in it.indices) {
                if (it[item].socondItem.isNotEmpty()){
                val firstLevel = TextView(requireContext())
                firstLevel.text = it[item].cat
                    firstLevel.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                    val padding=8
                    val topPadding=10
                    val rightPadding=16
                    val density=resources.displayMetrics.density
                    val topPadingFinal=topPadding*density.toInt()
                    val rightPaddingFinal=rightPadding*density.toInt()
                    val paddingFinal=padding*density.toInt()
                    firstLevel.setPadding(paddingFinal,topPadingFinal,rightPaddingFinal,0)
                frameShowRv.addView(firstLevel)
                val rv = RecyclerView(requireContext())
                rv.layoutManager =LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
                val allCatItemAdapter: AllCatItemAdapter by inject()
                allCatItemAdapter.secondLevel = it[item].socondItem as ArrayList<SecondLevel>
                rv.adapter = allCatItemAdapter
                frameShowRv.addView(rv)
                }

            }

        })
    }


}