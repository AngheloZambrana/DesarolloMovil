package com.example.componentes.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.example.componentes.databinding.PaginadorLayoutBinding

class PageSelector(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var binding: PaginadorLayoutBinding
    private val buttons: Array<Button>
    private var selectedPage: Int = 0

    init {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = PaginadorLayoutBinding.inflate(inflater, this, true)


        buttons = arrayOf(
            binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5
        )


        setupEventListeners()
    }

    private fun setupEventListeners() {
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                selectPage(index)
            }
        }
    }

    private fun selectPage(pageIndex: Int) {

        buttons[selectedPage].setBackgroundColor(android.graphics.Color.BLACK)
        buttons[pageIndex].setBackgroundColor(android.graphics.Color.RED)

        selectedPage = pageIndex
    }

    fun getSelectedPage(): Int {
        return selectedPage + 1
    }
}