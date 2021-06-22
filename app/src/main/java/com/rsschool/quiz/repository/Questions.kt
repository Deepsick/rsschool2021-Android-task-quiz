package com.rsschool.quiz.repository

import com.rsschool.quiz.model.Question

object Questions {
    fun getQuestions(): List<Question> {
        return listOf<Question>(
            Question(
                text = "Who wrote the Sinead O`Connor hit Nothing Compares 2?",
                correctAnswer = "Prince",
                incorrectAnswers = listOf(
                    "Michael Jackson",
                    "Cameo",
                    "Rick James",
                    "Pajama Sam"
                )
            ),
            Question(
                text = "Which of these musicals won the Tony Award for Best Musical?",
                correctAnswer = "Rent",
                incorrectAnswers = listOf(
                    "The Color Purple",
                    "American Idiot",
                    "Newsies",
                    "Freddi Fish"
                )
            ),
            Question(
                text = "Which of these is NOT a Humongous Entertainment game franchise?",
                correctAnswer = "Commander Keen",
                incorrectAnswers = listOf(
                    "Pajama Sam",
                    "Putt-Putt",
                    "Freddi Fish",
                    "Hell King"
                )
            ),
            Question(
                text = "In Dota 2, Wraith King was previously known as...",
                correctAnswer = "Skeleton King",
                incorrectAnswers = listOf(
                    "Reaper King",
                    "Skull King",
                    "Hell King",
                    "Putt-Putt"
                )
            ),
            Question(
                text = "In the video game Team Fortress which class is able to double jump?",
                correctAnswer = "Scout",
                incorrectAnswers = listOf(
                    "Spy",
                    "Engineer",
                    "Pyro",
                    "Doctor"
                )
            ),
            Question(
                text = "What is the name of the City in Saints Row The Third?",
                correctAnswer = "Steelport",
                incorrectAnswers = listOf(
                    "Stilwater",
                    "Carcer",
                    "Liberty",
                    "New-York"
                )
            )
        )
    }
}