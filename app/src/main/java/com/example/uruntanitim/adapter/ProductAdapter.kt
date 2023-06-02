package com.example.uruntanitim.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.uruntanitim.R
import com.example.uruntanitim.models.Product

class ProductAdapter(private val context: Activity, private val list: MutableList<Product>) : ArrayAdapter<Product>(context, R.layout.custom_list_item, list ) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rootView = context.layoutInflater.inflate(R.layout.custom_list_item, null, true)

        val r_title = rootView.findViewById<TextView>(R.id.r_title)
        val r_description = rootView.findViewById<TextView>(R.id.r_description)
        val r_price=rootView.findViewById<TextView>(R.id.r_price)
        val r_image = rootView.findViewById<ImageView>(R.id.r_image)

        val product = list.get(position)

        r_title.text =product.title
        r_description.text = product.description
        r_price.text= "${product.price.toString()} ₺"

        Glide.with(context).load(product.images.get(0)).override(250,150).into(r_image)

        return rootView
    }
}