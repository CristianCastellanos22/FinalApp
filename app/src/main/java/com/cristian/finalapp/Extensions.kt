package com.cristian.finalapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this,message,duration).show()

fun Activity.toast(resourceId: Int, duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(this,resourceId,duration).show()

fun ViewGroup.inflate(layout: Int) = LayoutInflater.from(context).inflate(layout, this, false)!!

inline fun <reified  T: Activity> Activity.goToActivity(noinline  init: Intent.()-> Unit = {}) {
    val intent = Intent(this,T::class.java)
    intent.init()
    startActivity(intent)
}