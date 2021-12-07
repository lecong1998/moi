package ie.app.uetstudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.Entity.Category.CategoryDto
import kotlinx.android.synthetic.main.item_chude.view.*

class adapter_chude(
    var clickItem: ClickItemCategory
) : RecyclerView.Adapter<adapter_chude.ViewHolder>()  {

    private var dataList: List<CategoryDto> = ArrayList<CategoryDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_chude, parent, false))
    }

    fun setData(list: List<CategoryDto>){
        this.dataList = list
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = dataList.get(position)
        holder.bindData(dataModel)
        holder.itemView.setOnClickListener(){
            clickItem.clickOnItemCategory(dataModel)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(d: CategoryDto) {
            itemView.txt_chude.text = d.category

        }
    }
}

interface ClickItemCategory{
    fun clickOnItemCategory(m: CategoryDto)
}