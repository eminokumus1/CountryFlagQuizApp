package com.eminokumus.udemyquizapp.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eminokumus.udemyquizapp.Constants
import com.eminokumus.udemyquizapp.R
import com.eminokumus.udemyquizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private var username : String? = null
    private var totalQuestions : Int? = null
    private var score : Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getValuesFromIntent()

        setUI()

    }

    private fun getValuesFromIntent() {
        username = intent.getStringExtra(Constants.USER_NAME)
        totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        score = intent.getIntExtra(Constants.SCORE, 0)
    }

    private fun setUI() {
        binding.run {
            nameText.text = username
            scoreText.text = "Your score is $score out of $totalQuestions"
        }
    }

}