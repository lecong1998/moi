package ie.app.uetstudents.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.Comment.get.CommentDto
import ie.app.uetstudents.ui.Entity.like.Get.like_comment_get
import ie.app.uetstudents.ui.Entity.like.Post.like_comment
import kotlinx.android.synthetic.main.itemcoment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class adapter_comment(
    var clickItem: ClickItemCommentLike
) : RecyclerView.Adapter<adapter_comment.ViewHolder>()  {

    private var dataList: List<CommentDto> = ArrayList<CommentDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.itemcoment, parent, false))
    }

    fun setData(list: List<CommentDto>){
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

        CallApilayluotthichcomment(dataModel,holder)

        var solanthich = 0

        holder.itemView.like_comment.setOnClickListener {
            clickItem.clickOnItem(dataModel)

            solanthich= solanthich+1
            if (solanthich % 2 == 1)
            {
                it.like_comment.setTextColor(
                    R.color.purple_500
                )
                it.like_comment.setTypeface(null,Typeface.BOLD)
            }
            else
            {
                it.like_comment.setTextColor(
                    R.color.black
                )
                it.like_comment.setTypeface(null,Typeface.NORMAL)
            }
            CallApilayluotthichcomment(dataModel,holder)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(d: CommentDto) {
            itemView.name_comment_account.text = "Lê Công"
            Glide.with(itemView.context).load(d.image).error(R.drawable._60279747_1127526494354946_6683273208343303265_n).into(itemView.anh_comment)
            itemView.content_comment.text = d.content

            val time : String= d.time.substring(11,16)+ d.time.substring(0,10)
            itemView.time_comment.text= time
        }
    }

    fun CallApilayluotthichcomment(m: CommentDto,holder: ViewHolder)
    {

        val call : Call<like_comment_get> = ApiClient.getClient.getPersonLikeComment(m.id!!,1)
        call.enqueue(object : Callback<like_comment_get>{
            override fun onResponse(
                call: Call<like_comment_get>,
                response: Response<like_comment_get>
            ) {
                if (response.isSuccessful)
                {
                    if (response.body()!!.result_quantity != 0)
                    {
                         val i  = response.body()!!.result_quantity
                        holder.itemView.soluotlikecomment.text = "Có ${response.body()!!.result_quantity} đã thích bình luận này!"

                    }
                }
            }

            override fun onFailure(call: Call<like_comment_get>, t: Throwable) {
                Log.e("Test","thất bại")
            }
        })

    }


}

interface ClickItemCommentLike{
    fun clickOnItem(m : CommentDto)
}
