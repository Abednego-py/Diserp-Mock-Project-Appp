package com.example.mockprojectapp.model

import android.app.Application
import androidx.room.Room
import com.example.mockprojectapp.db.ProjectDatabase

class MainApp : Application() {
    lateinit var database: ProjectDatabase

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            ProjectDatabase::class.java, "user_db"
        )
            .build()
    }
}