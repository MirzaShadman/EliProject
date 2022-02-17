package com.demo.eliproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import callbacks.ProductSelectedListener
import pojo.Product
import viewmodel.MainSharedViewModel
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.Field

class MainActivity : AppCompatActivity(), ProductSelectedListener {
    
    private lateinit var model: MainSharedViewModel
    private lateinit var fragmManager: FragmentManager
    
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        
        model = ViewModelProvider(this).get(MainSharedViewModel::class.java)
        
        fragmManager = this.supportFragmentManager
        
    }
    
    override fun selectProduct(product: Product) {
        model.setSelectedProducts(product)
    }
    
    override fun removeProduct(product: Product) {
        model.removeProduct(product)
    }
    
}