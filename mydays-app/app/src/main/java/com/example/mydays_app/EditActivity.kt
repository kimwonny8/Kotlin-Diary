package com.example.mydays_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mydays_app.databinding.ActivityEditBinding
import com.example.mydays_app.model.DiaryDatabase
import com.example.mydays_app.model.DiaryRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityEditBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var did = intent.getIntExtra("did", -1)
        // 시작할 때 db 데이터를 불러와야 함
        CoroutineScope(Dispatchers.IO).launch {
            val dao = DiaryDatabase.getInstance(this@EditActivity)?.diaryDao()
            val result = dao?.getDiary(did)!!
            Log.d("result", result.toString())

            var year = result[0].year.toString()
            var month = result[0].month.toString()
            var date = result[0].date.toString()
            var day = year+"년 "+month+"월 "+date+"일"
            var title = result[0].title
            var content = result[0].content

            binding.editDiaryDay.text = day
            binding.editDiaryTitle.setText(title)
            binding.editDiaryContent.setText(content)

            // 수정 버튼
            binding.editSubmitBtn.setOnClickListener {
                title = binding.editDiaryTitle.text.toString()
                content = binding.editDiaryContent.text.toString()

                Log.d("year",year!!)
                CoroutineScope(Dispatchers.IO).launch {
                    val db = DiaryDatabase.getInstance(this@EditActivity)
                    db?.diaryDao()?.update(DiaryRecord(did, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date), title, content))
                }
                finish()
            }

            // 삭제 버튼
            binding.deleteSubmitBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val db = DiaryDatabase.getInstance(this@EditActivity)
                    db?.diaryDao()?.delete(DiaryRecord(did, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(date), title, content))
                }
                finish()
            }
        }
    }
}