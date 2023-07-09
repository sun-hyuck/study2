package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sum = age(3,4)
        Log.d("MainActivity",  "INTEGER입니다. $sum" )
        val second = sun(10)
        Log.d("MainActivity",  "2번째 ${sun(5)}" )

    }

     fun age(a : Int, b : Int) : Int{
         return a - b
     }
    fun sun(c : Int) : Int{
        var sum = 0
        for( index in 0..c ){
            sum += index
        }
        return sum
    }
}