package com.utb.tomas.juego_pokemon

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

/**
 * Created by Tomas on 08/08/2014.
 */
class PokemonAdapter(protected var activity: Activity, protected var items: ArrayList<Pokemon>) : BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
        var vi = contentView

        if (contentView == null) {
            val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            vi = inflater.inflate(R.layout.listview_layout, null)
        }

        val item = items[position]

        val image = vi!!.findViewById(R.id.imgpokemon) as ImageView
        val resId = activity.resources.getIdentifier(item.nombre, "drawable", activity.packageName)
        image.setImageResource(resId)
        val nombre = vi.findViewById(R.id.lblpokemon) as TextView
        nombre.text = if (item.nombre == "pokeball") "??????" else item.nombre
        return vi
    }
}
