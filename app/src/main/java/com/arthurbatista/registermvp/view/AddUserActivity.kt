package com.arthurbatista.registermvp.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.arthurbatista.registermvp.R
import com.arthurbatista.registermvp.model.user.User
import com.arthurbatista.registermvp.presenter.AddUserPresenter
import kotlinx.android.synthetic.main.activity_add_user.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast


class AddUserActivity : AppCompatActivity(), AddUserPresenter.View {

    private val presenter = AddUserPresenter()

    var isUpdateDelete: Boolean = false

    var userToUpdateDelete: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        btnDelete.isClickable = false

        val intentFromMain = getIntent()

        if(intentFromMain.hasExtra("USER")){

            isUpdateDelete = true

            btnDelete.isClickable = true

            userToUpdateDelete = intentFromMain.extras.get("USER") as User
            editName.setText(userToUpdateDelete!!.name)
            editCep.setText(userToUpdateDelete!!.cep)
        }

        editCep.addTextChangedListener(MaskEditUtil.mask(editCep, MaskEditUtil.FORMAT_CEP))

        btnSave.setOnClickListener { view ->

            val userName = editName.text.toString()
            val userCep = editCep.text.toString()

            if(!userName.equals("") || !userCep.equals("")){
                if(isUpdateDelete){
                    val user = User(this.userToUpdateDelete!!.id, userName, userCep)
                    Log.i("SAVE","Atualizando user")
                    presenter.updateUser(user, applicationContext)
                }else{
                    val newUser = User(0, userName, userCep)
                    Log.i("SAVE","Salvando novo user")
                    presenter.insertUser(newUser, applicationContext)
                }
                finish()
            }
            else{
                toast("Complete os campos")
            }
        }

        btnDelete.setOnClickListener{ view ->

            if(isUpdateDelete) {

                val userToDelete = intentFromMain.extras.get("USER") as User

                //abrir dialog
                val dialog = AlertDialog.Builder(this@AddUserActivity)
                dialog.setTitle("Confirmar exclusão")
                dialog.setMessage("Deseja excluir o usuário: " + userToDelete.name + "?")
                dialog.setPositiveButton("Sim") { _, _ ->
                    presenter.deleteUser(userToDelete, applicationContext)
                    finish()
                }
                dialog.setNegativeButton("Não", null)
                dialog.show()
            }
            else{
                toast("Nenhum usuário selecionado")
            }
        }

        imgSearch.setOnClickListener { view ->

            Log.i("CLICK","Search clicado")
            closeKeyboard()
            val snackbar: Snackbar
            if(!editCep.text.toString().equals("")){
                Log.i("CLICK","CEP: " + editCep.text.toString())
                snackbar = Snackbar.make(activity_add_user, "Buscando CEP", Snackbar.LENGTH_SHORT)
                snackbar.show()

                presenter.findCEP(editCep.text.toString())

               var c = presenter.cepRetornado
                doAsync {
                    while (c == null) { /*GAMBIARRA*/
                        c = presenter.cepRetornado
                    }
                    val strResult: String = c!!.logradouro + " - " + c!!.cep + " - " +  c!!.bairro + " - " + c!!.localidade + " - " + c!!.uf + "\n"
                    Log.i("CLICK",strResult)
                    txtResultado.text = strResult
                }
            }else{
                Log.i("CLICK","CEP nulo")
                snackbar = Snackbar.make(activity_add_user, "Campo de CEP deve estar completo", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            /* txtResultado.text = "Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro de modelos de tipos. Lorem Ipsum sobreviveu não só a cinco séculos, como também ao salto para a editoração eletrônica, permanecendo essencialmente inalterado. Se popularizou na década de 60, quando a Letraset lançou decalques contendo passagens de Lorem Ipsum, e mais recentemente quando passou a ser integrado a softwares de editoração eletrônica como Aldus PageMaker."*/
        }

    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
