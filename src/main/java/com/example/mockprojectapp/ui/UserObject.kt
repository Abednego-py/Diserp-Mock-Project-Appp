package com.example.mockprojectapp.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserObject(
    val image: String,
    val login: String,
    val id: Int,
    val reposUrl: String
) : Parcelable