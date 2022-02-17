package fragments

import adapters.ConfirmProductsAdapter
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import callbacks.ProductSelectedListener
import com.demo.eliproject.MainApplication
import com.demo.eliproject.R
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pojo.Product
import viewmodel.MainSharedViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.lang.Exception

class OrderConfirmarion: Fragment() {
    
    private lateinit var model: MainSharedViewModel
    private lateinit var listener: ProductSelectedListener
    
    private lateinit var productRv: RecyclerView
    private lateinit var totalPriceTv: TextView
    private lateinit var confirmOrderButton: Button
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
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.order_confirmation, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.apply {
            productRv = findViewById(R.id.product_rv)
            productRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            totalPriceTv = findViewById(R.id.total_price)
            confirmOrderButton = findViewById(R.id.order_confirm_button)
            progressBarHolder = findViewById(R.id.progressBarHolder)
        }
    
        confirmOrderButton.setOnClickListener {
//            listener.addFragment(OrderSuccesFragment.newInstance(), "success")
            progressBarHolder.visibility = View.VISIBLE
            model.confirmOrder()
            Navigation.findNavController(confirmOrderButton).navigate(R.id.action_orderConfirmarion2_to_orderSuccesFragment2)
        }
        setProducts()
        initObserver()
    }
    
    private fun initObserver() {
        model.confirmOrderLD.observe(viewLifecycleOwner, {it->
            if(it.success.equals("success", true)) {
                progressBarHolder.visibility = View.GONE
                writeResponseToFile()
            }
        })
    }
    
    
    fun writeResponseToFile() {
        lifecycleScope.launch(Dispatchers.Default) {
                val gson = GsonBuilder().create()
                val json = gson.toJson(model.selectedProductsLD.value)
            try {
                val file = File(Environment.getExternalStorageDirectory().toString() + "/order.txt")
                if (!file.exists()) {
                    file.createNewFile()
                }
                val writer = FileWriter(file)
                writer.append(json)
                writer.flush()
                writer.close()
            } catch (e: IOException) {
            }
            }
    }
    
    private fun setProducts() {
        model.selectedProductsLD.observe(viewLifecycleOwner, {products->
            products?.let {
                val adapter = ConfirmProductsAdapter(products)
                productRv.adapter = adapter
                CoroutineScope(Dispatchers.Default).launch {
                    setPrice(products)
                }
            }
        })
    }
    
    suspend fun setPrice(productsList: ArrayList<Product>) {
        var price: Int = 0
        for(product in productsList) {
            price += product.productPrice.toInt()
        }
        withContext(Dispatchers.Main) {
            totalPriceTv.text = "Toal price:" + price.toString()
        }
    }
    
    companion object {
        fun newInstance(): OrderConfirmarion {
            return OrderConfirmarion()
        }
    }
}