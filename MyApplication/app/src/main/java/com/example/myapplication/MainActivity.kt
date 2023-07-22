package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

<<<<<<<<< Temporary merge branch 1
        val intent = Intent(this, activityTest::class.java)
=========
        val intent = Intent(this, WeatherActivity::class.java)
>>>>>>>>> Temporary merge branch 2

        var user = User("qwer", "1234")
        val a = binding.LoginID
        val b = binding.LoginPW




        binding.btn.setOnClickListener {
        var inputID = binding.LoginID.text.toString()
        var inputpassword = binding.LoginPW.text.toString()
        if (inputID==user.ID && inputpassword==user.Password){
<<<<<<<<< Temporary merge branch 1
            var intent = Intent(this, activityTest::class.java)
=========
                var intent = Intent(this, WeatherActivity::class.java)
>>>>>>>>> Temporary merge branch 2
            startActivity(intent)
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
        }else
        { if(inputID !=user.ID && inputpassword==user.Password){
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
        }else{
            if (inputID ==user.ID && inputpassword != user.Password){
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }else{
                if (inputID !=user.ID && inputpassword != user.Password){
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }else{
                    if(inputID.isNullOrEmpty() && inputpassword.isNullOrEmpty()){
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        }
            }

    }
}

data class User(val ID: String,
                val Password: String
                )



















