package com.eminokumus.udemyquizapp.quiz

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.eminokumus.udemyquizapp.R
import com.eminokumus.udemyquizapp.databinding.ActivityQuizQuestionsBinding
import com.eminokumus.udemyquizapp.vo.Question

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionsBinding
    private val viewModel: QuizQuestionsViewModel by viewModels()

    private val optionViews = arrayListOf<TextView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addOptionsToList()


        viewModel.currentQuestion.observe(this) { newQuestion ->
            setUIComponents(newQuestion)
        }

        setSubmitButtonOnClickListener()
        setOptionsOnClickListener()


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

    private fun setSubmitButtonOnClickListener() {
        binding.submitButton.setOnClickListener {
            viewModel.updateQuestion()
            resetOptionsPropertiesToDefault(null)
        }
    }

    private fun setOptionsOnClickListener() {
        binding.run {
            firstOptionText.setOnClickListener {
                setSelectedOptionProperties(it as TextView)
                resetOptionsPropertiesToDefault(firstOptionText)
                viewModel.selectAnswer(1)
            }
            secondOptionText.setOnClickListener {
                setSelectedOptionProperties(it as TextView)
                resetOptionsPropertiesToDefault(secondOptionText)
                viewModel.selectAnswer(2)
            }
            thirdOptionText.setOnClickListener {
                setSelectedOptionProperties(it as TextView)
                resetOptionsPropertiesToDefault(thirdOptionText)
                viewModel.selectAnswer(3)
            }
            fourthOptionText.setOnClickListener {
                setSelectedOptionProperties(it as TextView)
                resetOptionsPropertiesToDefault(fourthOptionText)
                viewModel.selectAnswer(4)
            }
        }
    }


    private fun setSelectedOptionProperties(view: TextView) {
        view.setBackgroundResource(R.drawable.selected_option_border_bg)
        view.setTextColor(Color.parseColor("#363A43"))
        view.setTypeface(view.typeface, Typeface.BOLD)
    }

    private fun resetOptionsPropertiesToDefault(viewToExclude: TextView?) {
        for (optionView in optionViews){
            if (optionView == viewToExclude){
                continue
            }
            optionView.setBackgroundResource(R.drawable.default_option_border_bg)
            optionView.setTextColor(Color.parseColor("#363A43"))
            optionView.typeface = Typeface.DEFAULT


        }
    }

    private fun addOptionsToList(){
        optionViews.add(binding.firstOptionText)
        optionViews.add(binding.secondOptionText)
        optionViews.add(binding.thirdOptionText)
        optionViews.add(binding.fourthOptionText)
    }
}