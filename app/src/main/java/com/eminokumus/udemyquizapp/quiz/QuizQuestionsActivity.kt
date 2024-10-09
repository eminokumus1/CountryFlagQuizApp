package com.eminokumus.udemyquizapp.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.eminokumus.udemyquizapp.databinding.ActivityQuizQuestionsBinding
import com.eminokumus.udemyquizapp.vo.Question

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionsBinding
    private val viewModel: QuizQuestionsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.currentQuestion.observe(this){newQuestion->
            setUIComponents(newQuestion)
        }

        setSubmitButtonOnClickListener()


    }

    private fun setUIComponents(newQuestion: Question) {
        binding.run {
            progressBar.progress = viewModel.getCurrentPosition()
            progressText.text = "${viewModel.getCurrentPosition()}/${binding.progressBar.max}"
            questionText.text = newQuestion.question
            questionImage.setImageResource(newQuestion.image ?: 0)
            firstOptionText.text = newQuestion.optionOne
            secondOptionText.text = newQuestion.optionTwo
            thirdOptionText.text = newQuestion.optionThree
            fourthOptionText.text = newQuestion.optionFour
        }
    }

    private fun setSubmitButtonOnClickListener(){
        binding.submitButton.setOnClickListener{
            viewModel.updateQuestion()
        }
    }
}