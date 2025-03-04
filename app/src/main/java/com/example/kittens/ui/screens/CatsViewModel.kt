package com.example.kittens.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kittens.CatApplication
import com.example.kittens.data.CatsRepository
import com.example.kittens.model.CatByBreedResponseApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface CatUiState {
    data class Success(
        val cats: List<CatByBreedResponseApi>,
    ) : CatUiState

    data object Error : CatUiState

    data object Loading : CatUiState
}

class CatViewModel(
    private val catsRepository: CatsRepository,
) : ViewModel() {
    var catUiState: CatUiState by mutableStateOf(CatUiState.Loading)

    init {
        getCat()
    }

    fun getCat() {
        viewModelScope.launch {
            try {
                val catsByBreed = catsRepository.getAllCatsByBreed()
                Log.d("KittenTag", "Success: $catsByBreed")
                catUiState = CatUiState.Success(catsByBreed)
            } catch (e: IOException) {
                Log.e("KittenTag", "IOException occurred: ${e.message}", e)
                catUiState = CatUiState.Error
            } catch (e: Exception) {
                Log.e("KittenTag", "Unknown error occurred: ${e.message}", e)
                catUiState = CatUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as CatApplication)
                    val catsRepository = application.container.catsRepository
                    CatViewModel(catsRepository = catsRepository)
                }
            }
    }
}
