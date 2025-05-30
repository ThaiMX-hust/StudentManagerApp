package com.example.studentmanagerapp

import Student
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context):
    SQLiteOpenHelper(context,"students.db", null, 1)
{
        companion object{
            const val TABLE_NAME = "students"
            const val COLUMN_ID = "id"
            const val COLUMN_NAME = "name"
            const val COLUMN_EMAIL = "email"
            const val COLUMN_PHONE = "phone"
        }
        override fun onCreate(db: SQLiteDatabase?) {
            val createTable = """
                CREATE TABLE $TABLE_NAME(
                    $COLUMN_ID TEXT PRIMARY KEY,
                    $COLUMN_NAME TEXT,
                    $COLUMN_EMAIL TEXT,
                    $COLUMN_PHONE TEXT
                )
            """.trimIndent()
            db?.execSQL(createTable)
        }
        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
        fun insertStudent(student: Student): Boolean {
            val db = writableDatabase
            val values = android.content.ContentValues().apply {
                put(COLUMN_ID, student.id)
                put(COLUMN_NAME, student.name)
                put(COLUMN_EMAIL, student.email)
                put(COLUMN_PHONE, student.phone)
            }
            return db.insert(TABLE_NAME, null, values) != -1L
        }
        fun updateStudent(oldId: String,student: Student): Boolean {
            val db = writableDatabase
            val values = android.content.ContentValues().apply {
                put(COLUMN_ID, student.id)
                put(COLUMN_NAME, student.name)
                put(COLUMN_EMAIL, student.email)
                put(COLUMN_PHONE, student.phone)
            }
            return db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(oldId)) > 0
        }
        fun deleteStudent(id: String): Boolean {
            val db = writableDatabase
            return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id)) > 0

        }
        fun getAllStudents(): MutableList<Student>{
            val db = readableDatabase
            val students = mutableListOf<Student>()
            val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
            if(cursor.moveToFirst()){
                do{
                    students.add(
                        Student(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)))
                    )
                } while(cursor.moveToNext())
            }

            cursor.close()
            return students
        }
}