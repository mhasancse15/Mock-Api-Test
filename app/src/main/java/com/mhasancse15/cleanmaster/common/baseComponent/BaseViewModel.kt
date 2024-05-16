package com.mhasancse15.cleanmaster.common.baseComponent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(){
    fun execute(job: suspend () -> Unit){
        viewModelScope.launch {
            job.invoke()
        }
    }
}