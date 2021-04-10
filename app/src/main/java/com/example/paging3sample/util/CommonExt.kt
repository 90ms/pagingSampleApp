package com.example.paging3sample.util

import timber.log.Timber

fun String.printD(){
    Timber.d(this)
}
fun String.printV(){
    Timber.v(this)
}
fun String.printE() {
    Timber.e(this)
}