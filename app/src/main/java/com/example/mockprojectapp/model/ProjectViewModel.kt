package com.example.mockprojectapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mockprojectapp.db.ProjectDao
import com.example.mockprojectapp.db.ProjectEntity
import kotlinx.coroutines.launch

class ProjectViewModel(private val itemDao: ProjectDao) : ViewModel() {

    private fun insertItem(ent: ProjectEntity) {
        viewModelScope.launch {
            itemDao.create(ent)
        }
    }

    private fun getNewItemEntry(
        login: String,
        profileId: Int,
        imageUrl: String,
        reposUrl: String
    ): ProjectEntity {
        return ProjectEntity(
            loginName = login,
            profileId = profileId,
            imageUrl = imageUrl,
            reposUrl = reposUrl
        )
    }

    fun addNewItem(login: String, profileId: Int, imageUrl: String, reposUrl: String) {
        val newItem = getNewItemEntry(login, profileId, imageUrl, reposUrl)
        insertItem(newItem)
    }

}

class ProjectViewModelFactory(private val itemDao: ProjectDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProjectViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}