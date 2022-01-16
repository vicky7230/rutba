package com.vicky7230.rutba.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.vicky7230.rutba.AppConstants
import com.vicky7230.rutba.databinding.ActivityMainBinding
import com.vicky7230.rutba.network.Resource
import com.vicky7230.rutba.network.ResponseParser
import com.vicky7230.rutba.network.Status
import okhttp3.ResponseBody


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var code: String? = null
    private var accessToken: String? = null

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val uri = intent?.data
        val newCode = uri?.getQueryParameter(AppConstants.CODE)

        if (newCode == null) {
            Timber.e("Code cannot be null... returning")
            return
        }

        if (code != newCode) {
            code = newCode
            Timber.d("Code retrieved: $code")
            getAuthToken()
        }
    }

    private fun getAuthToken() {
        Timber.d("Trying to get access token")

        viewModel.getAuthToken(code!!)
            .observe(this, Observer { resource: Resource<ResponseBody> ->
                val response = resource.data?.string()
                if (resource.status == Status.SUCCESS) {
                    if (ResponseParser.isSuccessfulResponse(response)) {
                        Timber.d("Access Token retrieved: $response")
                        accessToken = ResponseParser.getAccessToken(response)

                        getUserData()
                        getUserPresence()
                    } else {
                        Timber.e("Error : $response")
                    }
                } else {
                    //add loading view
                }
            })
    }

    private fun getUserData() {
        Timber.d("Trying to get user info")
        viewModel.getUserInfo("Bearer $accessToken")
            .observe(this, Observer { resource: Resource<ResponseBody> ->
                val response = resource.data?.string()
                if (resource.status == Status.SUCCESS) {
                    if (ResponseParser.isSuccessfulResponse(response)) {
                        Timber.d("User Info retrieved: $response")
                        Glide.with(this)
                            .load(ResponseParser.getImageReal(response))
                            .into(binding.profilePic)
                        binding.displayName.text = ResponseParser.getDisplayName(response)
                        binding.realName.text = ResponseParser.getRealName(response)
                    } else {
                        Timber.e("Error : $response")
                    }
                } else {
                    //add loading view
                }
            })
    }

    private fun getUserPresence() {
        Timber.d("Trying to get user presence")
        viewModel.getUserPresence("Bearer $accessToken")
            .observe(this, Observer {resource: Resource<ResponseBody> ->
                val response = resource.data?.string()
                if (resource.status == Status.SUCCESS) {
                    if (ResponseParser.isSuccessfulResponse(response)) {
                        Timber.d("User presence retrieved: $response")
                        //binding.realName.text = ResponseParser.getPresence(response)
                    } else {
                        Timber.e("Error : $response")
                    }
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.slackSignin.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(AppConstants.AUTHORIZATION_URL)))
        }

/*https://slack.com/workspace -signin?redir=/oauth?client _id=2185166192.278790370261 &scope=dnd:write ,emoji:read,team :read,users.profile :read,users.profile:write&user_scope= &redirect_uri=slick: //connect&state =sczy5tjd3&granular_bot _scope=0&single_channel =0&install_redirect= &tracked=1 &team=*/


    }

}