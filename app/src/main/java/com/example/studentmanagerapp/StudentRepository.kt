package com.example.studentmanagerapp

import Student

object StudentRepository {
    val students = mutableListOf<Student>()
    fun add(student : Student){
        students.add(student)
    }
    fun update(i: Int, student: Student){
        if(i in students.indices) students[i] = student
    }
    fun remove(i : Int){
        if(i in students.indices) students.removeAt(i)

    }


}