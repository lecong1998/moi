package ie.app.uetstudents.ui.timkiem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.Entity.Search.Question.QuestionDto
import kotlinx.android.synthetic.main.item_search.view.*

class adapter_search ( var ClickItem : OnCLickItem_search)
    :RecyclerView.Adapter<adapter_search.Viewholder>(){

    private var listsearch : ArrayList<QuestionDto>? = ArrayList()

    fun setDataQuestion(list : List<QuestionDto>)

    {
        listsearch = list as ArrayList<QuestionDto>
        notifyDataSetChanged()
    }

    inner class Viewholder(var itemview : View) : RecyclerView.ViewHolder(itemview) {
        fun onBinData(Questiontdo: QuestionDto)
        {
            Glide.with(itemView.context).load(Questiontdo.image).into(itemview.item_search_image)
            itemview.item_search_content.text = Questiontdo.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
       return Viewholder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_search,parent,false)
       )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val question : QuestionDto = listsearch?.get(position)!!
        holder.onBinData(question)
        holder.itemview.setOnClickListener {
            ClickItem.Onclick(question)
        }
    }

    override fun getItemCount(): Int {
        return listsearch!!.size
    }
}
interface OnCLickItem_search{
    fun Onclick(Questiontdo : QuestionDto)
}