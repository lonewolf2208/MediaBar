package com.example.mediabar

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediabar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private var layoutManager: RecyclerView.LayoutManager?=null
    lateinit var adapter: Adapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var data = arrayListOf<Content>(Content(listOf(0.0F, 25.0F),"#0099cc"),Content(listOf(25.0F, 50.0F),"#0099cc"),Content(listOf(50.0F, 75.0F),"#0099cc"),Content(listOf(75F, 100.0F),"#0099cc"))
        binding=ActivityMainBinding.inflate(layoutInflater)
        layoutManager = LinearLayoutManager(this)
        binding.seekBarRecycler.layoutManager = layoutManager
        adapter = Adapter(data,this)
        binding.seekBarRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
        setContentView(binding.root)
    }
}