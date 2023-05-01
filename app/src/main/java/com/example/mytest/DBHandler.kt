package com.example.mytest

import android.annotation.SuppressLint
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

    fun findUserById(userId: Int):List<User>{
        val selectQuery = "SELECT * FROM UserTable WHERE userId=$userId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadUser(cursor)
    }

    fun findUserByVocation(vocation: Int):List<User>{
        val selectQuery = "SELECT * FROM UserTable WHERE vocation=$vocation"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadUser(cursor)
    }

    fun findUserByEmail(email: String):List<User>{
        val selectQuery = "SELECT * FROM UserTable WHERE userEmail=$email"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadUser(cursor)
    }

    fun findUserByCourse(courseId: String):List<User>{
        val selectQuery = "SELECT * FROM UserTable WHERE userCourse LIKE %$courseId%"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadUser(cursor)
    }

    fun findCourseById(courseId: Int):List<Course>{
        val selectQuery = "SELECT * FROM CourseTable WHERE courseId=$courseId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadCourse(cursor)
    }

    fun findUsersCourse(userId: Int):List<Course>{
        val user = findUserById(userId)
        val courseIdList:List<String> = user[0].relatedCourse.split(",");
        val courseList:ArrayList<Course> = ArrayList<Course>()

        for (element in courseIdList) {
            courseList.add(findCourseById(element.toInt())[0])
        }
        return courseList
    }

    fun countUsersCorrectAnswer(userId: Int):Int{
        val user = findUserById(userId)
        val answerIdList:List<String> = user[0].relatedAnswer.split(",");
        var count = 0

        for (element in answerIdList) {
            if (findAnswerById(element.toInt())[0].isCorrect == 1)
                count++
        }
        return count
    }

    fun findActivityById(activityId: Int):List<Activity>{
        val selectQuery = "SELECT * FROM ActivityTable WHERE activityId=$activityId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadActivity(cursor)
    }
    fun findCoursesActivity(courseId: Int):List<Activity>{
        val course = findCourseById(courseId)
        val activityIdList:List<String> = course[0].relatedActivity.split(",");
        val activityList:ArrayList<Activity> = ArrayList<Activity>()

        for (element in activityIdList) {
            activityList.add(findActivityById(element.toInt())[0])
        }
        return activityList
    }

    fun findQuestionById(quesitonId: Int):List<Question>{
        val selectQuery = "SELECT * FROM QuestionTable WHERE questionId=$quesitonId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadQuestion(cursor)
    }
    fun findActivitysQuestion(activityId: Int):List<Question>{
        val activity = findActivityById(activityId)
        val questionIdList:List<String> = activity[0].containedQuestion.split(",");
        val questionList:ArrayList<Question> = ArrayList<Question>()

        for (element in questionIdList) {
            questionList.add(findQuestionById(element.toInt())[0])
        }
        return questionList
    }

    fun countActivitysQuestion(activityId: Int):Int{
        val activity = findActivityById(activityId)
        val questionIdList:List<String> = activity[0].containedQuestion.split(",");
        return questionIdList.size
    }

    fun findAnswerById(answerId: Int):List<Answer>{
        val selectQuery = "SELECT * FROM AnswerTable WHERE answerId=$answerId"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        db.close()
        return loadAnswer(cursor)
    }
    fun findAnswersQuestion(answerId: Int):List<Question>{
        val answer = findAnswerById(answerId)
        val questionId:Int = answer[0].relatedQuestionId;
        val questionList:ArrayList<Question> = ArrayList<Question>()

        questionList.add(findQuestionById(questionId)[0])
        return questionList
    }

    @SuppressLint("Range")
    fun loadUser(cursor: Cursor):List<User>  {
        val userList:ArrayList<User> = ArrayList<User>()
        var userId: Int
        var vocation: Int
        var userName: String
        var password: String
        var userEmail: String
        var relatedCourse: String
        var relatedAnswer: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("userId"))
                vocation = cursor.getInt(cursor.getColumnIndex("vocation"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                password = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                relatedCourse = cursor.getString(cursor.getColumnIndex("name"))
                relatedAnswer = cursor.getString(cursor.getColumnIndex("name"))
                val user= User(userId = userId, vocation = vocation, userName = userName, password = password,
                    userEmail = userEmail, relatedCourse = relatedCourse, relatedAnswer = relatedAnswer)
                userList.add(user)
            } while (cursor.moveToNext())
        }
        return userList
    }

    @SuppressLint("Range")
    fun loadCourse(cursor: Cursor):List<Course>  {
        val courseList:ArrayList<Course> = ArrayList<Course>()
        var courseId: Int
        var courseName: String
        var courseDescr: String
        var relatedActivity: String
        if (cursor.moveToFirst()) {
            do {
                courseId = cursor.getInt(cursor.getColumnIndex("courseId"))
                courseName = cursor.getString(cursor.getColumnIndex("courseName"))
                courseDescr = cursor.getString(cursor.getColumnIndex("courseDescr"))
                relatedActivity = cursor.getString(cursor.getColumnIndex("relatedActivity"))
                val course= Course(courseId = courseId, courseName = courseName,
                    courseDescr = courseDescr, relatedActivity = relatedActivity)
                courseList.add(course)
            } while (cursor.moveToNext())
        }
        return courseList
    }

    @SuppressLint("Range")
    fun loadActivity(cursor: Cursor):List<Activity>  {
        val activityList:ArrayList<Activity> = ArrayList<Activity>()
        var activityId: Int
        var activityName: String
        var containedQuestion: String
        var availableTill: String
        if (cursor.moveToFirst()) {
            do {
                activityId = cursor.getInt(cursor.getColumnIndex("activityId"))
                activityName = cursor.getString(cursor.getColumnIndex("activityName"))
                containedQuestion = cursor.getString(cursor.getColumnIndex("containedQuestion"))
                availableTill = cursor.getString(cursor.getColumnIndex("availableTill"))
                val activity= Activity(activityId = activityId, activityName = activityName,
                    containedQuestion = containedQuestion, availableTill = availableTill)
                activityList.add(activity)
            } while (cursor.moveToNext())
        }
        return activityList
    }

    @SuppressLint("Range")
    fun loadQuestion(cursor: Cursor):List<Question>  {
        val questionList:ArrayList<Question> = ArrayList<Question>()
        var questionId: Int
        var questionText: String
        var correctAnswer: String
        if (cursor.moveToFirst()) {
            do {
                questionId = cursor.getInt(cursor.getColumnIndex("questionId"))
                questionText = cursor.getString(cursor.getColumnIndex("questionText"))
                correctAnswer = cursor.getString(cursor.getColumnIndex("correctAnswer"))
                val question= Question(questionId = questionId, questionText = questionText, correctAnswer = correctAnswer)
                questionList.add(question)
            } while (cursor.moveToNext())
        }
        return questionList
    }

    @SuppressLint("Range")
    fun loadAnswer(cursor: Cursor):List<Answer>  {
        val answerList:ArrayList<Answer> = ArrayList<Answer>()
        var answerId: Int
        var relatedQuestionId: Int
        var answerText: String
        var isCorrect: Int
        if (cursor.moveToFirst()) {
            do {
                answerId = cursor.getInt(cursor.getColumnIndex("answerId"))
                relatedQuestionId = cursor.getInt(cursor.getColumnIndex("relatedQuestionId"))
                answerText = cursor.getString(cursor.getColumnIndex("answerText"))
                isCorrect = cursor.getInt(cursor.getColumnIndex("isCorrect"))
                val answer= Answer(answerId = answerId, relatedQuestionId = relatedQuestionId,
                    answerText = answerText, isCorrect = isCorrect)
                answerList.add(answer)
            } while (cursor.moveToNext())
        }
        return answerList
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