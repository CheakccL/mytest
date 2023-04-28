package com.example.mytest

class Answer (val answerId: Int,
              val relatedQuestionId: Int,
              val answerText: String,
              // 0: not marked, 1: correct, 2: wrong
              val isCorrect: Int) {
}