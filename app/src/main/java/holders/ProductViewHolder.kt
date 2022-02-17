package holders

import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import callbacks.ProductSelectedListener
import com.demo.eliproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pojo.Product
import java.lang.Exception
import java.net.URL

class ProductViewHolder(itemView: View, private val listener: ProductSelectedListener) : RecyclerView.ViewHolder(itemView) {
    
    private var productImv: ImageView
    private var productNameTv: TextView
    private var productPriceTv: TextView
    private var qtyIncBtn: Button
    private var qtyDecBtn: Button
    private var productQtyTv: TextView
    private var checkBox: CheckBox
    
    init {
        productImv = itemView.findViewById(R.id.product_imv)
        productNameTv = itemView.findViewById(R.id.product_name_tv)
        productPriceTv = itemView.findViewById(R.id.product_price_tv)
        qtyIncBtn = itemView.findViewById(R.id.qty_inc)
        productQtyTv = itemView.findViewById(R.id.product_qty_tv)
        qtyDecBtn = itemView.findViewById(R.id.qty_dec)
        checkBox = itemView.findViewById(R.id.select_chk)
    }
    
    fun setProductData(product: Product) {
        setImageUrl(product)
        productNameTv.text = product.productName
        productPriceTv.text = product.productPrice
        productQtyTv.text = product.qty.toString()
        qtyIncBtn.setOnClickListener {
            val qty = product.qty
            if(qty!! < 10) {
                product.qty = qty + 1
                productQtyTv.text = product.qty.toString()
            } else {
                Toast.makeText(itemView.context, "Max 10 products can be added", Toast.LENGTH_LONG).show()
            }
        }
        qtyDecBtn.setOnClickListener {
            val qty = product.qty
            if(qty!! > 1) {
                product.qty = qty - 1
                productQtyTv.text = product.qty.toString()
            }
        }
        
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                listener.selectProduct(product)
            } else {
                listener.removeProduct(product)
            }
        }
    }
    
    private fun setImageUrl(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(product.imageUrl)
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                withContext(Dispatchers.Main) {
                    productImv.setImageBitmap(bmp)
                }
            } catch (e: Exception) {
            
            }
        }
    }
}