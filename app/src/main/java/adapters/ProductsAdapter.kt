package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import callbacks.ProductSelectedListener
import com.demo.eliproject.R
import holders.ProductViewHolder
import pojo.Product

class ProductsAdapter(private val productList: List<Product>?, private val listener: ProductSelectedListener): RecyclerView.Adapter<ProductViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val holder = ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_layout, null), listener)
        return holder
    }
    
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.setProductData(productList!![position])
    }
    
    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }
}