package com.android.assignment2

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.fragment_questions.*
import android.widget.RadioGroup



class QuestionsActivity : AppCompatActivity() {

    private var mCurrentScore: Int = 0
    private var mCurrentQuestion: Int = 0

    private val mQuestionArray = arrayOf(
        Questions(R.string.question1, R.string.answer1b, R.string.answer1a, R.string.answer1b, R.string.answer1c, R.string.answer1d),
        Questions(R.string.question2, R.string.answer2d, R.string.answer2a, R.string.answer2b, R.string.answer2c, R.string.answer2d),
        Questions(R.string.question3, R.string.answer3a, R.string.answer4a, R.string.answer3b, R.string.answer3c, R.string.answer3d),
        Questions(R.string.question4, R.string.answer4c, R.string.answer1a, R.string.answer4b, R.string.answer4c, R.string.answer4d)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)


        //display the question fragment if it's not the last question yet
        //NOTE: doesn't work yet, it continues to loop till the last question, not making any new fragment
        addNewFragment()
//        val fm: FragmentManager = supportFragmentManager
//        var fragment: Fragment? = fm.findFragmentById(R.id.fragment_container)

//        if (fragment == null) {
//            addNewFragment(fragment)
////            Log.d(TAG, "Created a fragment")
////            fragment = QuestionFragment.newInstance()
////            val args = Bundle()
////            args.putSerializable("Question", mQuestionArray[mCurrentQuestion])
////            args.putInt("currentquestion", mCurrentQuestion)
////            args.putInt("score", mCurrentScore)
////            fragment.setArguments(args)
////            fm.beginTransaction()
////                .add(R.id.fragment_container, fragment)
////                .commit()
//        }
//        else {
//            Log.d(TAG, "Fragment already exists")
//            Log.d(TAG, "$mCurrentQuestion")
//            //need to pop the fragment off the stack
//        }
    }

    override fun onStart() {
        super.onStart()

        hideNextButton()
        nextButton.setOnClickListener {
            Log.d(TAG, "nextButton.setOnClickListener() called $mCurrentQuestion")
            hideNextButton()
            //if this is the last question, finish the activity
            if (mCurrentQuestion == 3) {
                //finish
                Log.d(TAG, "setScoreResult() called $mCurrentQuestion current score: $mCurrentScore")
                //this works
                setScoreResult(mCurrentScore)
                finish()
            }
            //continue to next question by popping of the previous fragment and creating a new one with the next question
            else {
                Log.d(TAG, "Not 3 yet, current question: $mCurrentQuestion")
                mCurrentQuestion++
                Log.d(TAG, "replaceFragmentStack() called")
                replaceFragment()
            }
        }
    }


    //this works
    //save the current score and pass it back to the main activity
    fun setScoreResult(score: Int) {
        val intent = Intent(this, QuestionsActivity::class.java)
        intent.putExtra(EXTRA_SCORE, score)
        setResult(RESULT_OK, intent)
    }


    //this works
    //shows the next button and checks if the selected option is the correct answer
    fun onRadioButtonClicked(view: View) {
        showNextButton()
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.radio_q1 ->
                    if (checked && isCorrectAnswer(radio_q1.text.toString())) {
                        Log.d(TAG, "${radio_q1.text} is checked, answer: ${isCorrectAnswer(radio_q1.getText().toString())}")
                        mCurrentScore++
                    }
                R.id.radio_q2 ->
                    if (checked && isCorrectAnswer(radio_q2.text.toString())) {
                        Log.d(TAG, "${radio_q2.text} is checked, answer: ${isCorrectAnswer(radio_q2.getText().toString())}")
                        mCurrentScore++
                    }
                R.id.radio_q3 ->
                    if (checked && isCorrectAnswer(radio_q3.text.toString())) {
                        Log.d(TAG, "${radio_q3.text} is checked, answer: ${isCorrectAnswer(radio_q3.getText().toString())}")
                        mCurrentScore++
                    }
                R.id.radio_q4 ->
                    if (checked && isCorrectAnswer(radio_q4.text.toString())) {
                        Log.d(TAG, "${radio_q3.text} is checked, answer: ${isCorrectAnswer(radio_q4.getText().toString())}")
                        mCurrentScore++
                    }
            }
        }
    }

    private fun showNextButton() {
        nextButton.visibility = View.VISIBLE
    }

    private fun hideNextButton() {
        nextButton.visibility = View.INVISIBLE

    }

    private fun isCorrectAnswer(answer: String): Boolean {
        return answer == getString(mQuestionArray[mCurrentQuestion].answer)
    }

    //add a new Fragment onto the stack
    private fun addNewFragment() {
        val fm: FragmentManager = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fragment_container)
        fragment = QuestionFragment.newInstance()
        val args = Bundle()
        args.putSerializable("Question", mQuestionArray[mCurrentQuestion])
        args.putInt("currentquestion", mCurrentQuestion)
        args.putInt("score", mCurrentScore)
        fragment.setArguments(args)
        fm.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    //create a new Fragment with the current question and use it to replace the current Fragment on the stack
    private fun replaceFragment() {
        val fm: FragmentManager = supportFragmentManager
        var fragment = QuestionFragment.newInstance()
        val args = Bundle()
        args.putSerializable("Question", mQuestionArray[mCurrentQuestion])
        args.putInt("currentquestion", mCurrentQuestion)
        args.putInt("score", mCurrentScore)
        fragment.setArguments(args)
        fm.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val EXTRA_SCORE = "com.android.assignment2.score"
        private const val TAG = "QuestionActivity"
    }
}
