package ie.app.uetstudents.ui.tailieu.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import ie.app.uetstudents.R
import ie.app.uetstudents.adapter.*
import ie.app.uetstudents.ui.API.ApiClient
import ie.app.uetstudents.ui.Entity.subject.DataSubject.ExamDocumentDto
import ie.app.uetstudents.ui.Entity.subject.DataSubject.data_subject
import ie.app.uetstudents.ui.Entity.subject.SubjectDto
import ie.app.uetstudents.ui.Entity.subject.subject
import ie.app.uetstudents.ui.tailieu.DetailDocumentExamActivity
import kotlinx.android.synthetic.main.dialog_listtailieudethi.view.*
import kotlinx.android.synthetic.main.fragment_c_n_t_t.*
import kotlinx.android.synthetic.main.fragment_c_n_t_t2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CNTTFragment : Fragment() , OnClickItem_Subject,OnCLickItem_DataSubject{

    private var id_category : Int? = null

    private var page : Int = 1
    private var page_data : Int = 1
    private lateinit var dialog : AlertDialog
    private lateinit var viewdialog : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id_category = it.getInt("id_category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_c_n_t_t, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callapi_id_category(id_category!!,page!!)

        tailieu_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY== v?.getChildAt(0)?.measuredHeight?.minus(v?.measuredHeight))
                {
                    page++
                    tailieu_progressbar.visibility = View.GONE
                    callapi_id_category(id_category!!,page)

                }
            }
        })


    }

    companion object {
        @JvmStatic
        fun newInstance(id_category : Int) =
            CNTTFragment().apply {
                arguments = Bundle().apply {
                    putInt("id_category", id_category)
                }
            }
    }

    fun callapi_id_category( id_category: Int, page : Int)
    {
        val call : Call<subject> = ApiClient.getClient.getSubject_category(id_category,page)
        call.enqueue(object : Callback<subject>{
            override fun onResponse(call: Call<subject>, response: Response<subject>) {
                if (response.isSuccessful)
                {
                    if (response.body()!! != null)
                    {
                        val adapterDocument = adapter_document(response.body()!!.subjectDtoList,this@CNTTFragment)
                        list_subjects_cntt.layoutManager = LinearLayoutManager(requireContext())
                        list_subjects_cntt.adapter = adapterDocument
                        tailieu_progressbar.visibility = View.GONE
                        Log.e("Test_phần_tailieu","Lỗi")
                    }

                }
            }

            override fun onFailure(call: Call<subject>, t: Throwable) {
                Log.e("Test","Lỗi")
            }
        })
    }

    override fun OnClick(subjectDto: SubjectDto) {
        Toast.makeText(context,subjectDto.subject_name,Toast.LENGTH_SHORT).show()

        val DialogBuilder : AlertDialog.Builder = AlertDialog.Builder(requireContext(),R.style.DialogFullScreen)

         viewdialog = LayoutInflater.from(context).inflate(R.layout.dialog_listtailieudethi,null)

        DialogBuilder.setView(viewdialog)
        dialog = DialogBuilder.create()
        dialog.show()

        callapisubjectdata(subjectDto.id,"DOCUMENT",page_data)
        viewdialog.list_document_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY== v?.getChildAt(0)?.measuredHeight?.minus(v?.measuredHeight))
                {
                    page_data++
                    callapisubjectdata(subjectDto.id,"DOCUMENT",page_data)
                }
            }
        })

    }

    override fun ClickItem(ExamDocument: ExamDocumentDto) {
        val intent = Intent(activity,DetailDocumentExamActivity::class.java)
        intent.putExtra("ExamDocument", ExamDocument.link)
        startActivity(intent)
    }

    fun callapisubjectdata(id_subject : Int, type : String, page : Int)
    {
        val call : Call<data_subject> = ApiClient.getClient.getDataSubjectforid(id_subject,type,page)
        call.enqueue(object : Callback<data_subject>{
            override fun onResponse(call: Call<data_subject>, response: Response<data_subject>) {
                if (response.isSuccessful)
                {
                    if (response.body()!! != null)
                    {
                        val adapter = adapter_datasubject(response.body()!!.examDocumentDtoList,this@CNTTFragment)
                        viewdialog.list_document.layoutManager = LinearLayoutManager(context)
                        viewdialog.list_document.adapter = adapter
                        Log.e("Test_lây_list_tailieu","Thành công")
                    }
                }
            }

            override fun onFailure(call: Call<data_subject>, t: Throwable) {
                Log.e("test","Lỗi")
            }
        })
    }
}