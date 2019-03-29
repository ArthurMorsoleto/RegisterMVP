package com.arthurbatista.registermvp.presenter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import com.arthurbatista.registermvp.model.user.User
import com.arthurbatista.registermvp.model.user.UserDAO
import com.arthurbatista.registermvp.model.user.UserDatabase
import com.arthurbatista.registermvp.view.AddUserActivity

open class MainActivityPresenter : MainActivityPresenterContract {

    var userDatabase: UserDatabase? = null

    var userDao: UserDAO? = null

    var usersList: ArrayList<User> = ArrayList()

    override fun openRegister(context: Context){
        val intent = Intent(context, AddUserActivity::class.java)
        startActivity(context,intent,null)
    }

    override fun getAllUsers(context: Context) : List<User>? {

        Thread{
            userDatabase = UserDatabase.getInstance(context)
            Log.i("LISTA_TESTE",userDatabase.toString())

            userDao = userDatabase?.getUserDao()
            Log.i("LISTA_TESTE",userDao.toString())

            var list: ArrayList<User>

            list = userDao?.getAllUser() as ArrayList<User>

            Log.i("LISTA_TESTE",usersList.toString())

            if(list!!.isNotEmpty()){
                list.forEach {
                    usersList.add(it)
                }
            }
        }.start()

        return usersList
    }

    interface View
}