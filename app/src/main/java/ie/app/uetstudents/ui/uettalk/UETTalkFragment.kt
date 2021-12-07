package ie.app.uetstudents.ui.uettalk

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import ie.app.uetstudents.R
import ie.app.uetstudents.RealPathUtil.RealPathUtil
import ie.app.uetstudents.Repository.Repository
import ie.app.uetstudents.adapter.*
import ie.app.uetstudents.databinding.FragmentUettalkBinding
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.Comment.post.Question
import ie.app.uetstudents.ui.Entity.Comment.post.comment_post
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDtoX
import ie.app.uetstudents.ui.Entity.Question.get.question
import ie.app.uetstudents.ui.Entity.like.Post.Account
import ie.app.uetstudents.ui.Entity.like.Post.Comment
import ie.app.uetstudents.ui.Entity.like.Post.like_comment
import ie.app.uetstudents.ui.Entity.notifications_comment.post.post_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.post.notification_question_post
import ie.app.uetstudents.ui.diendan.detailForum.DetailForumContract
import ie.app.uetstudents.ui.diendan.detailForum.DetailForumPresenter
import ie.app.uetstudents.ui.diendan.forum_main.forumContract
import ie.app.uetstudents.ui.diendan.forum_main.forumPresenter
import kotlinx.android.synthetic.main.fragment_uettalk.*
import kotlinx.android.synthetic.main.fragment_uettalk.view.*
import kotlinx.android.synthetic.main.layout_bottomsheet.*
import kotlinx.android.synthetic.main.sheetbottom_comment_uettalk.*
import kotlinx.android.synthetic.main.sheetbottom_comment_uettalk.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UETTalkFragment: Fragment() , forumContract.View,OnClickItem_UetTalk,DetailForumContract.View,
    ClickItemCommentLike {

    private val CAMERA_REQUEST: Int = 8888
    private var _binding: FragmentUettalkBinding? = null

    private val binding get() = _binding!!

    private lateinit var presenter: forumContract.Presenter
    private lateinit var adapter_uettalk : adapter_itemuettalk

    private var bottomSheetDialog : BottomSheetDialog? = null
    private lateinit var adapter_comment_uettalk : adapter_comment
    private lateinit var presenter_uettalk_comment: DetailForumContract.Presenter

    private lateinit var bottomSheetView : View

    private var page_comment : Int = 1
    private var page_uettalk: Int =1

     var uri : Uri? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUettalkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        presenter = forumPresenter(this, Repository(requireContext()))
        presenter_uettalk_comment = DetailForumPresenter(this, Repository(requireContext()))


        presenter.getQuestions(2,page_uettalk)
        adapter_uettalk = adapter_itemuettalk(this)
        root.recyclerview_item_uettalk.layoutManager = LinearLayoutManager(requireContext())
            root.recyclerview_item_uettalk.isNestedScrollingEnabled = false


        root.recyclerview_item_uettalk.adapter= adapter_uettalk
        root.recyclerview_item_uettalk.scrollToPosition(0)
        root.uettalk_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v?.getChildAt(0)?.measuredHeight?.minus(v!!?.measuredHeight) ?: Int)
                {
                    page_uettalk++
                    root.uet_talk_progressbar.visibility= View.VISIBLE
                    presenter.getQuestions(2,page_uettalk)
                }
            }
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        status_uettalk.setOnClickListener {
            this.findNavController().navigate(R.id.action_nav_uettalk_to_writingStatusFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*-----------------update dữ liệu question vào Layout -----------------------------------*/
    override fun updateViewData(data: question) {
        uet_talk_progressbar?.visibility = View.GONE
        adapter_uettalk.setData(data.questionDtoList)
        recyclerview_item_uettalk?.adapter?.notifyDataSetChanged()
    }

    /*-----------------------Click vào btn thích------------------------------------*/
    override fun ClickItem_like(QuestionDto: QuestionDtoX) {
        Toast.makeText(context,"đã thích",Toast.LENGTH_LONG).show()
        update_notification("LIKE",QuestionDto.id!!,"1")

    }

    /*-----------------------Chuyenr đến bottomsheet comment--------------------------------*/
    override fun ClickItem_comment(QuestionDto: QuestionDtoX) {
       // Toast.makeText(context,"Bình luận",Toast.LENGTH_LONG).show()
        //presenter_uettalk_comment = DetailForumPresenter(this, Repository(requireContext()))



        adapter_comment_uettalk = adapter_comment(this)


        bottomSheetDialog = BottomSheetDialog(
            this@UETTalkFragment.requireContext(),R.style.BottomSheetDialogTheme
        )
         bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.sheetbottom_comment_uettalk,bottomsheet_uettalk )

        bottomSheetView.comment_recyclerview_uettalk.layoutManager =
            LinearLayoutManager(context)

        presenter_uettalk_comment.getDetailComment(QuestionDto.id?.toInt()!!,page_comment)
        bottomSheetView.comment_recyclerview_uettalk.isNestedScrollingEnabled = false
        bottomSheetView.comment_recyclerview_uettalk.adapter = adapter_comment_uettalk
        bottomSheetView.comment_recyclerview_uettalk.adapter?.notifyDataSetChanged()

        bottomSheetView.comment_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v?.getChildAt(0)?.measuredHeight?.minus(v!!?.measuredHeight) ?: Int)
                {
                    page_comment++
                    bottomSheetView.comment_progressbar.visibility= View.VISIBLE
                    presenter_uettalk_comment.getDetailComment(QuestionDto.id,page_comment)
                }

            }
        })

        bottomSheetView.comment_uet_camera.setOnClickListener {
            val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent,CAMERA_REQUEST)
        }

        bottomSheetView.btn_update_comment_uettalk.setOnClickListener {

            if (uri==null)
            {
                Toast.makeText(context,"uri đang trống",Toast.LENGTH_LONG).show()
            }
            Log.e("uri",uri.toString())
            xulybtncommemt(QuestionDto.id,uri)
        }

        bottomSheetDialog!!.setContentView(bottomSheetView)
        bottomSheetDialog!!.show()




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==CAMERA_REQUEST && requestCode== Activity.RESULT_OK && data!= null)
        {
            uri = data.data
            Toast.makeText(context,"Đã thêm ảnh vào bình luận!",Toast.LENGTH_LONG).show()
            Log.e("uri",uri.toString())
        }
    }

    /*------------------------Click vao itemUet chuyển sang DetailFragment-------------------------------*/
    override fun ClickItem_uettalk(QuestionDto: QuestionDtoX) {
        val bundle = Bundle()
        bundle.putInt("id_question",QuestionDto.id!!)
        Toast.makeText( context,QuestionDto.id.toString(), Toast.LENGTH_SHORT).show()
        this.findNavController().navigate(R.id.action_nav_uettalk_to_detailForumFragment,bundle)
    }

    override fun getDataView(data: QuestionDtoX) {

    }

 /*------------------------update data comment----------------------------------------*/
    override fun getDataViewComment(datacomment: ie.app.uetstudents.ui.Entity.Comment.get.Comment) {
       // bottomSheetView.comment_processbar.progress = View.GONE
        bottomSheetView.comment_progressbar.visibility = View.GONE
        if (datacomment.commentDtoList.isEmpty())
        {
            Toast.makeText(context,"trống",Toast.LENGTH_LONG).show()
            bottomSheetView.txt_comment_chuacobinhluan.text = "Chưa có bình luận nào!"
        }
        else
        {


            adapter_comment_uettalk.setData(datacomment.commentDtoList)
            Toast.makeText(context,"đã có dữ liệu",Toast.LENGTH_LONG).show()
            bottomSheetView.comment_recyclerview_uettalk.adapter?.notifyDataSetChanged()


        }
    }

    override fun getDataViewPersonsLikeQuestion(songuoilike: Int) {
        TODO("Not yet implemented")
    }

    /*--------------------Post Like comment------------------------------*/
    override fun clickOnItem(m: ie.app.uetstudents.ui.Entity.Comment.get.CommentDto) {
        val idcomment = Comment(m.id!!.toInt())
        val account = Account(null)
        val likeComment = like_comment(account,idcomment)
        val call : Call<like_comment> = ApiClient.getClient.setLikeComment(likeComment)
        call.enqueue(object : Callback<like_comment>{
            override fun onResponse(call: Call<like_comment>, response: Response<like_comment>) {
                if (response.isSuccessful)
                {
                    val notifi_item = post_notifi_comment("LIKE","",ie.app.uetstudents.ui.Entity.notifications_comment.post.Comment(m.id),"1")
                    presenter_uettalk_comment.setNotificationComment(notifi_item)
                }
                Log.e("Test_API_Like_Comment","Thành công")
            }

            override fun onFailure(call: Call<like_comment>, t: Throwable) {
                Log.e("Test_API_Like_Comment","Thất bại")
            }
        })
    }

/*--------------------------------Xử lý khi click btn đăng binh luận-----------------------------------------*/
    fun xulybtncommemt(id_question: Int,uri: Uri?)
    {
        if (bottomSheetView.edt_comment_uettalk.text.isEmpty())
        {
            Toast.makeText(context,"Bạn Chưa nhập bình luận!",Toast.LENGTH_LONG).show()
        }
        else {

            val commentpost = comment_post(
                ie.app.uetstudents.ui.Entity.Comment.post.Account(1),
                bottomSheetView.edt_comment_uettalk.text.toString(),
                Question(id_question)
            )
            val gson = Gson()
           val commentstr = gson.toJson(commentpost).toString()
            val requestbodycomment = RequestBody.create(MediaType.parse("multipart/form-data"),commentstr)

            val realpathutil = uri?.let { RealPathUtil.getRealPath(requireContext(), it) }

            val file : File = File(realpathutil)

            val requestbodyFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
            var multipartbodyfile= MultipartBody.Part.createFormData("image_files",file.name,requestbodyFile)

            val call: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment> = ApiClient.getClient.setCommentQuestion(multipartbodyfile,requestbodycomment)
            call.enqueue(object : Callback<ie.app.uetstudents.ui.Entity.Comment.get.Comment> {
                override fun onResponse(call: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment>, response: Response<ie.app.uetstudents.ui.Entity.Comment.get.Comment>) {
                    Log.e("Test", "thành công")
                    Toast.makeText(context,"bình luận thành công!",Toast.LENGTH_LONG).show()
                    if (response.isSuccessful)
                    {
                        update_notification("COMMENT",id_question,"1")
                    }
                }

                override fun onFailure(call: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment>, t: Throwable) {
                    Log.e("Test", "thất bại")
                }
            })
            bottomSheetView.edt_comment_uettalk.text.clear()


            bottomSheetView.comment_progressbar.visibility = View.VISIBLE

            val call_get: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment> =
                ApiClient.getClient.getCommentQuestion(id_question, page_comment)
            call_get.enqueue(object : Callback<ie.app.uetstudents.ui.Entity.Comment.get.Comment> {
                override fun onResponse(call: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment>, response: Response<ie.app.uetstudents.ui.Entity.Comment.get.Comment>) {
                    if (response.isSuccessful) {
                        adapter_comment_uettalk.setData(response.body()?.commentDtoList!!)
                        bottomSheetView.comment_recyclerview_uettalk?.adapter?.notifyDataSetChanged()
                        bottomSheetView.comment_progressbar.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<ie.app.uetstudents.ui.Entity.Comment.get.Comment>, t: Throwable) {
                    Log.e("Test", "lỗi rồi")
                }
            })
            bottomSheetView.recyclerview_item_uettalk?.scrollToPosition(0)
        }
    }

    /*---------------------------Update thông báo Question---------------------------------*/

    fun update_notification(type_action : String, id_question: Int,username : String)
    {
        val notifi_item = notification_question_post(type_action,"",ie.app.uetstudents.ui.Entity.notifications_question.post.Question(id_question),username)
        presenter_uettalk_comment.setNotificationQuestion(notifi_item)
    }
}