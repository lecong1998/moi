package ie.app.uetstudents.ui.bantin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ie.app.uetstudents.R
import ie.app.uetstudents.adapter.adapter_itembantin
import ie.app.uetstudents.adapter.itembantin
import ie.app.uetstudents.databinding.FragmentHomeBinding
import ie.app.uetstudents.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.fragment_news.view.*


class NewsFragment : Fragment() {
    private lateinit var newsViewModel: NewsViewModel
    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var adapter : adapter_itembantin? =null
    private var listbantin = ArrayList<itembantin>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsViewModel =
            ViewModelProvider(this).get(NewsViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        root.item_news_recyclerview.layoutManager = LinearLayoutManager(requireContext())
        listbantin.add(itembantin(0, R.drawable.hinh_nen_trang_10_263x263,"Bản tin 1","10h30 01/11/2021"))
        listbantin.add(itembantin(1, R.drawable.vnu_the_subjects_2021_2_263x263,"Bản tin 2","10h30 01/11/2021"))
        listbantin.add(itembantin(2, R.drawable.cv_263x263,"Bản tin 3","10h30 01/11/2021"))
        listbantin.add(itembantin(3, R.drawable.banner_rev_2021_1_263x263,"Bản tin 4","10h30 01/11/2021"))
        adapter = adapter_itembantin(listbantin)
        root.item_news_recyclerview.adapter = adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}