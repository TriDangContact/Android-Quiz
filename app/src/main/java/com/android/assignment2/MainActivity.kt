package com.android.assignment2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private var mScore: Int? = -1
    private var mFirstName: String = ""
    private var mLastName: String = ""
    private var mNickName: String = ""
    private var mAge: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if user info already exists in internal storage
        if (fileExist(FILENAME)) {
            retrieveUserInfo()
            updateUserInfoView()
        }

        doneButton.setOnClickListener{
            saveUserInfo()
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
                if (mScore != -1) {
                    scoreView.text = "Score: ${mScore.toString()}"
                }
            }
            else {
                Log.d(TAG, "RESULT_CANCELED returned")
            }
        }
    }

    //update the View with current data
    private fun updateUserInfoView() {
        firstNameView.setText(mFirstName)
        lastNameView.setText(mLastName)
        nickNameView.setText(mNickName)
        ageView.setText(mAge.toString())
    }


    //reads data from internal file storage and retrieve it
    private fun retrieveUserInfo() {
        val context = application
        var fileIS = context.openFileInput(FILENAME)
        var inputSR = InputStreamReader(fileIS)
        var bufferedReader = BufferedReader(inputSR)
        mFirstName = bufferedReader.readLine()
        mLastName = bufferedReader.readLine()
        mNickName = bufferedReader.readLine()
        mAge = bufferedReader.readLine().toInt()

        Log.d(TAG, "$mFirstName, $mLastName, $mNickName, $mAge")
    }

    //save data into internal file storage
    private fun saveUserInfo() {
        mFirstName = firstNameView.text.toString()
        mLastName = lastNameView.text.toString()
        mNickName = nickNameView.text.toString()
        mAge = ageView.text.toString().toInt()
        Log.d(TAG, "First Name: $mFirstName Last Name: $mLastName Nickname: $mNickName Age: $mAge")

        val context = application
        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).use {
            it.write("$mFirstName\n".toByteArray())
            it.write("$mLastName\n".toByteArray())
            it.write("$mNickName\n".toByteArray())
            it.write("$mAge\n".toByteArray())
        }
        Toast.makeText(this, R.string.infoSaved_toast, Toast.LENGTH_SHORT).show()
    }

    //check if an internal file exists already
    fun fileExist(fname: String): Boolean {
        val file = baseContext.getFileStreamPath(fname)
        return file.exists()
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_QUESTIONS = 0
        private const val EXTRA_SCORE = "com.android.assignment2.score"
        private const val FILENAME = "user_info.txt"
    }
}
