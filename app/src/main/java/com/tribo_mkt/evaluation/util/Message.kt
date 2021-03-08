package com.tribo_mkt.evaluation.util

import android.content.Context
import android.widget.Toast

class Message {
    companion object {
        fun showMessage(context: Context?, message : String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}