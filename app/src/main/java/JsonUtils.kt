import com.google.gson.Gson

object JsonUtils {
    
    val gson = Gson()
    @JvmStatic
    fun <T> fromJson(json: String?, className: Class<T>?): T? {
        return try {
            gson.fromJson(json, className)
        } catch (ie: Exception) {
            ie.printStackTrace()
            null
        }
    }
}