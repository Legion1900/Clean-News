package com.legion1900.cleannews.presentation.view.activity.listener

import android.view.View
import android.widget.AdapterView

class OnTopicSelectedListener(private val callback: () -> Unit) :
    AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do here.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        callback()
    }
}