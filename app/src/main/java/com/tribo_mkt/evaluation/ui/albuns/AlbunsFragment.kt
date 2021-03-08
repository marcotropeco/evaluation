package com.tribo_mkt.evaluation.ui.albuns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
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
import com.tribo_mkt.evaluation.viewmodel.AlbunsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbunsFragment : Fragment() {
    private val viewModel: AlbunsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_albuns, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usuarioId = arguments?.getString("usuarioId")!!
        val usuarioNome = arguments?.getString("usuarioNome")!!
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
                val lista = view?.findViewById<RecyclerView>(R.id.lista)!!
                val adapter = activity?.let { it1 -> AlbunsAdapter(it1, albunsList, userName) }
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