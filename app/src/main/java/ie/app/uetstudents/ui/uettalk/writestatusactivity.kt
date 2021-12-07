package ie.app.uetstudents.ui.uettalk

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ie.app.uetstudents.R
import kotlinx.android.synthetic.main.fragment_writing_status.*
import kotlinx.android.synthetic.main.fragment_writing_status.view.*
import kotlinx.android.synthetic.main.layout_bottomsheet_anh.view.*
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class writestatusactivity : AppCompatActivity() {

    private val MY_REQUEST: Int = 1111
    private val IMG_REQUEST: Int = 1000
    private val  CAMERA_REQUEST: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_writing_status)


        val currentTime: Date = Calendar.getInstance().getTime()

        val sdf : SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy \n kk:mm:ss")


        val string : String = sdf.format(currentTime)

        edt_status.setText(string)

        btn_addimage_status.setOnClickListener {

            val alertDialogbuild: AlertDialog.Builder = AlertDialog.Builder(this)
            val dialogview =
                LayoutInflater.from(this).inflate(R.layout.layout_bottomsheet_anh, null)
            alertDialogbuild.setView(dialogview)
            val dialog = alertDialogbuild.create()
            dialog.show()

            dialogview.anh_camera.setOnClickListener {

                val cameraIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                 startActivityForResult(cameraIntent,CAMERA_REQUEST)

                dialog.dismiss()
            }
            dialogview.anh_thumuc.setOnClickListener {
                onclickRequestPermission()
                dialog.dismiss()
            }
        }
    }

    fun onclickRequestPermission()
    {
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.M)
        {
            openGallery()
            return
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            openGallery()
        }else
        {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST)
        {
            if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openGallery()
            }
        }

    }

    fun openGallery()
    {
        val intent : Intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        //mActivityResultLauncher.launch(Intent.createChooser(intent,"select picture"))
        startActivityForResult(Intent.createChooser(intent,"select picture"),IMG_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== IMG_REQUEST && resultCode == Activity.RESULT_OK && data!= null)
        {
            val uri : Uri = data.data!!

        }
        if (requestCode== CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!= null)
        {
            val uri : Uri = data.data!!

        }

    }

}