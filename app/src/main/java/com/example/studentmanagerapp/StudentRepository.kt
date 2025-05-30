package com.example.studentmanagerapp

import Student
import android.content.Context

object StudentRepository {
    private lateinit var dbHelper: StudentDatabaseHelper
    fun init(context: Context) {
        dbHelper = StudentDatabaseHelper(context)
        students.clear()
        students.addAll(dbHelper.getAllStudents())
    }

    val students = mutableListOf<Student>()
    fun add(student : Student){
        if(dbHelper.insertStudent(student)){
            students.add(student)
        }
    }
    fun update(idx: Int, newStudent: Student) {
        if (idx in students.indices) {
            val oldId = students[idx].id
            if (dbHelper.updateStudent(oldId, newStudent)) {
                students[idx] = newStudent
            }
        }
    }
    fun remove(i : Int){
        if(i in students.indices && dbHelper.deleteStudent(students[i].id)) students.removeAt(i)

    }


}