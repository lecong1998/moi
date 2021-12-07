package ie.app.uetstudents

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import ie.app.uetstudents.adapter.adapter_notification
import ie.app.uetstudents.databinding.ActivityMainBinding
import ie.app.uetstudents.ui.Entity.notifications_comment.get.NotificationCommentDto
import ie.app.uetstudents.ui.notifications.notification_service
import ie.app.uetstudents.ui.notifications.notifications_Fragment
import ie.app.uetstudents.ui.profile.ProfileActivity
import ie.app.uetstudents.ui.timkiem.*
import kotlinx.android.synthetic.main.activity_notifications.*


class MainActivity : AppCompatActivity() {

    var mBroadcastAction = "STRING_BROADCAST_ACTION"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var  view : View

    private  var username : String = "16020859"
    private var id_user : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        username = intent.getStringExtra("username")
        id_user = intent.getIntExtra("id_user",0)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding?.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_notifications,
                R.id.nav_document,
                R.id.nav_exam,
                R.id.nav_news,
                R.id.nav_forum,
                R.id.nav_uettalk,
                R.id.nav_login
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val bundle = Bundle()
        bundle.putInt("id_user", id_user!!)


  /*      navView.setNavigationItemSelectedListener(object : NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId)
                {
                    R.id.nav_home -> {
                        navController.navigate(R.id.nav_home,bundle)
                    }
                    R.id.nav_notifications -> {
                        navController.navigate(R.id.nav_notifications,bundle)
                    }
                    R.id.nav_document -> {
                        navController.navigate(R.id.nav_document,bundle)
                    }
                    R.id.nav_exam -> {
                        navController.navigate(R.id.nav_exam,bundle)
                    }
                    R.id.nav_news -> {
                        navController.navigate(R.id.nav_news,bundle)
                    }
                    R.id.nav_forum -> {
                        navController.navigate(R.id.nav_forum,bundle)
                    }
                    R.id.nav_uettalk -> {
                        navController.navigate(R.id.nav_uettalk,bundle)
                    }
                    R.id.nav_login -> {
                        navController.navigate(R.id.nav_login,bundle)
                    }
                }

                return true
            }
        })
*/



        val intent = Intent(this, notification_service::class.java)
        startService(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

   @SuppressLint("ResourceAsColor")
   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {

                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.action_search)
            }
            R.id.action_profile -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.action_profile)
            }
            R.id.action_notification -> {

                val navController = findNavController(R.id.nav_host_fragment_content_main)
                navController.navigate(R.id.action_notification)

            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onSupportNavigateUp(): Boolean {
        val bundle = Bundle()
        bundle.putInt("id_user", id_user!!)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        //navController.navigate(R.id.mobile_navigation,bundle)
        return navController.navigateUp(appBarConfiguration)|| super.onSupportNavigateUp()
    }

  /*  override fun onStart() {
        super.onStart()

       // val intentFilter = IntentFilter(mBroadcastAction)
        //registerReceiver(mbroadcastReceiver,intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        //unregisterReceiver(mbroadcastReceiver)
    }

 /*   val mbroadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action?.equals(MainActivity().mBroadcastAction)!!)
            {

                val jsonString : String = intent.getStringExtra("broadcast")
                val type = object : TypeToken<List<NotificationCommentDto?>?>() {}.type
                val gson : Gson = Gson()
                val notifi : List<NotificationCommentDto> = gson.fromJson(jsonString,type)

            }
        }
    }*/

*/
}