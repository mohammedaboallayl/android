package com.example.myapplication

import android.widget.EditText


class Actions {
    private lateinit var editText: EditText
    private lateinit var regex:Regex
    public lateinit var EMessage:String
    constructor(editText: EditText,pattern: String,ErrorMessage:String){
        this.editText=editText
        this.regex=Regex(pattern)
        this.EMessage=ErrorMessage
    }
    public fun IsMatch():Boolean{
        return regex.matches(editText.text)
    }
}