package adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.eliproject.R
import holders.ConfirmProductViewHolder
import pojo.Product

class ConfirmProductsAdapter(private val items: List<Product>?): RecyclerView.Adapter<ConfirmProductViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.confirm_product_layout, null)
        val viewHolder = ConfirmProductViewHolder(view)
        return viewHolder
    }
    
    override fun onBindViewHolder(holder: ConfirmProductViewHolder, position: Int) {
        holder.setProduct(items!![position])
    }
    
    override fun getItemCount(): Int {
        return items?.size ?: 0
    }
}
