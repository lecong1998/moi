package ie.app.uetstudents.ui.notifications

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import ie.app.uetstudents.MainActivity
import ie.app.uetstudents.R
import ie.app.uetstudents.Repository.Repository
import ie.app.uetstudents.adapter.OnClickItem_Notification
import ie.app.uetstudents.adapter.adapter_notification
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.notifications_comment.get.NotificationCommentDto
import ie.app.uetstudents.ui.Entity.notifications_comment.get.get_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.get.NotificationQuestionDto
import ie.app.uetstudents.ui.Entity.notifications_question.get.notification_question
import ie.app.uetstudents.ui.Entity.notifications_question.notification_item
import ie.app.uetstudents.ui.diendan.detailForum.DetailForumFragment
import kotlinx.android.synthetic.main.activity_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class notification_service : Service()  {

    private var page :Int = 1

    var list_notification_Question : List<NotificationQuestionDto> = ArrayList()
    var list_notification_Comment : List<NotificationCommentDto> = ArrayList()


    override fun onCreate() {
        super.onCreate()

        Log.e("service","Đã tạo")

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {



        Log.e("service_start","Đã tạo")



                val call : Call<get_notifi_comment> = ApiClient.getClient.getNotification_comment_account(1,page)
                call.enqueue(object : Callback<get_notifi_comment>{
                    override fun onResponse(
                        call: Call<get_notifi_comment>,
                        response: Response<get_notifi_comment>
                    ) {
                        if (response.isSuccessful)
                        {
                            var list1 : List<NotificationCommentDto> = ArrayList()
                            if (list_notification_Comment!= null)
                            {
                                list1 = list_notification_Comment
                            }
                            var list2 : ArrayList<NotificationCommentDto> = ArrayList()
                            list_notification_Comment = response.body()!!.notificationCommentDtoList
                            if (list1 != list_notification_Comment)
                            {
                                for (i in 0..list_notification_Comment.size.minus(1))
                                {
                                    if (list_notification_Comment[i]!=list1.get(0))
                                    {
                                        list2.add(list_notification_Comment[0])
                                    }else
                                    {
                                        return
                                    }
                                }
                            }



                            // val broadcastIntent = Intent()
                            // broadcastIntent.setAction(MainActivity().mBroadcastAction)

                            //val gson : Gson = Gson()
                            // val jsonArray : JsonArray = gson.toJsonTree(list_notification_Comment).asJsonArray
                            // val jsonString : String = jsonArray.toString()
                            // broadcastIntent.putExtra("broadcast",jsonString)
                            // Log.e("thureceiver",list_notification_Comment.size.toString())
                            // sendBroadcast(broadcastIntent)
                            Log.e("RESTAPI","Thành CÔng")
                            list2.forEach {
                                sentNotification(it)
                            }

                        }
                    }

                    override fun onFailure(call: Call<get_notifi_comment>, t: Throwable) {
                        Log.e("RESTAPI","Thất bại")
                    }
                })

        return START_REDELIVER_INTENT
    }

    fun sentNotification(notifi : NotificationCommentDto)
    {
        val intent = Intent(this,MainActivity::class.java)
        val bundle = Bundle()
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val notification : Notification = NotificationCompat.Builder(this,my_notification().CHANNEL_ID)
            .setContentTitle("Notification UET Students")
            .setContentText("${notifi.username} Đã thích bình luận của bạn")
            .setSmallIcon(R.drawable.unnamed)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1,notification)

    }

    override fun onDestroy() {
        super.onDestroy()
    }



}