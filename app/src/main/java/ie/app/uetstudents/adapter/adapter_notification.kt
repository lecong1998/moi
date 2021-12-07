package ie.app.uetstudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.Entity.notifications_comment.get.NotificationCommentDto
import ie.app.uetstudents.ui.Entity.notifications_comment.get.get_notifi_comment
import ie.app.uetstudents.ui.Entity.notifications_question.get.NotificationQuestionDto
import ie.app.uetstudents.ui.Entity.notifications_question.notification_item
import kotlinx.android.synthetic.main.item_notification.view.*

class adapter_notification( var ClickItem : OnClickItem_Notification)
    : RecyclerView.Adapter<adapter_notification.ViewHolder>(){

    private var listnotifi_item : ArrayList<notification_item>? = ArrayList()
    fun setData_question(list_notifi_question : List<NotificationQuestionDto>)
    {
        list_notifi_question.forEach {
            val notifi_item = notification_item(it.action_type,it.avatar,it.id,it.notification_item_id+1000,it.seen,it.username)
            listnotifi_item?.add(notifi_item)
        }
        notifyDataSetChanged()
    }
    fun setdata_comment(list_notifi_comment : List<NotificationCommentDto>)
    {
        list_notifi_comment.forEach {
            val notifi_item = notification_item(it.action_type,it.avatar,it.id,it.notification_item_id,it.seen,it.username)
            listnotifi_item?.add(notifi_item)
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(var itemview : View) : RecyclerView.ViewHolder(itemview) {
        fun OnBinData(n : notification_item)
        {
            Glide.with(itemView.context).load(n.avatar).error(R.drawable._60279747_1127526494354946_6683273208343303265_n).into(itemview.item_notification_image)
            if (n.action_type == "LIKE")
            {
                if(n.notification_item_id>=1000)
                {
                    itemview.item_notification_txtcontent.text = "${n.username} đã Thich Bài Viết của bạn!"
                }
                else
                {
                    itemview.item_notification_txtcontent.text = "${n.username} đã Thich Bình luận Của bạn!"
                }

            }
            if (n.action_type == "COMMENT")
            {
                itemview.item_notification_txtcontent.text = "${n.username} đã Bình luận Bài Viết của bạn!"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datamodel = listnotifi_item?.get(position)
        holder.OnBinData(datamodel!!)
        holder.itemview.setOnClickListener {
            ClickItem.OnCLick(datamodel!!)
        }
    }

    override fun getItemCount(): Int {
        return listnotifi_item!!.size
    }
}
interface OnClickItem_Notification{
    fun OnCLick(n : notification_item)
}