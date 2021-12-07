package ie.app.uetstudents.ui.diendan.Write

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
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ie.app.uetstudents.R
import ie.app.uetstudents.RealPathUtil.RealPathUtil
import ie.app.uetstudents.adapter.OnclickItem_deleteanh
import ie.app.uetstudents.adapter.adapter_anhwrite
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDto
import ie.app.uetstudents.ui.Entity.Question.get.question
import ie.app.uetstudents.ui.Entity.Question.post.Account
import ie.app.uetstudents.ui.Entity.Question.post.Category
import ie.app.uetstudents.ui.Entity.Question.post.QuestionPost
import ie.app.uetstudents.ui.Entity.Question.post.TypeContent
import kotlinx.android.synthetic.main.fragment_write.*
import kotlinx.android.synthetic.main.fragment_write.view.*
import kotlinx.android.synthetic.main.fragment_writing_status.*
import kotlinx.android.synthetic.main.layout_bottomsheet_anh.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class WriteFragment : Fragment(),OnclickItem_deleteanh{


    var select_nganhid : Int = 1
    private val MY_REQUEST: Int = 1111
    private val IMG_REQUEST: Int = 1000
    private val  CAMERA_REQUEST: Int = 100

    private var listanh : ArrayList<Uri> = ArrayList()
    private lateinit var adapteranh : adapter_anhwrite
    private var uri : Uri? = null



    private var database : QuestionDto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(radio_group.checkedRadioButtonId)
        {
            R.id.select_cntt -> {select_nganhid = 1}
            R.id.select_khmt -> {select_nganhid = 2}
            R.id.select_httt -> {select_nganhid = 3}
            R.id.select_cnktdttt -> {select_nganhid = 4}
            R.id.select_vlkt -> {select_nganhid = 5}
            R.id.select_ktnl -> {select_nganhid = 6}
            R.id.select_cokt -> {select_nganhid = 7}
            R.id.select_cnktcdt -> {select_nganhid = 8}
            R.id.select_mmttt -> {select_nganhid = 9}
            R.id.select_ktmt -> {select_nganhid = 10}
            R.id.select_cnktxdgt -> {select_nganhid = 11}
            R.id.select_cnhkvt -> {select_nganhid = 12}
            R.id.select_ktrb -> {select_nganhid = 13}
            R.id.select_cnnn -> {select_nganhid = 14}
            R.id.select_ktdktdh -> {select_nganhid = 15}

        }
        /*-----------------thêm ảnh---------------------------------*/
            view.write_camera.setOnClickListener {

                val alertDialogbuild : AlertDialog.Builder = AlertDialog.Builder(context)
                val dialogview = LayoutInflater.from(context).inflate(R.layout.layout_bottomsheet_anh,null)
                alertDialogbuild.setView(dialogview)
                val dialog = alertDialogbuild.create()
                dialog.show()

                dialogview.anh_camera.setOnClickListener {

                    val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
            listanh_write_forum.visibility = View.GONE
        }
        else{
            listanh_write_forum.visibility = View.VISIBLE
        }
        listanh_write_forum.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        listanh_write_forum.adapter= adapteranh

        /*---------------------đăng--------------------------*/
        chuyentrang?.setOnClickListener {
            callapi(edtxt_status.text.toString(),write_title.text.toString(),select_nganhid, uri,1)
            it.findNavController().navigate(R.id.writeFragment_to_forumFragment)

        }
    }

    fun callapi(writeContent : String,title : String, select_nganh : Int,anh : Uri?, user: Int)
    {
        val category = Category(1)
        val type_content = TypeContent(1)
        val account = Account(1)
        val question = QuestionPost(account,category,writeContent,title,type_content)
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
        val item : MenuItem = menu.findItem(R.id.action_search)
        val item2 : MenuItem = menu.findItem(R.id.action_profile)
        val item3 : MenuItem = menu.findItem(R.id.action_notification)
        item.isVisible = false
        item2.setVisible(false)
        item3.setVisible(false)
    }

    fun onclickRequestPermission()
    {
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.M)
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
            listanh_write_forum.adapter?.notifyDataSetChanged()


        }
        if (requestCode== CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data!= null)
        {
            uri = data.data!!
            listanh.add(uri!!)
            listanh_write_forum.adapter?.notifyDataSetChanged()
        }

    }

    override fun CLickDelete(anh: Uri) {
        listanh.remove(anh)
        listanh_write_forum.adapter?.notifyDataSetChanged()
    }


}
