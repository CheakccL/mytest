package com.example.mytest

class User (var userId: Int,
            // 1 for Educator, 2 for Learner
            val vocation: Int,
            val userName: String,
            val password: String,
            val userEmail: String,
            val relatedCourse: String,
            val relatedAnswer: String) {
}