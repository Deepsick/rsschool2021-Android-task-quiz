package com.rsschool.quiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.rsschool.quiz.repository.Questions

class MainActivity : AppCompatActivity(),
    QuestionFragment.QuestionFragmentListener, ResultFragment.ResultFragmentListener {
    private val questions = Questions.getQuestions().shuffled()
    private val answers = arrayOfNulls<String>(questions.size)
    private var currentQuestionNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openQuestionFragment()
    }


    private fun openQuestionFragment() {
        val question = questions[currentQuestionNumber - 1]
        val answer = answers.getOrNull(currentQuestionNumber - 1)
        val isLastQuestion = currentQuestionNumber > questions.size
        val fragment =
            QuestionFragment.newInstance(question, answer, currentQuestionNumber, isLastQuestion)
        replaceFragment(fragment)
    }

    private fun openResultFragment(result: Int) {
        val fragment = ResultFragment.newInstance(result)
        replaceFragment(fragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun resetAnswers() {
        answers.forEachIndexed { index, _ ->
            answers[index] = null
        }
    }

    private fun resetQuiz() {
        currentQuestionNumber = 1
        resetAnswers()
    }


    private fun countCorrectAnswers(): Int {
        var count = 0
        answers.forEachIndexed { index, answer ->
            if (questions[index].correctAnswer.equals(answer)) {
                count++
            }
        }

        return count
    }

    private fun getResult(): Int {
        return ((countCorrectAnswers().toDouble() / questions.size) * 100).toInt()
    }

    private fun generateReport(result: Int): String {
        val result = getResult()
        val strQuestions = questions.mapIndexed() { index, question ->
            """
            $index) ${question.text}
            Your answer: ${answers[index]} 
            """.trimIndent()
        }

        return """
Your result: $result %
            
${strQuestions.joinToString("\n")}
        """.trimIndent()
    }

    override fun onSubmit(answer: String) {
        answers[currentQuestionNumber - 1] = answer
        currentQuestionNumber++

        if (currentQuestionNumber > questions.size) {
            val result = getResult()
            openResultFragment(result)
            return
        }

        openQuestionFragment()
    }

    override fun onBackButtonPressed() {
        currentQuestionNumber--
        openQuestionFragment()
    }

    override fun onShareButtonPressed() {
        val text = generateReport(getResult())
        println(text)
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf("some_email@mail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        intent.resolveActivity(packageManager)?.let {
            val shareIntent = Intent.createChooser(intent, null)
            startActivity(shareIntent)
        }
    }

    override fun onBackButtonClicked() {
        resetQuiz()
        openQuestionFragment()
    }

    override fun onCloseButtonPressed() {
        finish()
    }
}