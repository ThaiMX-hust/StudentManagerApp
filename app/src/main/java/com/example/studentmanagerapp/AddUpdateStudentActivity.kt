package com.example.studentmanagerapp

import Student
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
//import com.example.studentmanagerapp.Student

class AddUpdateStudentActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etId: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText

    private var isUpdate = false
    private var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update)

        etName = findViewById(R.id.etName)
        etId = findViewById(R.id.etId)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        val btnSave: Button = findViewById(R.id.btnSave)

        isUpdate = intent.getBooleanExtra("isUpdate", false)
        index = intent.getIntExtra("index", -1)
        if (isUpdate) {
            title = getString(R.string.title_update)
            intent.getParcelableExtra<Student>("student")?.let {
                etName.setText(it.name)
                etId.setText(it.id)
                etEmail.setText(it.email)
                etPhone.setText(it.phone)
            }
        } else {
            title = getString(R.string.title_add)
        }

        btnSave.setOnClickListener {
            val student = Student(
                etName.text.toString(),
                etId.text.toString(),
                etEmail.text.toString(),
                etPhone.text.toString()
            )
            setResult(RESULT_OK, intent.apply {
                putExtra("student", student)
                putExtra("isUpdate", isUpdate)
                putExtra("index", index)
            })
            finish()
        }
    }
}