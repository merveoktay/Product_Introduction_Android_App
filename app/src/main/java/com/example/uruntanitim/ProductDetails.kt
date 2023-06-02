package com.example.uruntanitim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.uruntanitim.Products.Companion.product
import com.example.uruntanitim.adapter.ProductAdapter
import com.example.uruntanitim.models.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetails : AppCompatActivity(){

    lateinit var r_title:TextView
    lateinit var r_description:TextView
    lateinit var r_price:TextView
    lateinit var r_discountPercentage:TextView
    lateinit var r_rating:TextView
    lateinit var r_stock:TextView
    lateinit var r_brand:TextView
    lateinit var r_category:TextView
    lateinit var r_thumbnail:TextView
    lateinit var r_image:ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val product=product
         r_title=findViewById(R.id.r_title)
        r_description=findViewById(R.id.r_description)
        r_brand=findViewById(R.id.r_brand)
        r_price=findViewById(R.id.r_price)
        r_discountPercentage=findViewById(R.id.r_discountPercentage)
        r_rating=findViewById(R.id.r_rating)
        r_stock=findViewById(R.id.r_stock)
        r_category=findViewById(R.id.r_category)
        r_thumbnail=findViewById(R.id.r_thumbnail)
        r_image=findViewById(R.id.r_image)

        r_description.text=product.description
        r_thumbnail.text=product.thumbnail
        r_title.text=product.title
        r_category.text=product.category
        r_brand.text=product.brand
        r_price.text= "${product.price.toString()} ₺"
        r_rating.text=product.rating.toString()
        r_stock.text=product.stock.toString()
        r_discountPercentage.text=product.discountPercentage.toString()

        Glide.with(this).load(product.images.get(0)).override(250,150).into(r_image)


    }

}