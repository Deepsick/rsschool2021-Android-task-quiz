package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.core.view.get
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.model.Question
import java.lang.IllegalArgumentException
import kotlin.random.Random

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var listener: QuestionFragmentListener? = null

    interface QuestionFragmentListener {
        fun onSubmit(answer: String)
        fun onBackButtonPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val question = it.getParcelable<Question>(QUESTION) ?: return
            val questionNumber = it.getInt(QUESTION_NUMBER)
            val answer = it.getString(ANSWER)
            val isLastQuestion = it.getBoolean(IS_LAST_QUESTION)
            updateView(question, answer, questionNumber, isLastQuestion)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        inflater.context.setTheme(getThemeId())
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getThemeId(): Int {
        return when (Random.nextInt(1, 5)) {
            1 -> R.style.Theme_Quiz_First
            2 -> R.style.Theme_Quiz_Second
            3 -> R.style.Theme_Quiz_Third
            4 -> R.style.Theme_Quiz_Fourth
            else -> throw IllegalArgumentException("Incorrect theme number")
        }
    }

    private fun updateView(
        question: Question,
        answer: String?,
        questionNumber: Int,
        isLastQuestion: Boolean
    ) {
        updateQuestion(question, answer, isLastQuestion)
        updateToolbar(questionNumber)
        if (answer == null) {
            enableSubmit(false)
        }
        setListeners(questionNumber)
    }

    private fun enableSubmit(isEnabled: Boolean) {
        binding.nextButton.isEnabled = isEnabled
    }

    private fun updateQuestion(question: Question, answer: String?, isLastQuestion: Boolean) {
        if (question.incorrectAnswers == null || question.correctAnswer == null) {
            return
        }
        val options = mutableListOf<String>()
        options.apply {
            addAll(question.incorrectAnswers)
            add(question.correctAnswer)
            shuffle()
        }
        binding.question.text = question.text
        options.forEachIndexed { index, option ->
            val radioButton = binding.radioGroup[index] as RadioButton
            radioButton.tag = option
            radioButton.text = option
        }
        answer?.let {
            findRadio(it).isChecked = true
        }
        if (isLastQuestion) {
            binding.nextButton.text = getString(R.string.last_question_button_name)
        }
    }

    private fun findRadio(answer: String): RadioButton {
        return (binding.radioGroup.children.find {
            val radio = it as RadioButton
            radio.text == answer
        } as RadioButton)
    }

    private fun isFirstQuestion(questionNumber: Int): Boolean {
        return questionNumber == 1
    }

    private fun updateToolbar(questionNumber: Int) {
        binding.toolbar.title = "Question $questionNumber"
        if (isFirstQuestion(questionNumber)) {
            binding.toolbar.navigationIcon = null
            binding.previousButton.isEnabled = false
        }
    }

    private fun setListeners(questionNumber: Int) {
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            enableSubmit(true)
        }
        binding.previousButton.setOnClickListener {
            if (isFirstQuestion(questionNumber)) {
                return@setOnClickListener
            }
            listener?.onBackButtonPressed()
        }

        binding.toolbar.setOnClickListener {
            if (isFirstQuestion(questionNumber)) {
                return@setOnClickListener
            }
            listener?.onBackButtonPressed()
        }

        binding.nextButton.setOnClickListener {
            listener?.onSubmit(getCheckedAnswer())
            binding.radioGroup.clearCheck()
        }
    }

    private fun getCheckedAnswer(): String {
        val answer = (binding.radioGroup.children.find {
            val radio = it as RadioButton
            radio.isChecked
        } as RadioButton).text

        return answer.toString()
    }

    private fun removeListeners() {
        binding.previousButton.setOnClickListener(null)
        binding.nextButton.setOnClickListener(null)
        binding.toolbar.setOnClickListener(null)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as QuestionFragmentListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeListeners()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(
            question: Parcelable,
            answer: String?,
            questionNumber: Int,
            isLastQuestion: Boolean
        ): QuestionFragment {
            val fragment = QuestionFragment()
            fragment.apply {
                arguments = Bundle().apply {
                    putParcelable(QUESTION, question)
                    putString(ANSWER, answer)
                    putInt(QUESTION_NUMBER, questionNumber)
                    putBoolean(IS_LAST_QUESTION, isLastQuestion)
                }
            }
            return fragment
        }

        private const val QUESTION = "QUESTION"
        private const val ANSWER = "ANSWER"
        private const val IS_LAST_QUESTION = "IS_LAST_QUESTION"
        private const val QUESTION_NUMBER = "QUESTION_NUMBER"
    }
}