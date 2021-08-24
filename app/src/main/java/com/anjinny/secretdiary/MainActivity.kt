package com.anjinny.secretdiary

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }
    private val btnOpen: AppCompatButton by lazy {
        findViewById(R.id.btnOpen)
    }
    private val btnChangePassword: AppCompatButton by lazy {
        findViewById(R.id.btnReset)
    }

    private var changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        btnOpen.setOnClickListener {

            if (changePasswordMode) { //변경 중이면 다른 작업 못하게 막기
                Toast.makeText(this, "비밀번호 변경중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreference = getSharedPreferences("password", MODE_PRIVATE)
            val userPassword = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreference.getString("password", "000").equals(userPassword)) {
                //성공
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showFailAlertDialog()
            }
        }

        btnChangePassword.setOnClickListener {
            val passwordPreference = getSharedPreferences("password", MODE_PRIVATE)
            val userPassword = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changePasswordMode) {
                //변경할 비밀번호 저장
                passwordPreference.edit(true) {
                    putString("password", userPassword)
                }

                changePasswordMode = false
                btnChangePassword.setBackgroundColor(Color.BLACK)
            } else {
                if (passwordPreference.getString("password", "000").equals(userPassword)) {
                    //비밀번호 변경 모드
                    changePasswordMode = true
                    btnChangePassword.setBackgroundColor(Color.RED)
                    Toast.makeText(this, "비밀번호를 변경하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    showFailAlertDialog()
                }
            }
        }
    }

    private fun showFailAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 맞지 않습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }
}