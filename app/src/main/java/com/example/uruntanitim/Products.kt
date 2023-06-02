package com.example.uruntanitim

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.uruntanitim.adapter.ProductAdapter
import com.example.uruntanitim.configs.ApiClient
import com.example.uruntanitim.models.Product
import com.example.uruntanitim.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.uruntanitim.models.Products


class Products : AppCompatActivity() {
    companion object {
        lateinit var product: Product
    }
    lateinit var  editTextSearch:EditText
    lateinit var productListView:ListView
    lateinit var buttonSearch:Button
    lateinit var dummyService: DummyService

    var productsArray=mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        dummyService = ApiClient.getClient().create(DummyService::class.java)

        editTextSearch = findViewById(R.id.editTextSearch)
        productListView = findViewById(R.id.productListView)
        buttonSearch=findViewById(R.id.buttonSearch)

        dummyService.getProducts().enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                for(item in response.body()!!.products)
                {
                    productsArray.add(item)
                }
                productListView.adapter=ProductAdapter(this@Products,productsArray)
            }
            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("get", t.toString())
                Toast.makeText(this@Products, "Internet or Server Fail", Toast.LENGTH_LONG).show()
            }
        })

        buttonSearch.setOnClickListener(buttonSearchOnClickListener)
        productListView.setOnItemClickListener { parent, view, position, id ->
            product=productsArray.get(position)
            val intent = Intent(this, ProductDetails::class.java)
            startActivity(intent)
        }

    }

    val buttonSearchOnClickListener= View.OnClickListener {
        val searchText=editTextSearch.text
        var productsSearchArray=mutableListOf<Product>()
        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.search(searchText).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                for(item in response.body()!!.products)
                {
                    productsSearchArray.add(item)
                }
                productListView.adapter=ProductAdapter(this@Products,productsSearchArray)
            }
            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("get", t.toString())
                Toast.makeText(this@Products, "Internet or Server Fail", Toast.LENGTH_LONG).show()
            }
        })
        closeKeyboard()
    }
    private fun closeKeyboard() {

        val view = this.currentFocus

        if (view != null) {

            val manager = getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager
            manager
                .hideSoftInputFromWindow(
                    view.windowToken, 0
                )
        }
    }

}