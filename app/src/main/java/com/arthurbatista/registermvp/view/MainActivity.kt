package com.arthurbatista.registermvp.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import com.arthurbatista.registermvp.R
import com.arthurbatista.registermvp.model.user.User
import com.arthurbatista.registermvp.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.doAsync
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() , MainActivityPresenter.View {

    private val presenter = MainActivityPresenter()

    private var userAdapter: UserAdapter? = null

    private var usersList: List<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        //loadUsers()
        //userAdapter = UserAdapter(null , this)

        fab.setOnClickListener { view ->
            //abrir tela de cadastro
            presenter.openRegister(this)
        }


//        //insert teste
//        val user1 = User(1,"Fulano", "00000-000")
//        val user2 = User(2, "Sicrano","11111-111")
//        val user3 = User(3, "Estudo", "22222-222")
//        val user4 = User(4, "Manito", "33333-333")
//        presenter.insertUser(user1, this)
//        presenter.insertUser(user2, this)
//        presenter.insertUser(user3, this)
//       presenter.insertUser(user4, this)
    }

    override fun onStart() {
        loadUsers()
        super.onStart()
    }

    fun loadUsers(){
//       doAsync {

            usersList = presenter.getAllUsers(applicationContext)
            Log.i("LISTA_TESTE", usersList.toString())

            userAdapter = UserAdapter(usersList, applicationContext) { userItem: User -> itemClicked(userItem)} //OBS: uso de  lambda expressions

        val recyclerView = recyclerUsersList
            recyclerView.adapter = userAdapter
            recyclerView.layoutManager = LinearLayoutManager(
                applicationContext,
                LinearLayout.VERTICAL,
                false
            )
            recyclerView.hasFixedSize()
//        }
    }

    private fun itemClicked(userClicked : User) {
        Toast.makeText(this, userClicked.name, Toast.LENGTH_LONG).show()

        val editUserIntent = Intent(this, AddUserActivity::class.java)
        editUserIntent.putExtra("USER", userClicked)
        startActivity(editUserIntent)
    }


/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    */

}
