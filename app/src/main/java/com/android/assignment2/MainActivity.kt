package com.android.assignment2

import android.app.Activity
import android.content.Intent
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mScore: Int? = -1
    private var mFirstName: String = ""
    private var mLastName: String = ""
    private var mNickName: String = ""
    private var mAge: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        doneButton.setOnClickListener{
            //user info is saved permanently
        }

        quizButton.setOnClickListener{
            //takes user to quiz activity
            val intent = Intent(this@MainActivity, QuestionsActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_QUESTIONS)

        }
        Log.d(TAG, "Current Score=$mScore")
    }

    //this works
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //check which Activity its returning from
        if (requestCode == REQUEST_CODE_QUESTIONS) {
            //check if everything went well
            if (resultCode == RESULT_OK) {
                mScore = data?.getIntExtra(EXTRA_SCORE, 0)
                Log.d(TAG, "onActivityResult called, requestCode=$requestCode, resultCode=$resultCode, score=$mScore")
            }
        }
    }



    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_QUESTIONS = 0
        private const val EXTRA_SCORE = "com.android.assignment2.score"
    }
}
