package com.utb.tomas.juego_pokemon

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

/**
 * Created by Tomas on 12/08/2014.
 */
class Perder : AppCompatActivity() {

    private val TIEMPO_ESPERA = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_perder)
        Cuenta_Regresiva()
    }

    fun Cuenta_Regresiva() {
        object : CountDownTimer(TIEMPO_ESPERA.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                finish()
            }
        }.start()
    }
}

