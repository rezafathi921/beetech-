package fathi.reza.been_tech

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors
import fathi.reza.been_tech.Data.CartItemCount
import fathi.reza.been_tech.Utills.setupWithNavController
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private var currentNavController: LiveData<NavController>? = null
    lateinit var bottomNavigationView :BottomNavigationView
    val mainViewModel: MainViewModel by viewModel ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventBus.getDefault().register(this)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }




    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
         bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navGraphIds = listOf(R.navigation.home, R.navigation.cat, R.navigation.shop,R.navigation.beetech)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    /**
     * Overriding popBackStack is necessary in this case if the app is started from the deep link.
     */
    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true) {
            super.onBackPressed()
        }
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.getCartItemCount()

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
     fun checkCartItemCountEvent(cartItemCount: CartItemCount){
        var badge=bottomNavigationView.getOrCreateBadge(R.id.shop)
        badge.number=cartItemCount.count.toInt()
        badge.badgeTextColor=ContextCompat.getColor(applicationContext,R.color.white)
        badge.backgroundColor= ContextCompat.getColor(applicationContext,R.color.red)
        badge.badgeGravity=BadgeDrawable.TOP_START
        badge.isVisible=cartItemCount.count.toInt()>0

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }



}