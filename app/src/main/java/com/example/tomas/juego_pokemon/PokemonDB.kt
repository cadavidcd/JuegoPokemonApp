package com.example.tomas.juego_pokemon

import android.content.Context
import android.content.SharedPreferences

import java.util.ArrayList
import java.util.Arrays

/**
 * Created by Tomas on 02/11/2015.
 */
object PokemonDB {
    private val listaPokemon = ArrayList(Arrays.asList(
            Pokemon(0, "bulbasaur", "s_bulbasaur", false),
            Pokemon(1, "ivysaur", "s_ivysaur", false),
            Pokemon(2, "venasaur", "s_venasaur", false),
            Pokemon(3, "charmander", "s_charmander", false),
            Pokemon(4, "charmeleon", "s_charmeleon", false),
            Pokemon(5, "charizard", "s_charizard", false),
            Pokemon(6, "squirtle", "s_squirtle", false),
            Pokemon(7, "wartortle", "s_wartortle", false),
            Pokemon(8, "blastoise", "s_blastoise", false),
            Pokemon(9, "caterpie", "s_caterpie", false),
            Pokemon(10, "metapod", "s_metapod", false),
            Pokemon(11, "butterfree", "s_butterfree", false),
            Pokemon(12, "weedle", "s_weedle", false),
            Pokemon(13, "kakuna", "s_kakuna", false),
            Pokemon(14, "beedrill", "s_beedrill", false),
            Pokemon(15, "pidgey", "s_pidgey", false),
            Pokemon(16, "pidgeotto", "s_pidgeotto", false),
            Pokemon(17, "pidgeot", "s_pidgeot", false),
            Pokemon(18, "rattata", "s_rattata", false),
            Pokemon(19, "raticate", "s_raticate", false),
            Pokemon(20, "spearow", "s_spearow", false),
            Pokemon(21, "fearow", "s_fearow", false),
            Pokemon(22, "ekans", "s_ekans", false),
            Pokemon(23, "arbok", "s_arbok", false),
            Pokemon(24, "pikachu", "s_pikachu", false),
            Pokemon(25, "raichu", "s_raichu", false),
            Pokemon(26, "sandshrew", "s_sandshrew", false),
            Pokemon(27, "sandslash", "s_sandslash", false),
            Pokemon(28, "nidoran", "s_nidoran", false),
            Pokemon(29, "nidorina", "s_nidorina", false),
            Pokemon(30, "nidoqueen", "s_nidoqueen", false),
            Pokemon(31, "cloyster", "s_cloyster", false),
            Pokemon(32, "gloom", "s_gloom", false),
            Pokemon(33, "krabby", "s_krabby", false),
            Pokemon(34, "magmar", "s_magmar", false),
            Pokemon(35, "marowak", "s_marowak", false),
            Pokemon(36, "snorlax", "s_snorlax", false),
            Pokemon(37, "starmie", "s_starmie", false),
            Pokemon(38, "vulpix", "s_vulpix", false)
    ))

    var ADIVINADOS = 0
    var INTENTOS = 5
    var NUMEROGENERADO = 0
    var isACTIVATE_SOUND = true
    val Nombre= String
    val Sombra = String



    val tamaño: Int
        get() = listaPokemon.size

    val isGameOver: Boolean
        get() = INTENTOS == 0

    val isWin: Boolean
        get() = ADIVINADOS == tamaño




    fun getNombre(id: Int): String {
        return listaPokemon[id].nombre!!.toLowerCase().replace(" ", "_")
    }


    fun getSombra(id: Int): String {
        return listaPokemon[id].sombra!!.toLowerCase().replace(" ", "_")
    }

    fun getPokemon(id: Int): Pokemon {
        return listaPokemon[id]
    }

    fun isAdivinado(id: Int): Boolean {
        return listaPokemon[id].isAdivinado
    }

    fun setAdivinado(id: Int, valor: Boolean) {
        listaPokemon[id].isAdivinado = valor
    }

    fun cargarDatos(c: Context) {
        val mispreferencias = c.getSharedPreferences("DatosJuego", Context.MODE_PRIVATE)
        INTENTOS = mispreferencias.getInt("INTENTOS", 5)
        ADIVINADOS = mispreferencias.getInt("ADIVINADOS", 0)
        isACTIVATE_SOUND = mispreferencias.getBoolean("SONIDO", true)
        for (elemento in listaPokemon) {
            elemento.isAdivinado = mispreferencias.getBoolean(elemento.nombre, false)
        }
    }

    fun guardarDatos(c: Context) {
        val mispreferencias = c.getSharedPreferences("DatosJuego", Context.MODE_PRIVATE)
        val editor = mispreferencias.edit()
        editor.putInt("INTENTOS", INTENTOS)
        editor.putInt("ADIVINADOS", ADIVINADOS)
        editor.putBoolean("SONIDO", isACTIVATE_SOUND)
        for (elemento in listaPokemon) {
            editor.putBoolean(elemento.nombre, elemento.isAdivinado)
        }
        editor.apply()
    }

    fun cargarConfiguracion(c: Context) {
        val mispreferencias = c.getSharedPreferences("DatosConfiguracion", Context.MODE_PRIVATE)
        isACTIVATE_SOUND = mispreferencias.getBoolean("SONIDO", true)
    }

    fun guardarConfiguracion(c: Context) {
        val mispreferencias = c.getSharedPreferences("DatosConfiguracion", Context.MODE_PRIVATE)
        val editor = mispreferencias.edit()
        editor.putBoolean("SONIDO", isACTIVATE_SOUND)
        editor.apply()
    }

    fun removerConfiguracion(c: Context) {
        val settings = c.getSharedPreferences("DatosConfiguracion", Context.MODE_PRIVATE)
        settings.edit().clear().apply()
    }

    fun removerDatos(c: Context) {
        val settings = c.getSharedPreferences("DatosJuego", Context.MODE_PRIVATE)
        settings.edit().clear().apply()
    }

    fun isPokemon(x: String): Boolean {
        return listaPokemon[NUMEROGENERADO].nombre.equals(x, ignoreCase = true)
    }

    fun DisminuirIntentos() {
        INTENTOS--
    }
}
