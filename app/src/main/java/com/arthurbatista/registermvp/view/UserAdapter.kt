package com.arthurbatista.registermvp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.support.v7.widget.RecyclerView.Adapter
import android.view.ViewGroup
import com.arthurbatista.registermvp.R
import com.arthurbatista.registermvp.model.user.User
import kotlinx.android.synthetic.main.layout_user_adapter.view.*

class UserAdapter(

    var userList: List<User>? = ArrayList<User>(),
    private val context: Context) : Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_user_adapter, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if(userList!!.isEmpty()) 0 else userList!!.size
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val user = userList?.get(position)
        viewHolder?.let {
            if (user != null) {
                it.bindView(user)
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(user: User){
            val txtName = itemView.txtName
            val txtCep = itemView.txtCep

            txtName.text = user.name
            txtCep.text = user.cep
        }
    }
}
