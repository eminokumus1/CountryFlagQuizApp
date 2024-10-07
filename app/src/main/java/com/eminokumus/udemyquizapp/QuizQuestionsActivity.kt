package com.eminokumus.udemyquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.udemyquizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionList = Constants.getQuestions()
    }
}