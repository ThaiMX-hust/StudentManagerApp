package com.example.studentmanagerapp

import Student
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val students : MutableList<Student>,
    private val onUpdate : (Int) -> Unit,
    private val onDelete : (Int) -> Unit,
    private val onCall: (String) -> Unit,
    private val onEmail : (String) -> Unit
): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvId : TextView = itemView.findViewById(R.id.tvId)
        init{
            itemView.setOnLongClickListener {
                showPopup(it, adapterPosition)
                true
            }
        }
        private fun showPopup(view: View, pos: Int){
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.menu_student)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.action_update -> onUpdate(pos)
                    R.id.action_delete -> onDelete(pos)
                    R.id.action_call -> onCall(students[pos].phone)
                    R.id.action_email -> onEmail(students[pos].email)

                }
                true
                }
            popup.show()
        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.name
        holder.tvId.text = student.id
    }
    override fun getItemCount(): Int = students.size




}