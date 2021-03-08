package com.tribo_mkt.evaluation.ui.usuarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.databinding.FragmentUsuarioBinding
import com.tribo_mkt.evaluation.util.Message
import com.tribo_mkt.evaluation.viewmodel.UsuariosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsuarioFragment : Fragment() {

    private lateinit var bindingUsers: FragmentUsuarioBinding
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
    ): View {
        bindingUsers = FragmentUsuarioBinding.inflate(inflater, container, false)
        return bindingUsers.root
    }

    private fun setUpUsersList() {
        viewModel.usersData.observe(viewLifecycleOwner, Observer {
            it?.let { usuarios ->
                val userList = usuarios.toMutableList()
                userList.sortWith(Comparator { s1, s2 -> s1.nome.compareTo(s2.nome) })
                val lista = bindingUsers.lista
                val adapter = activity?.let { it1 -> UsuarioAdapter(it1, userList) }
                lista.layoutManager = LinearLayoutManager(context)
                lista.adapter = adapter
                bindingUsers.loading.visibility = View.GONE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { errors ->
                if (errors) {
                    bindingUsers.loading.visibility = View.GONE
                    Message.showMessage(context, getString(R.string.message_error_load))
                }
            }
        })
        viewModel.getUsers()
    }
}