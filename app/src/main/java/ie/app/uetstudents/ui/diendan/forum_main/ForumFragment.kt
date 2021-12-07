package ie.app.uetstudents.ui.diendan.forum_main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.app.uetstudents.R
import ie.app.uetstudents.Repository.Repository

import ie.app.uetstudents.adapter.ClickItem
import ie.app.uetstudents.adapter.ClickItemCategory
import ie.app.uetstudents.adapter.adapter_chude
import ie.app.uetstudents.adapter.adapter_forum
import ie.app.uetstudents.databinding.FragmentForumBinding
import ie.app.uetstudents.ui.Entity.Category.CategoryDto
import ie.app.uetstudents.ui.Entity.Category.category
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDto
import ie.app.uetstudents.ui.Entity.Question.get.QuestionDtoX
import ie.app.uetstudents.ui.Entity.Question.get.QuestionX
import ie.app.uetstudents.ui.Entity.Question.get.question
import ie.app.uetstudents.ui.diendan.category.CategoryContract
import ie.app.uetstudents.ui.diendan.category.CategoryPresenter
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_forum.*
import kotlinx.android.synthetic.main.fragment_forum.view.*
import kotlinx.android.synthetic.main.fragment_uettalk.view.*
import kotlinx.android.synthetic.main.layout_bottomsheet.*
import kotlinx.android.synthetic.main.layout_bottomsheet.view.*

class ForumFragment: Fragment(),ClickItem , forumContract.View,ClickItemCategory,CategoryContract.View{
    private lateinit var forumViewModel: ForumViewModel
    private var _binding: FragmentForumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var presenter: forumContract.Presenter
    private lateinit var adapter: adapter_forum

    private lateinit var presenterCategory: CategoryContract.Presenter
    private lateinit var adapterCategory: adapter_chude


    private var bottomSheetDialog : BottomSheetDialog? = null
    private lateinit var bottomSheetView : View

    private var page_forum : Int = 1

    var id_user : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            id_user = it?.getInt("id_user")
        }
        Toast.makeText(context,id_user.toString(),Toast.LENGTH_LONG).show()
        Log.e("id_user", id_user.toString())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forumViewModel =
            ViewModelProvider(this).get(ForumViewModel::class.java)

        _binding = FragmentForumBinding.inflate(inflater, container, false)
        val root: View = binding.root


       adapter = adapter_forum(this)
       presenter = forumPresenter(this, Repository(requireContext()))

       presenter.getQuestions(1,page_forum)
       root.forum_recycelview?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
       root.forum_recycelview.adapter = adapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_forum_write.setOnClickListener(
            View.OnClickListener {
                it.findNavController().navigate(R.id.forumFragment_to_writeFragment)
            }
        )

        btn_forum_selection.setOnClickListener {


             bottomSheetDialog = BottomSheetDialog(
                this@ForumFragment.requireContext(),R.style.BottomSheetDialogTheme
            )
            bottomSheetView = LayoutInflater.from(requireContext()).inflate(R.layout.layout_bottomsheet,bottomsheet )
            adapterCategory = adapter_chude(this)
            adapterCategory.notifyDataSetChanged()
            presenterCategory = CategoryPresenter(this, Repository(requireContext()))
            presenterCategory.getCategorys()
           // datalist = ArrayList<CategoryDto>()
            bottomSheetView.bottomsheetrecyclerview.layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
           // datalist?.let { it1 -> adapterCategory.setData(it1) }
            bottomSheetView.bottomsheetrecyclerview?.adapter= adapterCategory
            bottomSheetDialog!!.setContentView(bottomSheetView)
            bottomSheetDialog!!.show()

        }

        view.forum_scrollview.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v?.getChildAt(0)?.measuredHeight?.minus(v?.measuredHeight))
                {
                    page_forum++;
                    forum_progressbar.visibility = View.VISIBLE
                    presenter.getQuestions(1,page_forum)
                    //presenterCategory.getCategorys()
                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun clickOnItem(m: QuestionDtoX) {
        val bundle = Bundle()
        bundle.putInt("id_question",m.id!!)
        Toast.makeText( context,m.id.toString(), Toast.LENGTH_SHORT).show()
        this.findNavController().navigate(R.id.action_nav_forum_to_detailForumFragment,bundle)

    }

    override fun updateViewData(data : question) {

        forum_progressbar.visibility = View.GONE
        adapter.setData(data.questionDtoList)

        forum_recycelview.adapter?.notifyDataSetChanged()
    }

    override fun clickOnItemCategory(m: CategoryDto) {
        Toast.makeText(context,m.category,Toast.LENGTH_SHORT).show()
        bottomSheetDialog?.dismiss()

        adapter = adapter_forum(this)
        presenter = forumPresenter(this, Repository(requireContext()))
        if(m.category != "Tất cả") {
            presenter.getQuestions_of_Category(m.id,page_forum)

        } else
        {
            presenter.getQuestions(1,page_forum)
        }

        forum_recycelview.adapter?.notifyDataSetChanged()
        forum_recycelview.layoutManager = LinearLayoutManager(context)
        forum_recycelview?.adapter = adapter

    }

    override fun updateViewData(data: category) {
        if (data.categoryDtoList.isEmpty())
        {

        }
        else
        {
            Toast.makeText(context,"có rồi",Toast.LENGTH_LONG).show()
               adapterCategory.setData(data.categoryDtoList)
            bottomSheetView.chude_forum_progressbar.visibility = View.GONE
            bottomSheetView.bottomsheetrecyclerview?.adapter?.notifyDataSetChanged()

        }

    }


}