package com.arthurbatista.registermvp.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.arthurbatista.registermvp.model.cep.CEP
import com.arthurbatista.registermvp.model.cep.RetrofitInitializer
import com.arthurbatista.registermvp.model.user.User
import com.arthurbatista.registermvp.model.user.UserDAO
import com.arthurbatista.registermvp.model.user.UserDatabase
import com.arthurbatista.registermvp.view.AddUserActivity
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class AddUserPresenter : AddUserPresenterContract{

    var cepRetornado: CEP? = null

    override fun findCEP(cep: String) {

        //buscar cep
        val retrofitConfig = RetrofitInitializer()
        val call = retrofitConfig.getCEPService().findCEP(cep)
        call.enqueue(object : Callback<CEP> {
            override fun onResponse(call: Call<CEP>, response: Response<CEP>) {
                val responde = response.body()
                if (responde != null) {

                    val responseCep = CEP(
                        responde.cep,
                        responde.logradouro,
                        responde.bairro,
                        responde.localidade,
                        responde.uf
                    )
                    Log.i("CEP", responseCep.cep + " " +
                            responseCep.logradouro + " " +
                            responseCep.bairro + " " +
                            responseCep.localidade + " " +
                            responseCep.uf
                    )
                    cepRetornado = responseCep
                    return
                }
            }

            override fun onFailure(call: Call<CEP>, t: Throwable) {
                cepRetornado = null
            }
        })
    }

    var userDao: UserDAO? = null

    var userDatabase: UserDatabase? = null

    override fun insert(user: User) {
        userDao?.insert(user)
    }

    override fun insertUser(user: User, context: Context){
        doAsync {
            userDatabase = UserDatabase.getInstance(context)
            userDao = userDatabase?.getUserDao()
            userDao!!.insert(user)
        }
    }

    override fun deleteUser(user: User, context: Context) {
        doAsync {
            userDatabase = UserDatabase.getInstance(context)
            userDao = userDatabase?.getUserDao()
            userDao!!.delete(user)
        }
    }

    override fun updateUser(user: User, context: Context) {
        doAsync {
            userDatabase = UserDatabase.getInstance(context)
            userDao = userDatabase?.getUserDao()
            userDao!!.update(user)
        }
    }

    interface View

}