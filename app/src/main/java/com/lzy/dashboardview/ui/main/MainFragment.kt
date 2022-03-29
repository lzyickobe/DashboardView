package com.lzy.dashboardview.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.lzy.dashboardview.R
import com.lzy.dashboardview.view.DashboardView

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    lateinit var text: TextView
    lateinit var message:TextView
    lateinit var btn1:Button
    lateinit var btn2:Button
    lateinit var btn3:Button
    lateinit var btn4:Button

    lateinit var dashboardView:DashboardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        text = view.findViewById(R.id.textView) as TextView
        message = view.findViewById(R.id.message)
        btn1 = view.findViewById(R.id.cursorBtn1)
        btn2 = view.findViewById(R.id.cursorBtn2)
        btn3 = view.findViewById(R.id.cursorBtn3)
        btn4 = view.findViewById(R.id.cursorBtn4)
        dashboardView = view.findViewById(R.id.dashboardView)
        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val list = viewModel.getList()
        text.text = "仪表盘自定义View"
        message.text = list

        btn1.setOnClickListener{
            dashboardView.addMarkOne()
        }
        btn2.setOnClickListener{
            dashboardView.smoothAddMarkOne()
        }
        btn3.setOnClickListener{
            dashboardView.addMarkCircle()
        }
        btn4.setOnClickListener{
            dashboardView.smoothAddMarkCircle()
        }
    }

}