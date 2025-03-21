package com.example.myfootballcollection.ui.screens.auth.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfootballcollection.domain.error.Result
import com.example.myfootballcollection.domain.model.football.Team
import com.example.myfootballcollection.domain.useCase.football.SearchTeamsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class CreateScreenViewModel(
    private val searchTeamsUseCase: SearchTeamsUseCase
) : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _teams = MutableStateFlow(emptyList<Team>())
    @OptIn(FlowPreview::class)
    val teams = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_teams) { text, persons ->
            if(text.length < 3) {
                persons
            } else {
                val result = searchTeamsUseCase(text)
                if (result is Result.Success) {
                    result.data
                } else {
                    emptyList()
                }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _teams.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}