package com.example.calendartest.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeedViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<DeedItem>>
    private val repository: DeedRepository

    init {
        val deedDao = DeedDatabase.getInstance(application).deedDao()
        repository = DeedRepository(deedDao)
        readAllData = repository.readAllData
    }

    fun addDeed(deedItem: DeedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDeed(deedItem)
        }
    }

    fun updateDeed(deedItem: DeedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDeed(deedItem = deedItem)
        }
    }

    fun deleteDeed(deedItem: DeedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDeed(deedItem = deedItem)
        }
    }

    fun deleteAllDeeds() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllDeeds()
        }
    }
}

class DeedViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DeedViewModel::class.java)) {
            return DeedViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}