package ie.app.uetstudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.Entity.subject.DataSubject.ExamDocumentDto
import kotlinx.android.synthetic.main.itemdatasubject.view.*

class adapter_datasubject(var listdata : List<ExamDocumentDto> , var CLickItem : OnCLickItem_DataSubject)
    : RecyclerView.Adapter<adapter_datasubject.ViewHolder>(){
   inner class ViewHolder(var itemview : View) : RecyclerView.ViewHolder(itemview) {
        fun OnBindata(ExamDocument: ExamDocumentDto)
        {
            itemview.datasubject_name.text = ExamDocument.name.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.itemdatasubject,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listdata[position]
        holder.OnBindata(data)
        holder.itemview.setOnClickListener {
            CLickItem.ClickItem(data)
        }
    }

    override fun getItemCount(): Int {
        return listdata.size
    }
}
interface OnCLickItem_DataSubject{
    fun ClickItem(ExamDocument : ExamDocumentDto)
}