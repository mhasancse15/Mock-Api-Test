package com.mhasancse15.cleanmaster.presentation

import android.os.Bundle

import com.mhasancse15.cleanmaster.common.baseComponent.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import om.mhasancse15.cleanmaster.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun viewBindingLayout(): ActivityMainBinding  = ActivityMainBinding.inflate(layoutInflater)
    override fun initializeView(savedInstanceState: Bundle?) {
    }

}