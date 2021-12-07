package ie.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.login.SigninActivity
import kotlinx.android.synthetic.main.activity_start_app.*

class Start_app : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_app)
        val animation : Animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sequential)
        imageview_startapp.startAnimation(animation)
        Handler().postDelayed(Runnable {
            val intent : Intent = Intent(this,SigninActivity::class.java)
            startActivity(intent)
            finish()
        },4000)
    }
}