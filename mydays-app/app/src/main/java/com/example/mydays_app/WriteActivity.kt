package com.example.mydays_app

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import com.example.mydays_app.databinding.ActivityWriteBinding
import com.example.mydays_app.model.DiaryDatabase
import com.example.mydays_app.model.DiaryRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWriteBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var today = GregorianCalendar()
        var selectYear: Int = today.get(Calendar.YEAR)
        var selectMonth: Int = today.get(Calendar.MONTH)
        var selectDate: Int = today.get(Calendar.DATE)

        var result: Int = -1

        binding.selectDayBtn.setOnClickListener {
            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    selectYear = year
                    selectMonth = month+1
                    selectDate = dayOfMonth

                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = DiaryDatabase.getInstance(this@WriteActivity)?.diaryDao()
                        result = dao?.getDiaryCnt(selectYear, selectMonth, selectDate)!!
                    }

                    binding.TextViewSelectDay.setText("${selectYear}년 ${selectMonth}월 ${selectDate}일")
                }
            }, selectYear, selectMonth, selectDate)
            dlg.show()
        }

        binding.writeSubmitBtn.setOnClickListener {
            val title = binding.writeDiaryTitle.text.toString()
            val content = binding.writeDiaryContent.text.toString()

            Log.d("result", result.toString())

            if (title.isNotEmpty() && content.isNotEmpty() && result == 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    val db = DiaryDatabase.getInstance(this@WriteActivity)
                    db?.diaryDao()?.insert(DiaryRecord(0,selectYear,selectMonth,selectDate,title,content)
                    )
                }
                finish()
            } else {
                binding.errorText.text = "일기를 등록할 수 없습니다."
            }
        }
    }
}