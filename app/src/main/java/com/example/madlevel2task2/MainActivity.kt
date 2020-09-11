package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Init()
    }

    private fun Init() {
        binding.rvQuestions.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionAdapter
        binding.rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))

        questions.add(Question("A val and var are the same", false))
        questions.add(Question("Mobile Application Development grants 12 ECTS", true))
        questions.add(Question("A unit in Kotlin corresponds to a void in Java", false))
        questions.add(Question("In Kotlin when replaces switch operator in Java", true))
        questionAdapter.notifyDataSetChanged()

        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        var callback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT) {
                    if(!questions[position].answer) {
                        questions.removeAt(position)
                        questionAdapter.notifyDataSetChanged()
                    } else {
                        questionAdapter.notifyDataSetChanged()
                        Snackbar.make(rvQuestions, "Error! Check your answer.", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    if(questions[position].answer) {
                        questions.removeAt(position)
                        questionAdapter.notifyDataSetChanged()
                    } else {
                        questionAdapter.notifyDataSetChanged()
                        Snackbar.make(rvQuestions, "Error! Check your answer.", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return ItemTouchHelper(callback)
    }
}