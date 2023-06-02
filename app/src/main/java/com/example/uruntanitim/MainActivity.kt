package com.example.uruntanitim

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.uruntanitim.configs.ApiClient
import com.example.uruntanitim.configs.Util
import com.example.uruntanitim.models.User
import com.example.uruntanitim.models.UserData
import com.example.uruntanitim.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var editTextUserName: EditText
    lateinit var editTextPassword:EditText
    lateinit var loginButton: Button
    lateinit var dummyService: DummyService

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("users", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        dummyService = ApiClient.getClient().create(DummyService::class.java)

        editTextUserName = findViewById(R.id.editTextUserName)
        editTextPassword = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener(btnOnClickListener)

        val username = sharedPreferences.getString("username", "")
        editTextUserName.setText(username)

    }

    val btnOnClickListener = View.OnClickListener {
        val name = editTextUserName.text.toString()
        val pass = editTextPassword.text.toString()

        if(name!=null  && pass!=null){
            val user = User(name,pass)
            dummyService.login(user).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                val jwtUser = response.body()
                Log.d("status", response.code().toString())
                if (jwtUser != null) {
                    Util.user = jwtUser
                    Log.d("User", jwtUser.toString())

                    editor.putString("username", jwtUser.username)
                    editor.putString("firstName", jwtUser.firstName)
                    editor.commit()

                    val intent = Intent(this@MainActivity, Products::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.e("login", t.toString())
                Toast.makeText(this@MainActivity, "Internet or Server Fail", Toast.LENGTH_LONG).show()
            }
        })
        }else if( name==null  || pass==null)
        {
            Toast.makeText(this@MainActivity, "Please do not leave blank !", Toast.LENGTH_LONG).show()
        }
    }
}