package com.utb.tomas.juego_pokemon

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.app.Activity



class Creditos : AppCompatActivity() {

    private var Regresar: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_creditos)


        Regresar = findViewById(R.id.btnregresar) as Button


        Regresar!!.setOnClickListener {
            val nuevoform = Intent(this, Principal::class.java)
            startActivity(nuevoform)
        }
    }


}

