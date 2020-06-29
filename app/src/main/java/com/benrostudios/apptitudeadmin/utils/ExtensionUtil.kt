package com.benrostudios.apptitudeadmin.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.hide(){
    this.visibility = View.INVISIBLE
}
fun View.show(){
    this.visibility = View.VISIBLE
}
fun Context.shortToaster(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}