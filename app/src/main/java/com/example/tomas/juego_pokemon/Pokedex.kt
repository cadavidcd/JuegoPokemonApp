package com.example.tomas.juego_pokemon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ListView

import java.util.ArrayList


/**
 * Created by Tomas on 09/08/2014.
 */
class Pokedex : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_lista_pokemon)
        val lv = findViewById(R.id.lvlista) as ListView
        val itemsCompra = obtenerItems()
        val adapter = PokemonAdapter(this, itemsCompra)
        lv.adapter = adapter
    }

    private fun obtenerItems(): ArrayList<Pokemon> {
        val items = ArrayList<Pokemon>()
        PokemonDB.cargarDatos(applicationContext)
        for (i in 0 until PokemonDB.tamaño) {
            val bol = PokemonDB.isAdivinado(i)
            if (bol) {
                items.add(PokemonDB.getPokemon(i))
            } else {
                items.add(Pokemon(i, "pokeball", null, false))
            }
        }
        return items
    }
}
