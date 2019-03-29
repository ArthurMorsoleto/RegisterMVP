package com.arthurbatista.registermvp.presenter

import android.content.Context
import com.arthurbatista.registermvp.model.user.User

interface MainActivityPresenterContract {

    fun openRegister(context: Context)
    fun getAllUsers(context: Context): List<User>?
}
