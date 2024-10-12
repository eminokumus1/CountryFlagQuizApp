package com.eminokumus.udemyquizapp.quiz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.eminokumus.udemyquizapp.Constants
import com.eminokumus.udemyquizapp.R
import com.eminokumus.udemyquizapp.databinding.ActivityQuizQuestionsBinding
import com.eminokumus.udemyquizapp.result.ResultActivity
import com.eminokumus.udemyquizapp.vo.Question

class QuizQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizQuestionsBinding
    private val viewModel: QuizQuestionsViewModel by viewModels()

    private val optionViews = arrayListOf<TextView>()

    private var username: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(Constants.USER_NAME)

        addOptionsToList()


        viewModel.currentQuestion.observe(this) { newQuestion ->
            setUIComponents(newQuestion)
        }

        setOnClickListeners()


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

    private fun setOnClickListeners() {
        setSubmitButtonOnClickListener()
        setNextQuestionButtonOnClickListener()
        setOptionsOnClickListener()
        setFinishButtonOnClickListener()
    }

    private fun setSubmitButtonOnClickListener() {
        binding.submitButton.setOnClickListener {
            showCorrectAndWrongAnswer()
            if (viewModel.getCurrentPosition() == viewModel.getQuestionList().size) {
                binding.finishButton.visibility = View.VISIBLE
            } else {
                binding.nextQuestionButton.visibility = View.VISIBLE
            }
            it.visibility = View.GONE
            viewModel.selectAnswer(0)
        }
    }

    private fun setNextQuestionButtonOnClickListener() {
        binding.nextQuestionButton.setOnClickListener {
            viewModel.updateQuestion()
            resetOptionsPropertiesToDefault(null)
            binding.submitButton.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
    }

    private fun setFinishButtonOnClickListener() {
        binding.finishButton.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(Constants.USER_NAME, username)
            intent.putExtra(Constants.SCORE, viewModel.getScore())
            intent.putExtra(Constants.TOTAL_QUESTIONS, viewModel.getQuestionList().size)
            startActivity(intent)
            finish()
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
        for (optionView in optionViews) {
            if (optionView == viewToExclude) {
                continue
            }
            optionView.setBackgroundResource(R.drawable.default_option_border_bg)
            optionView.setTextColor(Color.parseColor("#363A43"))
            optionView.typeface = Typeface.DEFAULT


        }
    }

    private fun addOptionsToList() {
        optionViews.add(binding.firstOptionText)
        optionViews.add(binding.secondOptionText)
        optionViews.add(binding.thirdOptionText)
        optionViews.add(binding.fourthOptionText)
    }

    private fun setCorrectBackground(view: TextView?) {
        view?.setBackgroundResource(R.drawable.correct_option_border_bg)
    }

    private fun setWrongBackground(view: TextView?) {
        view?.setBackgroundResource(R.drawable.wrong_option_border_bg)
    }

    private fun showCorrectAndWrongAnswer() {
        val selectedAnswerView = findSelectedAnswerView()
        val correctAnswerView = findCorrectAnswerView()
        if (viewModel.isAnswerCorrect()) {
            setCorrectBackground(selectedAnswerView)
            viewModel.increaseScore()
        } else {
            setCorrectBackground(correctAnswerView)
            setWrongBackground(selectedAnswerView)
        }
    }

    private fun findCorrectAnswerView(): TextView {
        return when (viewModel.currentQuestion.value?.correctAnswer) {
            1 -> binding.firstOptionText
            2 -> binding.secondOptionText
            3 -> binding.thirdOptionText
            4 -> binding.fourthOptionText
            else -> throw Exception("Unknown answer")
        }
    }

    private fun findSelectedAnswerView(): TextView? {
        return when (viewModel.getSelectedAnswer()) {
            1 -> binding.firstOptionText
            2 -> binding.secondOptionText
            3 -> binding.thirdOptionText
            4 -> binding.fourthOptionText
            else -> null
        }
    }
}