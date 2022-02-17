package fragments

import adapters.ProductsAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import callbacks.ProductSelectedListener
import com.demo.eliproject.R
import viewmodel.MainSharedViewModel

class ProductFragment: Fragment() {
    
    private lateinit var model: MainSharedViewModel
    private lateinit var listener: ProductSelectedListener
    
    private lateinit var titleTv: TextView
    private lateinit var subTitleTv: TextView
    private lateinit var rv: RecyclerView
    private lateinit var placeOrder: Button
    private lateinit var progressBarHolder: FrameLayout
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(activity is ProductSelectedListener) {
            listener = activity as ProductSelectedListener
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(requireActivity()).get(MainSharedViewModel::class.java)
    }
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.product_fragment_layout, container, false)
        view.apply {
            titleTv = findViewById(R.id.top_title)
            subTitleTv = findViewById(R.id.sub_title)
            placeOrder = findViewById(R.id.place_order)
            progressBarHolder = findViewById(R.id.progressBarHolder)
            rv = findViewById(R.id.product_list)
            rv.layoutManager = GridLayoutManager(activity, 2)
        }
        initObservers()
        model.getStoreInfo()
        return view
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        placeOrder.setOnClickListener {
            if(model.getSelectedProducts().size > 0) {
                model.setProductData()
                Navigation.findNavController(it).navigate(R.id.action_productFragment_to_orderConfirmarion2)
            } else {
                Toast.makeText(activity, "Please select product", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    fun initObservers() {
        model.storeInfoLD.observe(viewLifecycleOwner, {store->
            store?.let {
                titleTv.text = store.storeName
                subTitleTv.text = store.storeInfo
                progressBarHolder.visibility = View.VISIBLE
                model.getProducts()
            }
        })
        
        model.productListLD.observe(viewLifecycleOwner, {products->
            products?.let {
                progressBarHolder.visibility = View.GONE
                val adapter = ProductsAdapter(it, listener)
                rv.adapter = adapter
            }
            
        })
    }
    
    companion object {
        fun newInstance(): ProductFragment {
            val fragment = ProductFragment()
            return fragment
        }
    }
}