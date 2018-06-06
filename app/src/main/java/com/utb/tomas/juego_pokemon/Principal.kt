package com.utb.tomas.juego_pokemon

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast


class Principal : AppCompatActivity() {
    private var jugar: Button? = null
    private var creditos: Button? = null
    private var listapokemon: Button? = null
     var salir: Button? = null
    private var reproductor: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_principal)
        jugar = findViewById(R.id.btnjugar) as Button
        creditos = findViewById(R.id.btnacerca) as Button
        listapokemon = findViewById(R.id.btnlista) as Button
        salir = findViewById(R.id.btnsalir) as Button
        PokemonDB.cargarDatos(applicationContext)
        Log.e("hola","estamos lanzando la actividad\n")
        reproductor = MediaPlayer.create(this, R.raw.musicafondo)
        reproductor!!.isLooping = true
        reproductor!!.start()



        jugar!!.setOnClickListener {
            val nuevoform = Intent(this , Jugar::class.java)
            startActivity(nuevoform)
        }

        listapokemon!!.setOnClickListener {
            val nuevoform = Intent(this , Pokedex::class.java)
            startActivity(nuevoform)
        }

        creditos!!.setOnClickListener {
            val nuevoform = Intent(this,Creditos ::class.java)
            startActivity(nuevoform)
        }

        salir!!.setOnClickListener { finish() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (reproductor!!.isPlaying) {
            reproductor!!.stop()
            reproductor!!.release()
        }
    }

    override fun onResume() {
        super.onResume()
        reproductor!!.start()
    }

    override fun onPause() {
        super.onPause()
        reproductor!!.pause()
    }
}
