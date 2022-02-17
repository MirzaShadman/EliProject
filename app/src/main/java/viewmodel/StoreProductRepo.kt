package viewmodel

import com.demo.eliproject.MainApplication
import network.RetrofitHelper
import network.StoreProductFetcher
import pojo.OrderSuccess
import pojo.Product
import pojo.StoreInfo
import retrofit2.Response

class StoreProductRepo: BaseRepo() {
    
    val storeProductsFetcher = RetrofitHelper.getInstance(MainApplication.applicationContext()).create(
            StoreProductFetcher::class.java)
    
    suspend fun getStoreInfo(): Response<StoreInfo> {
        return storeProductsFetcher.getStoreInfo()
    }
    
    suspend fun getProducts(): Response<List<Product>> {
        return storeProductsFetcher.getProductList()
    }
    
    suspend fun placeOrder(): Response<OrderSuccess> {
        return storeProductsFetcher.placeOrder()
    }
}