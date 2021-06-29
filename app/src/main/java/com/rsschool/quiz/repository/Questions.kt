package com.rsschool.quiz.repository

import com.rsschool.quiz.model.Question

object Questions {
    fun getQuestions(): List<Question> {
        return listOf<Question>(
            Question(
                text = "Who wrote the Sinead O`Connor hit Nothing Compares 2?",
                correctAnswer = "Prince",
                answers = listOf(
                    "Michael Jackson",
                    "Cameo",
                    "Rick James",
                    "Pajama Sam",
                    "Prince"
                )
            ),
            Question(
                text = "Which of these musicals won the Tony Award for Best Musical?",
                correctAnswer = "Rent",
                answers = listOf(
                    "The Color Purple",
                    "American Idiot",
                    "Rent",
                    "Newsies",
                    "Freddi Fish"
                )
            ),
            Question(
                text = "Which of these is NOT a Humongous Entertainment game franchise?",
                correctAnswer = "Commander Keen",
                answers = listOf(
                    "Commander Keen",
                    "Pajama Sam",
                    "Putt-Putt",
                    "Freddi Fish",
                    "Hell King"
                )
            ),
            Question(
                text = "In Dota 2, Wraith King was previously known as...",
                correctAnswer = "Skeleton King",
                answers = listOf(
                    "Reaper King",
                    "Skeleton King",
                    "Skull King",
                    "Hell King",
                    "Putt-Putt"
                )
            ),
            Question(
                text = "In the video game Team Fortress which class is able to double jump?",
                correctAnswer = "Scout",
                answers = listOf(
                    "Spy",
                    "Engineer",
                    "Pyro",
                    "Scout",
                    "Doctor"
                )
            ),
            Question(
                text = "What is the name of the City in Saints Row The Third?",
                correctAnswer = "Steelport",
                answers = listOf(
                    "Stilwater",
                    "Carcer",
                    "Liberty",
                    "New-York",
                    "Steelport"
                )
            )
        )
    }
}