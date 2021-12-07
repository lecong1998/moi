package ie.app.uetstudents.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ie.app.uetstudents.R
import kotlinx.android.synthetic.main.item_anh_write.view.*

class adapter_anhwrite (var listanh : List<Uri>, var Clickdelete : OnclickItem_deleteanh)
    : RecyclerView.Adapter<adapter_anhwrite.Viewholder>(){
    inner class Viewholder (var itemview: View) : RecyclerView.ViewHolder(itemview) {

        fun onBindata(image :Uri)
        {
            itemview.item_anh.setImageURI(image)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_anhwrite.Viewholder {
       return Viewholder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_anh_write,parent,false)
       )
    }

    override fun onBindViewHolder(holder: adapter_anhwrite.Viewholder, position: Int) {
        val anh : Uri = listanh[position]
        holder.onBindata(anh)
        holder.itemview.delete_anh.setOnClickListener {
            Clickdelete.CLickDelete(anh)
        }
    }

    override fun getItemCount(): Int {
        return listanh.size
    }
}

interface OnclickItem_deleteanh{
    fun CLickDelete(anh : Uri)
}