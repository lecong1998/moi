package ie.app.uetstudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.app.uetstudents.R
import kotlinx.android.synthetic.main.item_news.view.*

class adapter_itembantin(var listitem: ArrayList<itembantin>): RecyclerView.Adapter<adapter_itembantin.viewholder>() {
    inner class viewholder(var itemview : View): RecyclerView.ViewHolder(itemview) {
        fun onBindata(itembantin: itembantin)
        {
            itemview.item_news_image.setImageResource(R.drawable.hinh_nen_trang_10_263x263)
            itemview.item_news_txtname.text = itembantin.name
            itemview.item_news_txttime.text= itembantin.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        )
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        return holder.onBindata(listitem.get(position))
    }

    override fun getItemCount(): Int {
        return listitem.size
    }
}