package ie.app.uetstudents.ui.dethi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ie.app.uetstudents.adapter.adapter_pageView
import ie.app.uetstudents.databinding.FragmentExamBinding
import ie.app.uetstudents.ui.dethi.fragment.dethi_CNTTFragment
import kotlinx.android.synthetic.main.fragment_documents.view.*
import kotlinx.android.synthetic.main.fragment_exam.view.*

class ExamFragment: Fragment() {
    private lateinit var examViewModel: ExamViewModel
    private var _binding: FragmentExamBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        examViewModel =
            ViewModelProvider(this).get(ExamViewModel::class.java)

        _binding = FragmentExamBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapterPageview = activity?.supportFragmentManager?.let { adapter_pageView(it) }
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(1),"CNTT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(2),"KHMT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(3), "HTTT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(4), "CNKTDTTT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(5), "VLKT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(6), "KTNL")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(7), "Cơ-KT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(8), "CN-KT-Cơ-DT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(9), "MMT-TT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(10), "KTMT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(11), "CN-KTXD-GT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(12), "CNHKVT")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(13), "KTRB")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(14), "CNNN")
        adapterPageview?.addFragment(dethi_CNTTFragment.newInstance(15), "KTDK-TDH")

        root.viewpager_dethi?.adapter = adapterPageview
        root.tablayout_dethi?.setupWithViewPager(root.viewpager_dethi)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}