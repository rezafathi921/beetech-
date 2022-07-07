package fathi.reza.been_tech.Details.Chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import fathi.reza.been_tech.Details.Chart.viewmodel.PriceHistoryViewModel
import fathi.reza.been_tech.R
import fathi.reza.been_tech.Utills.DATA
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

val array= mutableListOf<String>()

class PriceHistoryActivity : AppCompatActivity() {
    val priceHistoryViewModel:PriceHistoryViewModel by inject{ parametersOf(intent.extras!!.getString(DATA))}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_history)

        val lineChart=findViewById<LineChart>(R.id.chart)
        val back=findViewById<ImageView>(R.id.img_property_backward)
        val titleToolbar=findViewById<TextView>(R.id.txt_property_tiltleToolbar)
        titleToolbar.setText("نمودار قیمت")

        back.setOnClickListener{
            finish()
        }
        priceHistoryViewModel.chartData.observe(this){


            if (it.isNotEmpty()) {
                val values: MutableList<Entry> = ArrayList()
                for (i in it.indices) {

                    values.add(Entry(i.toFloat(), it[i].price.toFloat()))
                    array.add(it[i].date)

                }
                val lineDataSet = LineDataSet(values,"نمودار قیمت های پیشین").apply {
                    color=ContextCompat.getColor(applicationContext,R.color.greenIcon)
                    valueTextColor=ContextCompat.getColor(applicationContext,R.color.black)
                    valueTextSize=15f
                    lineWidth=4f
                    setCircleColor(ContextCompat.getColor(applicationContext,R.color.red))
                    fillColor=ContextCompat.getColor(applicationContext,R.color.yellow)
                    setDrawFilled(true)
                    fillDrawable=ContextCompat.getDrawable(applicationContext,R.drawable.gradient_chart)
                }
                val iLineDataSet:MutableList<ILineDataSet> =  ArrayList()
                iLineDataSet.add(lineDataSet)
                val lineData=LineData(iLineDataSet)
                lineChart.data=lineData
                lineChart.animateX(400)
                val xAxis=lineChart.xAxis
                xAxis.valueFormatter=MyXAxisFormatter()
                xAxis.labelCount=4
                lineChart.invalidate()
                lineChart.setDrawBorders(true)


            }
        }
    }
    class MyXAxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return array.getOrNull(value.toInt()) ?: value.toString()
        }
    }
}




