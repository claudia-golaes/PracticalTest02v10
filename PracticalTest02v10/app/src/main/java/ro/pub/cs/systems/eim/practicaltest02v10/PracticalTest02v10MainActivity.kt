package ro.pub.cs.systems.eim.practicaltest02v10

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import java.io.InputStream


class PracticalTest02v10MainActivity : AppCompatActivity() {
    private lateinit var serverPort : EditText
    private lateinit var serverButton : Button
    private lateinit var clientAddress : EditText
    private lateinit var clientPort : EditText
    private lateinit var pokemonName : EditText
    private lateinit var getPokemonButton : Button
    private lateinit var pokemonTypeText : TextView
    private lateinit var pokemonAbilityText : TextView
    private lateinit var pokemonImage: ImageView
    private lateinit var serverThread : ServerThread

    inner class ButtonListener() : View.OnClickListener {
        override fun onClick(v: View?) {
            val serverPortText = serverPort.text.toString().toInt()
            val clientPortText = clientPort.text.toString().toInt()
            val clientAddressText = clientAddress.text.toString()
            val pokemon = pokemonName.text.toString()
            if(v?.id == R.id.serverButton){
                serverThread = ServerThread(serverPortText)
                serverThread.start()
            } else if(v?.id == R.id.getPokemonButton) {
                ClientThread(pokemon, clientPortText, clientAddressText) {
                        response ->
                    runOnUiThread {
                        val list = response.toString().split(",")
                        pokemonAbilityText.text = list[0]
                        pokemonTypeText.text = list[1]
                        pokemonImage.setImageURI(list[2].toUri())
                    }
                }.start()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test02v10_main)

        serverPort = findViewById(R.id.serverPort)
        serverButton = findViewById(R.id.serverButton)
        clientAddress = findViewById(R.id.clientAddress)
        clientPort = findViewById(R.id.clientPort)
        pokemonName = findViewById(R.id.pokemonName)
        getPokemonButton = findViewById(R.id.getPokemonButton)
        pokemonTypeText = findViewById(R.id.pokemonTypeText)
        pokemonAbilityText = findViewById(R.id.pokemonAbilityText)
        pokemonImage = findViewById(R.id.imagePokemon)

        serverButton.setOnClickListener(ButtonListener())
        getPokemonButton.setOnClickListener(ButtonListener())

    }

}