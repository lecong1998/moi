package ie.app.uetstudents.ui.uettalk

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentResolverCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.API.ApiClient
import kotlinx.android.synthetic.main.fragment_writing_status.*
import kotlinx.android.synthetic.main.fragment_writing_status.view.*
import kotlinx.android.synthetic.main.layout_bottomsheet.*
import kotlinx.android.synthetic.main.layout_bottomsheet.view.*
import kotlinx.android.synthetic.main.layout_bottomsheet_anh.*
import kotlinx.android.synthetic.main.layout_bottomsheet_anh.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URI
import kotlin.jvm.internal.Intrinsics

import org.jetbrains.annotations.NotNull

import kotlin.Unit
import ie.app.uetstudents.MainActivity

import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import gun0912.tedbottompicker.TedBottomPicker
import ie.app.uetstudents.RealPathUtil.RealPathUtil
import ie.app.uetstudents.adapter.OnclickItem_deleteanh
import ie.app.uetstudents.adapter.adapter_anhwrite
import ie.app.uetstudents.ui.Entity.Question.get.question
import ie.app.uetstudents.ui.Entity.Question.post.*
import kotlinx.android.synthetic.main.fragment_uettalk.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class WritingStatusFragment : Fragment(),OnclickItem_deleteanh {


    private val MY_REQUEST: Int = 1111
    private val IMG_REQUEST: Int = 1000
    private val  CAMERA_REQUEST: Int = 100

    private var listanh : ArrayList<Uri> = ArrayList()
    private lateinit var adapteranh : adapter_anhwrite

    var uri : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_writing_status, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*-------------------Chọn ảnh--------------------------------*/

        view.btn_addimage_status.setOnClickListener {

             val alertDialogbuild : AlertDialog.Builder = AlertDialog.Builder(context)
             val dialogview = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheet_anh,null)
             alertDialogbuild.setView(dialogview)
             val dialog = alertDialogbuild.create()
             dialog.show()

            dialogview.anh_camera.setOnClickListener {

                val cameraIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(cameraIntent,CAMERA_REQUEST)

                dialog.dismiss()
            }
            dialogview.anh_thumuc.setOnClickListener {
                onclickRequestPermission()
                dialog.dismiss()
            }

        }
        adapteranh = adapter_anhwrite(listanh,this)
        if (listanh == null)
        {
            write_themanh.visibility = View.GONE
        }
        else{
            write_themanh.visibility = View.VISIBLE
        }
        write_themanh.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        write_themanh.adapter= adapteranh

        /*------------------Đăng bài viết-----------------------------*/
        update_status.setOnClickListener {

            callApi(edt_status.text.toString(),uri,1)
            this.findNavController().navigate(R.id.action_writingStatusFragment_to_nav_uettalk)

        }
    }

    fun onclickRequestPermission()
    {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M)
        {
            openGallery()
            return
        }
        if (activity?.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
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
            uri = data.data!!
            listanh.add(uri!!)
            write_themanh.adapter?.notifyDataSetChanged()


        }
        if (requestCode== CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!= null)
        {
            uri = data.data!!
            listanh.add(uri!!)
            write_themanh.adapter?.notifyDataSetChanged()
        }

    }


    private fun callApi(writeContent: String,anh: Uri?,user : Int) {
        val category = Category(1)
        val type_content = TypeContent(2)
        val account = Account(1)
        val question = QuestionPost(account,category,writeContent,writeContent,type_content)
        val gson : Gson = Gson()
        val question_to_json : String = gson.toJson(question).toString()
        val requestbodyQuestion : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),question_to_json)

        var multipartbodyfile : MultipartBody.Part? = null

        if(uri != null)
        {
            Log.e("uri",uri.toString())
            var strRealPath = RealPathUtil.getRealPath(requireContext(), uri!!)
            val file : File = File(strRealPath)
            val requestbodyFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
            // listrequestbody.add(requestbodyFile)
            multipartbodyfile= MultipartBody.Part.createFormData("image_files",file.name,requestbodyFile)

        }
        else
        {
            multipartbodyfile = null
        }



        val call : Call<question> = ApiClient.getClient.setQuestion(multipartbodyfile,requestbodyQuestion)
        call.enqueue(object : Callback<question>{
            override fun onResponse(call: Call<question>, response: Response<question>) {
                if (response.isSuccessful)
                {
                    Log.e("Đăng thành công","Đăng thành công")
                }
            }

            override fun onFailure(call: Call<question>, t: Throwable) {
                Log.e("đăng thành công","Thất bại")
            }
        })

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item : MenuItem= menu.findItem(R.id.action_search)
        val item2 : MenuItem = menu.findItem(R.id.action_profile)
        val item3 : MenuItem = menu.findItem(R.id.action_notification)
        item.isVisible = false
        item2.setVisible(false)
        item3.isVisible = false
    }

    override fun CLickDelete(anh: Uri) {
        listanh.remove(anh)
        write_themanh.adapter?.notifyDataSetChanged()
    }
}