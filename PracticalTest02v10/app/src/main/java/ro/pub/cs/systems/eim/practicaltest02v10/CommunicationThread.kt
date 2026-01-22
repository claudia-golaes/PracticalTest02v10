package ro.pub.cs.systems.eim.practicaltest02v10

import kotlin.toString

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.net.Socket
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class CommunicationThread(val socket: Socket) : Thread() {

    companion object {
        const val API_URL = "https://pokeapi.co/api/v2/pokemon/"
    }

    override fun run() {
        try {
            val reader = Utilities.getReader(socket)
            val writer = Utilities.getWriter(socket)

            val query = reader.readLine()
            Log.d("[COMMUNICATION THREAD]", "The pokemon's name is $query")

            val result = getData(query)

            writer.println(result)
            writer.flush()
            socket.close()
        } catch (e: Exception){
            Log.e("Communication Thread", "Communication error", e)
        }
    }

    fun getData(query: String) : String{
        val url = URL("${API_URL}$query")
        val connection = url.openConnection() as HttpsURLConnection
        connection.requestMethod = "GET"
        var response = connection.inputStream.bufferedReader().readText()

        Log.d("Communication Thread", "Response: $response")

        var json = JSONObject(response)
        var array = json.getJSONArray("abilities")
        var abilityObject = array.getJSONObject(0)
        var ability = abilityObject.getJSONObject("ability")
        var abilityName = ability.getString("name")

        var types = json.getJSONArray("types")
        var typeObject = types.getJSONObject(0)
        var type = typeObject.getJSONObject("type")
        var typeName = type.getString("name")

        var sprites = json.getJSONObject("sprites")
        var imageUrl = sprites.getString("front_default")


        Log.d("Communication Thread", "ARRAY: $imageUrl")

        val result = abilityName + "," + typeName + "," + imageUrl

        return result
    }

}