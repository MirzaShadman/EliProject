package viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.eliproject.MainApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pojo.Product
import pojo.StoreInfo
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import pojo.OrderSuccess
import java.io.FileOutputStream
import java.lang.Exception

class MainSharedViewModel: ViewModel() {
    
    val repo = StoreProductRepo()
    
    private val selectedProdList = ArrayList<Product>()
    
    private val storeInfoMLD = MutableLiveData<StoreInfo>()
    val storeInfoLD: LiveData<StoreInfo> = storeInfoMLD
    
    private val productListMLD = MutableLiveData<List<Product>>()
    val productListLD: LiveData<List<Product>> = productListMLD
    
    private val confirmOrderMLD = MutableLiveData<OrderSuccess>()
    val confirmOrderLD: LiveData<OrderSuccess> = confirmOrderMLD
    
    private val selectedProductsMLD = MutableLiveData<ArrayList<Product>>()
    val selectedProductsLD: LiveData<ArrayList<Product>> = selectedProductsMLD
    
    fun getStoreInfo() {
        viewModelScope.launch {
            val response = repo.getStoreInfo()
            if(response.isSuccessful) {
                storeInfoMLD.value = response.body()
            }
        }
    }
    
    fun getProducts() {
        viewModelScope.launch {
            val response = repo.getProducts()
            if(response.isSuccessful) {
                delay(3000L)
                productListMLD.value = response.body()
            }
        }
    }
    
    fun confirmOrder() {
        viewModelScope.launch {
            val response = repo.placeOrder()
            if(response.isSuccessful) {
                delay(3000L)
                confirmOrderMLD.value = response.body()
            }
        }
    }
   
    
    fun setSelectedProducts(product: Product) {
        selectedProdList.add(product)
    }
    
    fun removeProduct(product: Product) {
        selectedProdList.remove(product)
    }
    
    fun setProductData() {
        selectedProductsMLD.value = selectedProdList
    }
    
    fun getSelectedProducts() : ArrayList<Product> {
        return selectedProdList
    }
    
    fun clearProduct() {
        selectedProdList.clear()
        selectedProductsMLD.value = selectedProdList
    }
}