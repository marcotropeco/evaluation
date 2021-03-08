package com.tribo_mkt.evaluation.ui.comentarios

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
import com.tribo_mkt.evaluation.databinding.FragmentComentariosBinding
import com.tribo_mkt.evaluation.util.Message
import com.tribo_mkt.evaluation.viewmodel.ComentariosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComentariosFragment : Fragment() {

    private lateinit var bindingComentarios: FragmentComentariosBinding
    private val comentariosViewModel: ComentariosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingComentarios = FragmentComentariosBinding.inflate(inflater, container, false)
        return bindingComentarios.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postagemId = arguments?.getString("postagemId")!!
        val usuarioNome = arguments?.getString("usuarioNome")!!
        setActionBar(usuarioNome)
        setUpComentsPostList(postagemId)
    }

    private fun setActionBar(usuarioNome: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.message_comment_prefix) + usuarioNome
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setUpComentsPostList(postId: String) {
        comentariosViewModel.comentariosPostData.observe(viewLifecycleOwner, Observer {
            it?.let { comments ->
                val todosComentarios = comments.toList()
                val lista = bindingComentarios.lista
                val adapter = ComentariosAdapter(todosComentarios)
                lista.layoutManager = LinearLayoutManager(context)
                lista.adapter = adapter
                bindingComentarios.loading.visibility = View.GONE
            }
        })
        comentariosViewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { errors ->
                if (errors) {
                    bindingComentarios.loading.visibility = View.GONE
                    Message.showMessage(context, getString(R.string.message_error_load))
                }
            }
        })
        comentariosViewModel.getCommentsPerPost(postId.toInt())
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