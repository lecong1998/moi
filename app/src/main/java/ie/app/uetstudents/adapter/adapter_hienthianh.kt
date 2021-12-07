package ie.app.uetstudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.squareup.picasso.Picasso
import ie.app.uetstudents.Glideapp.GlideApp
import ie.app.uetstudents.R
import kotlinx.android.synthetic.main.anh_detail.view.*

class adapter_hienthianh(var listanh : ArrayList<String>) : RecyclerView.Adapter<adapter_hienthianh.Viewholder>() {
    class Viewholder (var itemview : View) : RecyclerView.ViewHolder(itemview){
        fun onBindata(link : String)
        {
            //GlideApp.with()
                Glide.with(itemview.context).load(link).into(itemview.item_anh_detail)
          // Picasso.get().load(link).into(itemview.item_anh_detail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            LayoutInflater.from(parent.context).inflate(R.layout.anh_detail,parent,false)
        )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        return holder.onBindata(listanh[position])
    }

    override fun getItemCount(): Int {
        return listanh.size
    }
}