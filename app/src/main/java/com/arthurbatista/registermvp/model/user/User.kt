package com.arthurbatista.registermvp.model.user

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "cep")
    var cep: String = "",

    @ColumnInfo(name = "logradouro")
    var logradouro: String = "",

    @ColumnInfo(name = "bairro")
    var bairro: String = "",

    @ColumnInfo(name = "localidade")
    var localidade: String = "",

    @ColumnInfo(name = "uf")
    var uf: String = ""
)