package com.example.mockprojectapp.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var loginName: String,
    var imageUrl: String,
    var profileId: Int,
    var reposUrl: String
) : Parcelable