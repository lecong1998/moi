package ie.app.uetstudents.ui.tailieu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ie.app.uetstudents.adapter.adapter_pageView
import ie.app.uetstudents.databinding.FragmentDocumentsBinding
import ie.app.uetstudents.ui.tailieu.fragment.CNTTFragment
import kotlinx.android.synthetic.main.fragment_documents.*
import kotlinx.android.synthetic.main.fragment_documents.view.*

class DocumentFragment: Fragment() {

    private lateinit var documentViewModel: DocumentViewModel
    private var _binding: FragmentDocumentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        documentViewModel =
            ViewModelProvider(this).get(DocumentViewModel::class.java)

        _binding = FragmentDocumentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapterPageview = activity?.supportFragmentManager?.let { adapter_pageView(it) }
        adapterPageview?.addFragment(CNTTFragment.newInstance(1),"CNTT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(2),"KHMT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(3), "HTTT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(4), "CNKTDTTT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(5), "VLKT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(6), "KTNL")
        adapterPageview?.addFragment(CNTTFragment.newInstance(7), "Cơ-KT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(8), "CN-KT-Cơ-DT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(9), "MMT-TT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(10), "KTMT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(11), "CN-KTXD-GT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(12), "CNHKVT")
        adapterPageview?.addFragment(CNTTFragment.newInstance(13), "KTRB")
        adapterPageview?.addFragment(CNTTFragment.newInstance(14), "CNNN")
        adapterPageview?.addFragment(CNTTFragment.newInstance(15), "KTDK-TDH")

        root.viewpager?.adapter = adapterPageview
        root.tablayout?.setupWithViewPager(root.viewpager)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}