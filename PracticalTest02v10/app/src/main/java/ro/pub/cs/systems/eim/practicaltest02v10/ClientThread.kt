package ro.pub.cs.systems.eim.practicaltest02v10

import android.util.Log
import java.net.Socket

class ClientThread(val pokemon: String, val clientPort: Int, val clientAddress: String, val callback: (String) -> Unit): Thread() {
    override fun run(){
        try {
            val socket = Socket(clientAddress, clientPort)
            val writer = Utilities.getWriter(socket)
            val reader = Utilities.getReader(socket)

            writer.println(pokemon)
            val response = reader.readText()
            Log.d("Client Thread", "Response is $response")

            socket.close()
            callback(response)
            Log.d("Client Thread", "Client Successful")
        } catch (e: Exception){
            Log.e("ClientThread", "Client error", e)
        }
    }
}