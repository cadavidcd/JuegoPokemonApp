package com.example.tomas.juego_pokemon

import android.app.ProgressDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import java.util.ArrayList
import java.util.Collections


class Jugar : AppCompatActivity(), View.OnClickListener {


    lateinit var lblintentos: TextView
    lateinit var lblcuenta: TextView
    lateinit var imagen: ImageView
    private var reproductor: MediaPlayer? = null
    private val TIEMPO_ESPERA = 5000
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null
    private var btn4: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_jugar)
        iniciarComponentes()
        PokemonDB.cargarDatos(applicationContext)
        reproductor = MediaPlayer.create(this, R.raw.atrapalosya)
        reproductor!!.isLooping = true
        reproductor!!.start()
        Log.e("hola","estamos lanzando la actividad\n")
        MiTarea().onPreExecute()
        PokemonDB.cargarConfiguracion(applicationContext)
        lblintentos.text = PokemonDB.INTENTOS.toString()
    }


    fun setSombra(id: Int) {
        val resId = resources.getIdentifier(PokemonDB.getSombra(id), "drawable", packageName)

        imagen.setImageResource(resId)
    }

    fun setPokemon(id: Int) {
        val resId = resources.getIdentifier(PokemonDB.getNombre(id), "drawable", packageName)
        imagen.setImageResource(resId)
    }

    private fun iniciarComponentes() {
        imagen = findViewById(R.id.miimagen) as ImageView
        lblintentos = findViewById(R.id.lblintentos) as TextView
        lblcuenta = findViewById(R.id.lblcuenta) as TextView
        btn1 = findViewById(R.id.btn1) as Button
        btn2 = findViewById(R.id.btn2) as Button
        btn3 = findViewById(R.id.btn3) as Button
        btn4 = findViewById(R.id.btn4) as Button
        btn1!!.setOnClickListener(this)
        btn2!!.setOnClickListener(this)
        btn3!!.setOnClickListener(this)
        btn4!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val boton = v as Button
        val nombrePokemon = boton.text.toString().toLowerCase()
        if (PokemonDB.isPokemon(nombrePokemon)) {
            setPokemon(PokemonDB.NUMEROGENERADO)
            PokemonDB.setAdivinado(PokemonDB.NUMEROGENERADO, true)
            PokemonDB.ADIVINADOS = PokemonDB.ADIVINADOS + 1
            habilitarBotones(false)
            boton.visibility = View.VISIBLE
            boton.isClickable = false
            esperar()
        } else {
            PokemonDB.DisminuirIntentos()
            lblintentos.text = PokemonDB.INTENTOS.toString()
            v.setVisibility(View.INVISIBLE)
        }

        if (PokemonDB.isGameOver) {
            val i = Intent(this@Jugar, Perder::class.java)
            startActivity(i)
            finish()
        }
    }

    fun esperar() {
        object : CountDownTimer(TIEMPO_ESPERA.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                lblcuenta.text = "Generando en " + millisUntilFinished / 1000
            }

            override fun onFinish() {
                lblcuenta.text = ""
                if (!PokemonDB.isWin) {
                    MiTarea().onPreExecute()
                } else {
                    Toast.makeText(applicationContext, resources.getString(R.string.msg_ganaste), Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }.start()
    }

    fun habilitarBotones(valor: Boolean) {
        if (valor) {
            btn1!!.visibility = View.VISIBLE
            btn2!!.visibility = View.VISIBLE
            btn3!!.visibility = View.VISIBLE
            btn4!!.visibility = View.VISIBLE
            btn1!!.isClickable = true
            btn2!!.isClickable = true
            btn3!!.isClickable = true
            btn4!!.isClickable = true
        } else {
            btn1!!.visibility = View.INVISIBLE
            btn2!!.visibility = View.INVISIBLE
            btn3!!.visibility = View.INVISIBLE
            btn4!!.visibility = View.INVISIBLE
        }
    }

    override fun onStop() {
        super.onStop()
        if (PokemonDB.isGameOver) {
            PokemonDB.removerDatos(applicationContext)
        } else {
            PokemonDB.guardarDatos(applicationContext)
        }
        reproductor!!.pause()
    }

    override fun onResume() {
        super.onResume()
        reproductor!!.start()
    }

    override fun onDestroy() {
        if (reproductor!!.isPlaying) {
            reproductor!!.stop()
            reproductor!!.release()
        }
        super.onDestroy()
    }

    private inner class MiTarea () {

        private var dialog: ProgressDialog? = null
        private var numero = 0
        private val totalgenerados = 4
        private val numerosrestantes = totalgenerados - 1
        private var contador = 0
        private var permitidos = 0
        private var valorgenerado = -1
        internal var numeros = ArrayList<Int>()

        fun onPreExecute() {
            dialog = ProgressDialog(this@Jugar)
            dialog!!.setMessage("Generando ...")
            dialog!!.show()
        }

         fun doInBackground(vararg params: Void): Void? {
            do {
                numero = (Math.random() * PokemonDB.tamaño).toInt()
                if (!PokemonDB.isAdivinado(numero) && valorgenerado <= 0) {
                    valorgenerado = numero
                    contador++
                    numeros.add(numero)
                } else if (!numeros.contains(numero) && permitidos < numerosrestantes) {
                    numeros.add(numero)
                    contador++
                    permitidos++
                }
            } while (contador < totalgenerados)
            Collections.shuffle(numeros)

            try {
                Thread.sleep(250)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return null
        }

        fun onPostExecute(aVoid: Void) {
            PokemonDB.NUMEROGENERADO = valorgenerado
            setSombra(valorgenerado)
            habilitarBotones(true)
            btn1!!.text = PokemonDB.getNombre(numeros[0])
            btn2!!.text = PokemonDB.getNombre(numeros[1])
            btn3!!.text = PokemonDB.getNombre(numeros[2])
            btn4!!.text = PokemonDB.getNombre(numeros[3])
            if (dialog!!.isShowing) {
                dialog!!.dismiss()
            }
           onPostExecute(aVoid)

        }
    }
}


