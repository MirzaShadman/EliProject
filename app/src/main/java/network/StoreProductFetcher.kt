package network

import pojo.OrderSuccess
import pojo.Product
import pojo.StoreInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface StoreProductFetcher {
    @GET("/storeinfo")
    suspend fun getStoreInfo(): Response<StoreInfo>
    
    @GET("/products")
    suspend fun getProductList(): Response<List<Product>>
    
    @POST("/orderDone")
    suspend fun placeOrder(): Response<OrderSuccess>
}