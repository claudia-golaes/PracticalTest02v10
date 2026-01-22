package ro.pub.cs.systems.eim.practicaltest02v10

import android.util.Log
import java.net.ServerSocket

class ServerThread(val port:Int): Thread() {
    companion object {
        var isRunning = true
    }

    private lateinit var serverSocket: ServerSocket

    override fun run() {
        try {
            serverSocket = ServerSocket(port)
            while (isRunning) {
                try {
                    val socket = serverSocket.accept()
                    CommunicationThread(socket).start()
                    Log.d("Server Thread", "accepting communication")
                } catch (e: Exception) {
                    if (isRunning) {
                        Log.e("Server Thread", "Error accepting communication", e)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Server Thread", "Server Error", e)
        }
    }
}