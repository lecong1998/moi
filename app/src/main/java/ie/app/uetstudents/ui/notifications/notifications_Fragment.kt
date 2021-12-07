package ie.app.uetstudents.ui.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import ie.app.uetstudents.R
import ie.app.uetstudents.Repository.Repository
import ie.app.uetstudents.adapter.OnClickItem_Notification
import ie.app.uetstudents.adapter.adapter_notification
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.Question.get.QuestionX
import ie.app.uetstudents.ui.Entity.notifications_comment.get.NotificationCommentDto
import ie.app.uetstudents.ui.Entity.notifications_comment.get.get_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.get.NotificationQuestionDto
import ie.app.uetstudents.ui.Entity.notifications_question.get.notification_question
import ie.app.uetstudents.ui.Entity.notifications_question.notification_item
import kotlinx.android.synthetic.main.activity_notifications.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class notifications_Fragment : Fragment() , OnClickItem_Notification,notification_Contract.View{


    var list_notification_Question : List<NotificationQuestionDto> = ArrayList()
    var list_notification_Comment : List<NotificationCommentDto> = ArrayList()

    private lateinit var adapterNotification: adapter_notification
    private lateinit var presenter: notification_Contract.Presenter
    private var page : Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_notifications,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = notificationPresenter(this, Repository(requireContext()))
        presenter.getNotificationComment(1,page)
        presenter.getNotificationQuestion(1,page)

        adapterNotification = adapter_notification(this)
        notification_recyclerview.layoutManager = LinearLayoutManager(context)
        notification_recyclerview.adapter = adapterNotification
        notification_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v?.getChildAt(0)?.measuredHeight?.minus(v?.measuredHeight))
                {
                    progress_bar_notifi.visibility = View.VISIBLE
                    page++
                    presenter.getNotificationQuestion(1,page)
                    presenter.getNotificationComment(1,page)
                    progress_bar_notifi.visibility = View.GONE
                }
            }
        })


    }



    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item1 : MenuItem = menu.findItem(R.id.action_search)
        val item2 : MenuItem = menu.findItem(R.id.action_notification)
        val item3 : MenuItem = menu.findItem(R.id.action_profile)
        item1.setVisible(false)
        item2.setVisible(false)
        item3.setVisible(false)
    }

    override fun OnCLick(n: notification_item) {
        if (n.notification_item_id>1000)
        {
            val bundle = Bundle()
            bundle.putInt("id_question",n.notification_item_id.minus(1000))
            this.findNavController().navigate(R.id.action_action_notification_to_detailForumFragment,bundle)
        }
        if (n.notification_item_id<1000)
        {
            val call : Call<QuestionX> = ApiClient.getClient.getQuestion_of_comment(n.notification_item_id)
            call.enqueue(object : Callback<QuestionX>{
                override fun onResponse(call: Call<QuestionX>, response: Response<QuestionX>) {
                    if (response.isSuccessful)
                    {
                        val id_question = response.body()!!.questionDtoList[0].id
                        val bundle = Bundle()
                        bundle.putInt("id_question",id_question!!)
                        this@notifications_Fragment.findNavController().navigate(R.id.action_action_notification_to_detailForumFragment,bundle)
                        Log.e("Test_tucommentdenQuestion","Thành công")
                    }
                }

                override fun onFailure(call: Call<QuestionX>, t: Throwable) {
                   Log.e("Test_tucommentdenQuestion","Thất bại")
                }
            })
        }
    }

    override fun updateViewNotification_question(notification_question: notification_question) {
        adapterNotification.setData_question(notification_question.notificationQuestionDtoList)
        notification_recyclerview.adapter?.notifyDataSetChanged()
    }

    override fun updateViewNotification_comment(notification_comment: get_notifi_comment) {
        adapterNotification.setdata_comment(notification_comment.notificationCommentDtoList)
        notification_recyclerview.adapter?.notifyDataSetChanged()
    }


}

