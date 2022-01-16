package com.vicky7230.rutba.network

import com.google.gson.JsonParser
import org.json.JSONObject

object ResponseParser {

    fun isSuccessfulResponse(response: String?): Boolean {
        return JsonParser.parseString(response).asJsonObject["ok"].asBoolean
    }

    fun getAccessToken(response: String?): String {
        return JsonParser.parseString(response).asJsonObject["access_token"].asString
    }

    fun getImageReal(response: String?): String {
        return JsonParser.parseString(response).asJsonObject["profile"].asJsonObject["image_original"].asString
    }

    fun getDisplayName(response: String?): String {
        return JsonParser.parseString(response).asJsonObject["profile"].asJsonObject["display_name"].asString
    }

    fun getRealName(response: String?): String {
        return JsonParser.parseString(response).asJsonObject["profile"].asJsonObject["real_name"].asString
    }

}