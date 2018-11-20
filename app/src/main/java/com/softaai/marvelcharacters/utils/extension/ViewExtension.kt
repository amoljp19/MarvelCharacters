package com.softaai.marvelcharacters.utils.extension

import android.content.ContextWrapper
import android.support.v7.app.AppCompatActivity
import android.view.View


/**
 * Created by Amol Pawar on 20-11-2018.
 * softAai Apps
 */

fun View.getParentActivity(): AppCompatActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}