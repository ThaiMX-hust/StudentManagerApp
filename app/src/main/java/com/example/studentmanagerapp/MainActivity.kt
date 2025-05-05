package com.example.studentmanagerapp

import Student
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.studentmanagerapp.R
//import com.example.studentmanagerapp.Student
import com.example.studentmanagerapp.StudentAdapter
import com.example.studentmanagerapp.StudentRepository

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val isUpdate = result.data!!.getBooleanExtra("isUpdate", false)
            val student: Student? = result.data!!.getParcelableExtra("student")
            val index = result.data!!.getIntExtra("index", -1)
            student?.let {
                if (isUpdate && index != -1) {
                    StudentRepository.update(index, it)
                    adapter.notifyItemChanged(index)
                } else {
                    StudentRepository.add(it)
                    adapter.notifyItemInserted(StudentRepository.students.lastIndex)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 1. Thêm dữ liệu mẫu
        StudentRepository.add(Student("Nguyễn Văn A", "123456", "a@gmail.com", "0901234567"))

        // 2. Khởi tạo adapter sau khi đã có dữ liệu
        adapter = StudentAdapter(
            StudentRepository.students,
            onUpdate = { index -> openAddUpdate(true, index) },
            onDelete = { index -> confirmDelete(index) },
            onCall = { phone -> dialPhone(phone) },
            onEmail = { email -> sendEmail(email) }
        )

        // 3. Gán vào RecyclerView
        recyclerView.adapter = adapter

        // 4. (Không cần notify vì dữ liệu có sẵn khi khởi tạo adapter)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                openAddUpdate(false, -1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun openAddUpdate(isUpdate: Boolean, index: Int) {
        val intent = Intent(this, AddUpdateStudentActivity::class.java).apply {
            putExtra("isUpdate", isUpdate)
            putExtra("index", index)
            if (isUpdate) putExtra("student", StudentRepository.students[index])
        }
        launcher.launch(intent)
    }
    private fun confirmDelete(index: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_delete_title)
            .setMessage(R.string.confirm_delete_msg)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                StudentRepository.remove(index)
                adapter.notifyItemRemoved(index)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }
    private fun dialPhone(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(intent)
    }


}