package com.example.mydays_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydays_app.databinding.ActivityListBinding
import com.example.mydays_app.model.DiaryDatabase
import com.example.mydays_app.model.DiaryRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityListBinding.inflate(layoutInflater)
    }
    private val adapter = ListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val manager = LinearLayoutManager(this)

        // 시작할 때 db 데이터를 불러와야 함
        CoroutineScope(Dispatchers.IO).launch {
            val dao = DiaryDatabase.getInstance(this@ListActivity)?.diaryDao()
            val result = dao?.selectAll()!!
            withContext(Dispatchers.Main) {
                adapter.updateData(result)
            }
        }
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.adapter = adapter

        adapter.setListener{ v,position ->
            val data = adapter.getItem(position)
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("did", data.did)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            val dao = DiaryDatabase.getInstance(this@ListActivity)?.diaryDao()
            val result = dao?.selectAll()!!
            withContext(Dispatchers.Main) {
                adapter.updateData(result)
            }
        }
    }
}