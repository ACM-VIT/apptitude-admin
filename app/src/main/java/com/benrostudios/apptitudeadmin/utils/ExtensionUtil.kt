package com.benrostudios.apptitudeadmin.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.benrostudios.apptitudeadmin.R
import com.google.android.material.snackbar.Snackbar

fun View.hide(){
    this.visibility = View.INVISIBLE
}
fun View.show(){
    this.visibility = View.VISIBLE
}
fun View.gone(){
    this.visibility = View.GONE
}
fun Context.shortToaster(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun CharSequence.isValidMobile(): Boolean{
    if(android.util.Patterns.PHONE.matcher(this).matches()){
        return true
    }
    return false
}
fun View.errSnack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.setBackgroundTint(resources.getColor(R.color.red))
    snack.show()
}