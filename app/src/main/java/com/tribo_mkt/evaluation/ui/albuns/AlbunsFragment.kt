package com.tribo_mkt.evaluation.ui.albuns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.databinding.FragmentAlbunsBinding
import com.tribo_mkt.evaluation.util.Message
import com.tribo_mkt.evaluation.viewmodel.AlbunsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbunsFragment : Fragment() {
    private lateinit var bindingAlbuns: FragmentAlbunsBinding
    private val viewModel: AlbunsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingAlbuns = FragmentAlbunsBinding.inflate(inflater, container, false)
        return bindingAlbuns.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var usuarioId: String
        lateinit var usuarioNome: String

        arguments?.getString("usuarioId")?.let {
            usuarioId = it
        }
        arguments?.getString("usuarioNome")?.let {
            usuarioNome = it
        }
        setActionBar(usuarioNome)
        setUpAlbunsUserList(usuarioId, usuarioNome)
    }

    private fun setActionBar(usuarioNome: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.message_album_prefix) + usuarioNome
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setUpAlbunsUserList(userId: String, userName: String) {
        viewModel.albunsData.observe(viewLifecycleOwner, Observer {
            it?.let { todosAlbuns ->
                val albunsList = todosAlbuns.toList()
                val lista = bindingAlbuns.lista
                val adapter = activity?.let { it1 -> AlbunsAdapter(it1, albunsList, userName) }
                lista.layoutManager = LinearLayoutManager(context)
                lista.adapter = adapter
                bindingAlbuns.loading.visibility = View.GONE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { errors ->
                if (errors) {
                    bindingAlbuns.loading.visibility = View.GONE
                    Message.showMessage(context, getString(R.string.message_error_load))
                }
            }
        })
        viewModel.getAlbunsPerUser(userId.toInt())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}