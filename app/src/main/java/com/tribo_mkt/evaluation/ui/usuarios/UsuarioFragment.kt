package com.tribo_mkt.evaluation.ui.usuarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.util.Message
import com.tribo_mkt.evaluation.viewmodel.UsuariosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsuarioFragment : Fragment() {

    private val viewModel: UsuariosViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()
        setUpUsersList()
    }

    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.message_users_prefix)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_usuario, container, false)
    }

    private fun setUpUsersList() {
        viewModel.usersData.observe(viewLifecycleOwner, Observer {
            it?.let { usuarios ->
                val userList = usuarios.toMutableList()
                userList.sortWith(Comparator { s1, s2 -> s1.nome.compareTo(s2.nome) })
                val lista = view?.findViewById<RecyclerView>(R.id.lista)!!
                val adapter = activity?.let { it1 -> UsuarioAdapter(it1, userList) }
                lista.layoutManager = LinearLayoutManager(context)
                lista.adapter = adapter
                view?.findViewById<View>(R.id.loading)!!.visibility = View.GONE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { errors ->
                if (errors) {
                    view?.findViewById<View>(R.id.loading)!!.visibility = View.GONE
                    Message.showMessage(context, getString(R.string.message_error_load))
                }
            }
        })
        viewModel.getUsers()
    }
}