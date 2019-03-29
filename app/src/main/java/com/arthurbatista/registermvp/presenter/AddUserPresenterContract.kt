package com.arthurbatista.registermvp.presenter

import android.content.Context
import com.arthurbatista.registermvp.model.cep.CEP
import com.arthurbatista.registermvp.model.user.User

interface AddUserPresenterContract{

    fun insert(user: User)

    fun findCEP(cep: String)

    fun insertUser(user: User, context: Context)

    fun deleteUser(user: User, context: Context)

    fun updateUser(user: User, context: Context)
}