package com.example.ewing20.map.bird.birdAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ewing20.R
import com.example.ewing20.map.bird.birdVariables.Bird

class BirdAdapter(
    private val context: Context,
    private val birdList: ArrayList<Bird>,
    private val rarityTypes: Map<Int, String> = mapOf(
        Pair(0, "Common"),
        Pair(1, "Rare"),
        Pair(2, "Extremely rare")
    )

) : BaseAdapter() {
    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Any {
        return birdList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return birdList.size
    }

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        Log.i("VIEW", convertView.toString())
        val dataitem = birdList[position]

        val rowView = inflater.inflate(R.layout.bird_list, parent, false)

        rowView.findViewById<TextView>(R.id.listName).text = dataitem.name
        rowView.findViewById<TextView>(R.id.listRarity).text = "(${rarityTypes[dataitem.rarity]})"
        rowView.findViewById<TextView>(R.id.listDesc).text = dataitem.note
        rowView.findViewById<TextView>(R.id.listDate).text = dataitem.date
        rowView.findViewById<TextView>(R.id.listAddress).text = dataitem.address

        if (dataitem.image != null) {
            val bitmap = BitmapFactory.decodeByteArray(dataitem.image, 0, dataitem.image!!.size)

            rowView.findViewById<ImageView>(R.id.listImage).setImageBitmap(bitmap)
        }

        rowView.tag = position
        return rowView
    }
}