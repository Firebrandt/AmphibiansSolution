/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianApiService
import com.example.amphibians.network.amphibAPI
import kotlinx.coroutines.launch
import java.lang.Exception

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {

    private val _status = MutableLiveData<AmphibianApiStatus>(AmphibianApiStatus.LOADING)
    val status : LiveData<AmphibianApiStatus> = _status

    private val _amphibians = MutableLiveData<List<Amphibian>>()
    val amphibians : LiveData<List<Amphibian>> = _amphibians

    private val _currentAmphibian = MutableLiveData<Amphibian>()
    val currentAmphibian : LiveData<Amphibian> = _currentAmphibian

    fun getAmphibiansList() {
        _status.value = AmphibianApiStatus.LOADING
        try {
            viewModelScope.launch {
                _amphibians.value = amphibAPI.retrofitService.getAmphibiansList()
            }
        } catch (e: Exception) {
            _status.value = AmphibianApiStatus.ERROR
        }
        _status.value = AmphibianApiStatus.DONE
    }

    fun onAmphibianClicked(amphibian: Amphibian) {
        _currentAmphibian.value = amphibian
    }
}
