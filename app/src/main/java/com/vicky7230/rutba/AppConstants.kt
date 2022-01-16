package com.vicky7230.rutba

object AppConstants {

    val AUTHORIZATION_URL = "https://slack.com/oauth/authorize?" +
            "scope=users.profile:read,users.profile:write,users:write,users:read,users:read.email" +
            "&client_id=2885486251284.2876518786758" +
            "&response_type=code" +
            "&redirect_uri=https://open.rutba.app/home"
    val CODE = "code"
}