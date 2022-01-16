package com.vicky7230.rutba.network

import com.slack.api.Slack
import timber.log.Timber
import com.slack.api.SlackConfig


object SlackHttpLayer {

    private lateinit var slack: Slack

    init {
        val config = SlackConfig()
        config.isPrettyResponseLoggingEnabled = true
        slack = Slack.getInstance(config)

    }

    fun test() {
        //val response = slack.methods().usersGetPresence()
        //Timber.e(response.toString())

    }
}