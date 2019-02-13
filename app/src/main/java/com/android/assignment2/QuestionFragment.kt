package com.android.assignment2

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import kotlinx.android.synthetic.main.fragment_questions.*
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_questions.*
import kotlinx.android.synthetic.main.fragment_questions.view.*




class QuestionFragment : Fragment() {

    private var mQuestion: String =""
    private var mOption1: String =""
    private var mOption2: String =""
    private var mOption3: String =""
    private var mOption4: String =""
    private var mCurrentScore: Int = 0
    private var mCurrentQuestion: Int = 0
    private var mCurrentAnswer: String = ""

    companion object {

        //whenever a new fragment is created, a Question object is passed along with the current score
        fun newInstance(): QuestionFragment {
            return QuestionFragment()
        }

        private const val MAX_QUESTIONS_INDEX: Int = 3
        private const val TAG = "QuestionFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater!!.inflate(R.layout.fragment_questions, container, false)

        //get the Question objects and all related values from Bundle
        val bundle = arguments
        val question = bundle!!.getSerializable("Question") as Questions
        mQuestion = getString(question.textResId)
        mCurrentAnswer = getString(question.answer)
        mOption1 = getString(question.option1)
        mOption2 = getString(question.option2)
        mOption3 = getString(question.option3)
        mOption4 = getString(question.option4)
        mCurrentScore = bundle!!.getInt("score", 0)
        mCurrentQuestion = bundle!!.getInt("currentquestion", 0)


        Log.d(TAG, "Score: $mCurrentScore Question: $mQuestion Answer: $mCurrentAnswer")
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showQuestion()
        showOptions()

    }

    fun showQuestion() {
        questionView.text = mQuestion
    }

    fun showOptions() {
        radio_q1.text = mOption1
        radio_q2.text = mOption2
        radio_q3.text = mOption3
        radio_q4.text = mOption4
    }
}