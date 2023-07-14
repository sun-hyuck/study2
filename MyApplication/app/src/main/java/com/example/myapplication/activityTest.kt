package com.example.myapplication

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityTestBinding
import com.example.myapplication.databinding.ItemRecyclerBinding

class activityTest : AppCompatActivity() {

    val binding by lazy {ActivityTestBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val data:MutableList<Memo> = loadData()
        var adapter = CustomAdapter()
        adapter.listData = data
        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


 }

    fun loadData(): MutableList<Memo>{
      val data: MutableList<Memo> = mutableListOf()
      for (no in 1..100){
          var memo = Memo(no)
          data.add(memo)
      }
        return data
    }
 }


data class Memo(var no: Int)

