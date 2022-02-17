package callbacks

import androidx.fragment.app.Fragment
import pojo.Product

interface ProductSelectedListener {
    fun selectProduct(product: Product)
    fun removeProduct(product: Product)
}