package com.anjinny.secretdiary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {
    private val handler: Handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val etDiary: EditText = findViewById(R.id.etDiary)

        val diarySharedPreference = getSharedPreferences("diary", MODE_PRIVATE)

        etDiary.setText(diarySharedPreference.getString("diary", ""))

        val runnable = Runnable {
            getSharedPreferences("diary", MODE_PRIVATE).edit {
                putString("diary", etDiary.text.toString())
                Log.d("DiaryActivity", etDiary.text.toString())
            }
        }

        etDiary.addTextChangedListener {
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}