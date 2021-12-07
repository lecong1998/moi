package ie.app.uetstudents.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ie.app.uetstudents.R
import ie.app.uetstudents.adapter.adapter_document
import ie.app.uetstudents.adapter.document
import ie.app.uetstudents.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: adapter_document? = null
    private var listdocumnet = ArrayList<document>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        val root: View = binding.root

        // val textView: TextView = binding.textHome
        /*  homeViewModel.text.observe(viewLifecycleOwner, Observer {
              textView.text = it
          })*/
        listdocumnet.add(document(0, R.drawable.unnamed, "UET logo"))
        listdocumnet.add(document(1, R.drawable.logo__1_, "UET logo ảnh trường"))
      //  adapter = adapter_document(listdocumnet)
       /* root.home_recyclerview_document.layoutManager = LinearLayoutManager(context)
        root.home_recyclerview_document.adapter = adapter
        root.home_viewflipper.isAutoStart= true
        root.home_viewflipper.flipInterval = 3000
        root.home_viewflipper.startFlipping()*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


  /*  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        when(item.itemId)
        {
            R.id.action_search -> {
                val intent = Intent(activity,SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.action_profile -> {

            }
        }
    }*/
}