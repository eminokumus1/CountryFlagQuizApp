package com.eminokumus.udemyquizapp.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.udemyquizapp.Constants
import com.eminokumus.udemyquizapp.vo.Question

class QuizQuestionsViewModel: ViewModel() {

    private val questionList = Constants.getQuestions()

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question> get() = _currentQuestion

    private var currentPosition = 1

    init {
        _currentQuestion.value = questionList[0]
    }

    fun updateQuestion(){
        currentPosition++
        _currentQuestion.value = questionList[currentPosition-1]
    }

    fun getCurrentPosition(): Int{
        return currentPosition
    }

}