package xyz.eddief.funwithflows

import android.util.Log
import xyz.eddief.funwithflows.viewmodel.DisplayViewModel2

fun DisplayViewModel2.customLogging(text: String) {
    Log.d("FunWithFlowsLog", "$vmTag --- $text    on ${Thread.currentThread().name}")
}

fun DisplayViewModel2.customErrorLogging(text: String) {
    Log.e("FunWithFlowsLog", "$vmTag --- $text    on ${Thread.currentThread().name}")
}