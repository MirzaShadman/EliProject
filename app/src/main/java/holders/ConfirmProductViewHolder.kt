package holders

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.demo.eliproject.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pojo.Product
import java.lang.Exception
import java.net.URL

class ConfirmProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var productImv: ImageView = itemView.findViewById(R.id.product_imv)
    
    fun setProduct(product: Product) {
        setImageUrl(product)
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