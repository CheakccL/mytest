package com.example.mytest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "EduSupDatabase"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        var createTable = ("CREATE TABLE UserTable(" +
                "userId INTEGER PRIMARY KEY," +
                "vocation INTEGER," +
                "username TEXT," +
                "username TEXT," +
                "password TEXT," +
                "userEmail TEXT," +
                "relatedCourse TEXT," +
                "relatedAnswer TEXT)")
        db?.execSQL(createTable)

        createTable = ("CREATE TABLE CourseTable(" +
                "courseId INTEGER PRIMARY KEY," +
                "courseName TEXT," +
                "courseDescr TEXT," +
                "relatedActivity TEXT)")
        db?.execSQL(createTable)

        createTable = ("CREATE TABLE ActivityTable(" +
                "activityId INTEGER PRIMARY KEY," +
                "activityName TEXT," +
                "containedQuestion TEXT," +
                "availableTill TEXT)")
        db?.execSQL(createTable)

        createTable = ("CREATE TABLE QuestionTable(" +
                "questionId INTEGER PRIMARY KEY," +
                "questionText TEXT," +
                "correctAnswer TEXT)")
        db?.execSQL(createTable)

        createTable = ("CREATE TABLE AnswerTable(" +
                "answerId INTEGER PRIMARY KEY," +
                "relatedQuestionId INTEGER," +
                "answerText TEXT," +
                "isCorrect BOOLEAN)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    fun addUser(user: User):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("userId", user.userId)
        contentValues.put("vocation", user.vocation)
        contentValues.put("userName", user.userName)
        contentValues.put("password", user.password)
        contentValues.put("userEmail", user.userEmail)
        contentValues.put("relatedCourse", user.relatedCourse)
        contentValues.put("relatedAnswer", user.relatedAnswer)
        // Inserting Row
        val success = db.insert("UserTable", null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addCourse(course: Course):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("courseId", course.courseId)
        contentValues.put("courseName", course.courseName)
        contentValues.put("courseDescr", course.courseDescr)
        contentValues.put("relatedActivity", course.relatedActivity)
        // Inserting Row
        val success = db.insert("CourseTable", null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addActivity(activity: Activity):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("activityId", activity.activityId)
        contentValues.put("activityName", activity.activityName)
        contentValues.put("containedQuestion", activity.containedQuestion)
        contentValues.put("availableTill", activity.availableTill)
        // Inserting Row
        val success = db.insert("ActivityTable", null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addQuestion(question: Question):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("questionId", question.questionId)
        contentValues.put("questionText", question.questionText)
        contentValues.put("correctAnswer", question.correctAnswer)
        // Inserting Row
        val success = db.insert("QuestionTable", null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addAnswer(answer: Answer):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("answerId", answer.answerId)
        contentValues.put("relatedQuestionId", answer.relatedQuestionId)
        contentValues.put("answerText", answer.answerText)
        contentValues.put("isCorrect", answer.isCorrect)
        // Inserting Row
        val success = db.insert("AnswerTable", null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read data
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val emp= EmpModelClass(userId = userId, userName = userName, userEmail = userEmail)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    //method to update data
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL,emp.userEmail ) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}