import android.content.Context
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.nio.charset.StandardCharsets

class MockResponseInterceptor(private val context: Context): Interceptor {
    
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val json: String? = if(request.url.toUrl().toString().contains("/storeinfo")) {
            loadJSONFromAsset("storeinfo.json")
        } else if(request.url.toUrl().toString().contains("/products")) {
            loadJSONFromAsset("products.json")
        } else if(request.url.toUrl().toString().contains("/orderDone")) {
            "{\"success\": \"success\"}"
        } else {
            "success"
        }
        
        if(!json.isNullOrEmpty()) {
            val rb = json.toResponseBody("application/json".toMediaTypeOrNull())
            return Response.Builder()
                    .code(200)
                    .body(rb)
                    .addHeader("content-type", "application/json")
                    .message("success")
                    .protocol(Protocol.HTTP_2)
                    .request(chain.request())
                    .build()
        }
        return chain.proceed(request)
    }
    
    private fun loadJSONFromAsset(fileName: String): String? {
        var json: String? = null
        
        try {
            val assetFile = context.assets.open(fileName)
            val size = assetFile.available()
            val buffer = ByteArray(size)
            assetFile.read(buffer)
            assetFile.close()
            json = String(buffer, StandardCharsets.UTF_8)
            
        } catch (ex: IOException) {
        
        }
        
        return json
    }
}