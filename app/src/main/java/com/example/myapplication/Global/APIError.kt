package com.example.myapplication.Global


import okhttp3.ResponseBody
import org.json.JSONObject


fun ResponseBody.getError(): String? {
    val errorJsonString = this.string()
    var msg = ""
    try {
        val json: JSONObject = JSONObject(errorJsonString)
        msg = json.getString("message")
    } catch (ex: Exception) {
        msg = errorJsonString
    }
    return msg
}