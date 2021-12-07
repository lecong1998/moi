package ie.app.uetstudents.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDto
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDtoX
import ie.app.uetstudents.ui.Entity.like_question.get.like_question
import kotlinx.android.synthetic.main.item_uettalk.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class adapter_itemuettalk(
    var ClickItem: OnClickItem_UetTalk
) : RecyclerView.Adapter<adapter_itemuettalk.ViewHolder>()  {

    private var dataList: List<QuestionDtoX> = ArrayList<QuestionDtoX>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_uettalk, parent, false))
    }

    fun setData(list: List<QuestionDtoX>){
        this.dataList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataList.get(position)
        holder.bindData(dataModel)
        holder.itemView.like_itemuettlk.setOnClickListener {
            ClickItem.ClickItem_like(dataModel)
            it.like_itemuettlk.setTextColor(
                R.color.purple_500
            )
            it.like_itemuettlk.text = "Đã thích"
            it.like_itemuettlk.setTypeface(null, Typeface.BOLD)
        }
        holder.itemView.comment_itemuettlk.setOnClickListener {
            ClickItem.ClickItem_comment(dataModel)
        }
        holder.itemView.setOnClickListener {
            ClickItem.ClickItem_uettalk(dataModel)
        }
       // callLayluotlikequestion(dataModel,holder)
       // calllaysolanbinhluanquestion(dataModel,holder)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(d: QuestionDtoX) {
            itemView.txt_status_itemuettalk.text = d.content
           // Glide.with(itemView.context).load(d.image).into(itemView.image_recyclerview_itemuettalk)
            val thoigian :String = d.time?.substring(11,16).toString()
            val ngay : String = d.time?.substring(0,10).toString()
            itemView.time_uetttalk_item.setText(thoigian)
            itemView.date_uettalk_item.setText(ngay)
        }
    }

    fun callLayluotlikequestion(d: QuestionDto, holder: ViewHolder)
    {
        val call : Call<like_question> = ApiClient.getClient.getPersonLikeQuestion(d.id!!,1)
        call.enqueue(object : Callback<like_question>{
            override fun onResponse(call: Call<like_question>, response: Response<like_question>) {
                if (response.isSuccessful)
                {
                    if (response.body()!!.result_quantity!= 0)
                    {
                        holder.itemView.numberlike.text = "${response.body()!!.result_quantity} người thích bài viết!"
                    }
                }
            }

            override fun onFailure(call: Call<like_question>, t: Throwable) {
                Log.e("Test","Lối")
            }
        })

    }
    fun calllaysolanbinhluanquestion(d : QuestionDto, holder: ViewHolder)
    {
        /*val call : Call<comment> = ApiClient.getClient.getCommentQuestion(d.id!!,1)
        call.enqueue(object : Callback<comment>{
            override fun onResponse(call: Call<comment>, response: Response<comment>) {
                if (response.isSuccessful)
                {
                    if (response.body()!!.result_quantity !=0)
                    {
                        holder.itemView.number_comment.text = "Có ${response.body()!!.result_quantity} bình luận!"
                    }
                }
            }

            override fun onFailure(call: Call<comment>, t: Throwable) {
                Log.e("Test","Lỗi")
            }
        })*/
    }

}

interface OnClickItem_UetTalk{
    fun ClickItem_like(QuestionDto : QuestionDtoX)
    fun ClickItem_comment(QuestionDto : QuestionDtoX)
    fun ClickItem_uettalk(QuestionDto : QuestionDtoX)
}
