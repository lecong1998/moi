package ie.app.uetstudents.ui.diendan.detailForum

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import ie.app.uetstudents.R
import ie.app.uetstudents.RealPathUtil.RealPathUtil
import ie.app.uetstudents.Repository.Repository
import ie.app.uetstudents.adapter.ClickItemCommentLike
import ie.app.uetstudents.adapter.adapter_comment
import ie.app.uetstudents.adapter.adapter_hienthianh
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.Comment.post.Question
import ie.app.uetstudents.ui.Entity.Comment.post.comment_post
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDtoX
import ie.app.uetstudents.ui.Entity.like.Post.Account
import ie.app.uetstudents.ui.Entity.like.Post.Comment
import ie.app.uetstudents.ui.Entity.like.Post.like_comment
import ie.app.uetstudents.ui.Entity.like_question.post.like_question
import ie.app.uetstudents.ui.Entity.notifications_comment.post.post_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.post.notification_question_post
import kotlinx.android.synthetic.main.fragment_detail_forum.*
import kotlinx.android.synthetic.main.fragment_detail_forum.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DetailForumFragment : Fragment(), DetailForumContract.View, ClickItemCommentLike {

    private val CAMERA_REQUEST: Int = 9999
    private var id_question: Int? = null

    private lateinit var adapter_comment: adapter_comment
    private lateinit var presenterDetailForum: DetailForumContract.Presenter
    private var page_comment = 1

    private var uri : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            id_question = it?.getInt("id_question")
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_forum, container, false)
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenterDetailForum = DetailForumPresenter(this, Repository(requireContext()))

        id_question?.let { presenterDetailForum.getDetailForum(it) }

        /*-------------------Thích Question---------------------------*/
        getApiLike(id_question!!,1)

        view.btn_like_detail.setOnClickListener {
            if(view.btn_like_detail.text == "Thích")
            {
                view.btn_like_detail.text ="Đã thích"
                view.btn_like_detail.setTextColor(R.color.purple_500)
                view.btn_like_detail.setCompoundDrawables(context?.getDrawable(R.drawable.ic_baseline_favorite_24),null,null,null)
                PostApiLike(id_question!!,1)
                getApiLike(id_question!!,1)
            }
            if(view.btn_like_detail.text=="Đã thích")
            {
                view.btn_like_detail.text ="Thích"
                view.btn_like_detail.setTextColor(R.color.black)
                val call : Call<like_question> = ApiClient.getClient.deletelikeQueston(1,id_question!!)
                call.enqueue(object : Callback<like_question>{
                    override fun onResponse(
                        call: Call<like_question>,
                        response: Response<like_question>
                    ) {
                        if (response.isSuccessful)
                        {
                            Log.e("Test_bỏ_like","Thành CÔng")
                        }
                    }

                    override fun onFailure(call: Call<like_question>, t: Throwable) {
                        Log.e("Test_bỏ_like","Thất bại")
                    }
                })
            }



        }
        /*---------------------------------------------------------------*/

        /*---------------------update Comment vào layout-----------------------------------*/
        id_question?.let { presenterDetailForum.getDetailComment(it, page_comment) }

        adapter_comment = adapter_comment(this)

        view.detail_comment_forum_recyclerview.layoutManager = LinearLayoutManager(context)
        view.detail_comment_forum_recyclerview.adapter = adapter_comment
        view.detail_comment_forum_recyclerview.isNestedScrollingEnabled= false
        view.detail_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v?.getChildAt(0)?.measuredHeight?.minus(v.measuredHeight))
                {
                    page_comment++
                    view.detailforum_progressbar.visibility = View.VISIBLE
                    id_question?.let { presenterDetailForum.getDetailComment(it,page_comment) }

                }
                if ( scrollY == oldScrollY.plus(v?.getChildAt(0)?.measuredHeight!!) )
                {
                    view.detailforum_progressbar.visibility = View.VISIBLE
                    id_question?.let { presenterDetailForum.getDetailComment(it,1) }
                }
            }
        })
        view.detail_comment_forum_recyclerview.scrollToPosition(-1)
        /*--------------------Lấy ảnh bình luận-----------------------------------------*/

        view.camera_comment.setOnClickListener {
            val cameraIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent,CAMERA_REQUEST)
        }

        /*----------------------Đăng -Bình luận-----------------------*/


        btndang_detail_forum.setOnClickListener(
            View.OnClickListener {
                detailforum_progressbar.visibility = View.VISIBLE
                if(edt_detail_forum.text.isEmpty())
                {
                    Toast.makeText(context,"Bạn chưa nhập bình luận!",Toast.LENGTH_LONG).show()
                    detailforum_progressbar.visibility = View.GONE
                }
                else
                {
                    CallApiComment(edt_detail_forum.text.toString(),1, id_question!!,uri)
                    edt_detail_forum.text.clear()
                    Toast.makeText(context,"Bình luận thành công",Toast.LENGTH_SHORT).show()
                    chuacocomment.text = ""
                    presenterDetailForum.getDetailComment(id_question!!,page_comment)
                    view.detail_comment_forum_recyclerview.adapter = adapter_comment
                    detailforum_progressbar.visibility = View.GONE
                    view.detail_comment_forum_recyclerview.scrollToPosition(0)
                }

            }
        )


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==CAMERA_REQUEST&& requestCode == Activity.RESULT_OK&& data != null)
        {
            uri = data.data!!
            Toast.makeText(context,"Đã thêm ảnh vào bình luận!",Toast.LENGTH_LONG).show()
        }
    }
    /*-------------------------Click Nút thích comment----------------------------------*/
    override fun clickOnItem(m: ie.app.uetstudents.ui.Entity.Comment.get.CommentDto) {

       // Toast.makeText(context,"Đã thích",Toast.LENGTH_SHORT).show()
        val idcomment = Comment(m.id!!.toInt())
        val account = Account(1)
        val likeComment = like_comment(account,idcomment)
        val call : Call<like_comment> = ApiClient.getClient.setLikeComment(likeComment)
        call.enqueue(object : Callback<like_comment>{
            override fun onResponse(call: Call<like_comment>, response: Response<like_comment>) {

                if (response.isSuccessful)
                {   Log.e("Test_API_Like_Comment","Thành công")
                    update_notification_comment("LIKE",m.id!!,"1")
                }
            }

            override fun onFailure(call: Call<like_comment>, t: Throwable) {
                Log.e("Test_API_Like_Comment","Thất bại")
            }
        })
    }

    /*-----------------------Lấy thông tin nội dung Question-----------------------*/
    override fun getDataView(data: QuestionDtoX) {
        txtcontent_forum.text = data.content.toString()
        //Log.e("data", data.content.toString())
        val thoigian :String = data.time?.substring(11,16).toString()
        val ngay : String = data.time?.substring(0,10).toString()
        time_detail_forum.setText(thoigian)
        date_detail_forum.setText(ngay)
        var listlink : ArrayList<String> = ArrayList()
        if (data.imageDtoList.isNotEmpty())
        {
            data.imageDtoList.forEach {
                listlink.add(it.image)
            }
        }
        var adapterhienthi = adapter_hienthianh(listlink)
        detail_listanh.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        detail_listanh.adapter = adapterhienthi

    }

    /*----------------Lấy Thông tin commnet---------------------------*/
    override fun getDataViewComment(datacomment: ie.app.uetstudents.ui.Entity.Comment.get.Comment) {
        if(datacomment.result_quantity != 0)
        {
            val soluotbinhluan = datacomment.result_quantity?.toInt()
            soluotbinhluan_detail.text =  "Có $soluotbinhluan bình luận!"
        }
        adapter_comment.setData(datacomment.commentDtoList)
        adapter_comment.notifyDataSetChanged()
        if (datacomment.commentDtoList.isEmpty())
        {
            chuacocomment.text = "Chưa có bình luận nào"
        }
        else
        {
            chuacocomment.text = ""
        }
        detailforum_progressbar.visibility = View.GONE
    }



    /*-----------------------Post comment lên  database---------------------------*/
    fun CallApiComment(comment: String,userid : Int, question_id : Int, uri: Uri?)
    {
       val account = ie.app.uetstudents.ui.Entity.Comment.post.Account(userid)
        val question = Question(question_id)
        val comment_post = comment_post(account,comment,question)
        val gson = Gson()
        val comment_to_json = gson.toJson(comment_post).toString()
        val requestbodyComment : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),comment_to_json)

        var strRealPath = RealPathUtil.getRealPath(requireContext(), uri!!)
        val file : File = File(strRealPath)

        val requestbodyFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
       var multipartbodyfile= MultipartBody.Part.createFormData("image_files",file.name,requestbodyFile)

        val call : Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment> = ApiClient.getClient.setCommentQuestion(multipartbodyfile,requestbodyComment)
        call.enqueue(object : Callback<ie.app.uetstudents.ui.Entity.Comment.get.Comment>{
            override fun onResponse(
                call: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment>,
                response: Response<ie.app.uetstudents.ui.Entity.Comment.get.Comment>
            ) {
                if (response.isSuccessful)
                {
                    Log.e("đăng comment","Đăng thành công")
                    update_notification("COMMENT",id_question!!,"1")

                }
            }

            override fun onFailure(
                call: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment>,
                t: Throwable
            ) {
                Log.e("Đăng comment","Thất bại")
            }
        })


    }
/*------------------ẩn menu-----------------------------*/
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item : MenuItem = menu.findItem(R.id.action_profile)
        val item1 : MenuItem = menu.findItem(R.id.action_search)
        val item2 : MenuItem = menu.findItem(R.id.action_notification)
        item.isVisible = false
        item1.isVisible = false
        item2.setVisible(false)
    }

    /*------------------------PostLike bài viết---------------------------------*/
    fun PostApiLike(id_question: Int, id_account : Int)
    {
        val account = ie.app.uetstudents.ui.Entity.like_question.post.Account(id_account)
        val question = ie.app.uetstudents.ui.Entity.like_question.post.Question(id_question)
        val likeQuestion = like_question(account, question)
        val call : Call<like_question> = ApiClient.getClient.postlikequestion(likeQuestion)
        call.enqueue(object : Callback<like_question>{
            override fun onResponse(call: Call<like_question>, response: Response<like_question>) {
                if (response.isSuccessful)
                {
                    update_notification("LIKE",id_question,"1")
                    Log.e("Test_PostLike","thành công")
                }
            }

            override fun onFailure(call: Call<like_question>, t: Throwable) {
                Log.e("Test_postlike","thất Bại")
            }
        })
    }
    /*-------------------------Lấy số người thích bài viết-----------------------------*/
    fun getApiLike(id_question: Int,page: Int)
    {
      presenterDetailForum.getPersonlikeQuestion(id_question, page)
    }
    override fun getDataViewPersonsLikeQuestion(songuoilike: Int) {
        if (songuoilike>0)
        {
            soluotlike_detail.text = "Có $songuoilike Người thích bài viết!"
        }
    }
   /*---------------------------update notification question-------------------------------------------*/
    fun update_notification(type_action : String, id_question: Int,username : String)
    {
        val notifi_item = notification_question_post(type_action,"",ie.app.uetstudents.ui.Entity.notifications_question.post.Question(id_question),username)
        presenterDetailForum.setNotificationQuestion(notifi_item)
    }
    /*-----------------------------update notification comment-----------------------------------------------*/
    fun update_notification_comment(type: String,id_comment : Int,username : String )
    {
        val notifiItem = post_notifi_comment(type,"",ie.app.uetstudents.ui.Entity.notifications_comment.post.Comment(id_comment),username)
        presenterDetailForum.setNotificationComment(notifiItem)
    }

}