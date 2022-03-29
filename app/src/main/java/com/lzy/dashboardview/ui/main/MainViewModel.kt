package com.lzy.dashboardview.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var list : List<String> = listOf("hello","kotlin")

    fun getElement(pos:Int) : String {
        return list.get(pos)
    }


    fun getList() :String {
        val val1 = getElement(0)
        val val2 = getElement(1)
        return ("$val1 $val2")
    }

}