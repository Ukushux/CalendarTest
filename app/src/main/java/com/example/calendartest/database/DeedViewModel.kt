package com.example.calendartest.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeedViewModel(application: Application) : AndroidViewModel(application) {
    private val _deeds: MutableLiveData<List<DeedItem>> = MutableLiveData(listOf())
    private val repository: DeedRepository

    init {
        val deedDao = DeedDatabase.getInstance(application).deedDao()
        repository = DeedRepository(deedDao)
        viewModelScope.launch(Dispatchers.IO){
            requestDeeds()
        }
    }

    val deeds :LiveData<List<DeedItem>>
        get() = _deeds

    suspend fun requestDeeds(){
            val data = repository.getDeeds()
            viewModelScope.launch(Dispatchers.Main) {
                _deeds.value = data
            }
    }

    fun addDeed(deedItem: DeedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDeed(deedItem)
            requestDeeds()
        }
    }

    fun updateDeed(deedItem: DeedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDeed(deedItem = deedItem)
            requestDeeds()
        }
    }

    fun deleteDeed(deedItem: DeedItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDeed(deedItem = deedItem)
            requestDeeds()
        }
    }

    fun deleteAllDeeds() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllDeeds()
            requestDeeds()
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