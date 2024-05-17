package com.mhasancse15.task.presentation

import android.os.Bundle

import com.mhasancse15.task.common.baseComponent.BaseActivity
import com.mhasancse15.task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun viewBindingLayout(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initializeView(savedInstanceState: Bundle?) {
    }
}