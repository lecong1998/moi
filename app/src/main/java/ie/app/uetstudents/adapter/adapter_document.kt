package ie.app.uetstudents.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.app.uetstudents.R
import ie.app.uetstudents.ui.Entity.subject.SubjectDto
import kotlinx.android.synthetic.main.item_document.view.*
import java.util.zip.Inflater

class adapter_document(var listdocument : List<SubjectDto>, var ClickItem : OnClickItem_Subject) : RecyclerView.Adapter<adapter_document.ViewHoldel>() {
    inner class ViewHoldel(var itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun onBinData(subjectDto: SubjectDto)
        {
            itemview.item_document_name.text = subjectDto.subject_name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldel {
        return ViewHoldel(
            LayoutInflater.from(parent.context).inflate(R.layout.item_document,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHoldel, position: Int) {
        val itemSubject = listdocument.get(position)
        holder.onBinData(itemSubject)
        holder.itemview.setOnClickListener {
            ClickItem.OnClick(itemSubject)
        }
    }

    override fun getItemCount(): Int {
        return listdocument.size
    }
}
interface OnClickItem_Subject{
    fun OnClick(subjectDto: SubjectDto)
}