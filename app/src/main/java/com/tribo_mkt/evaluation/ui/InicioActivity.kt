package com.tribo_mkt.evaluation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.viewmodel.UsuariosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InicioActivity : AppCompatActivity() {

    private val viewModel: UsuariosViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        supportActionBar!!.title = "Usuários"
        setUpUsersList()
    }

    private fun setUpUsersList() {
        viewModel.usersData.observe(this, Observer {
            it?.let { usuarios ->
                val userList = usuarios.toMutableList()
                userList.sortWith(Comparator { s1, s2 -> s1.nome.compareTo(s2.nome) })
                val lista = findViewById<RecyclerView>(R.id.lista)!!
                val adapter = UsuarioAdapter(this, userList)
                lista.layoutManager = LinearLayoutManager(this)
                lista.adapter = adapter
                findViewById<View>(R.id.loading)!!.visibility = View.GONE
            }
        })
        viewModel.error.observe(this, Observer {
            it?.let { errors ->
                if (errors) {
                    findViewById<View>(R.id.loading)!!.visibility = View.GONE
                    Toast.makeText(this, "Algo errado aconteceu. Tente novamente mais tarde.", Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.getUsers()
    }
}