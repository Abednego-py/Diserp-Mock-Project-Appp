package com.example.mockprojectapp.retrofit

data class RepoResponseX(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)