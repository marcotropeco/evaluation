package com.tribo_mkt.evaluation.ui.postagens

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
import com.tribo_mkt.evaluation.model.ComentarioResposta
import com.tribo_mkt.evaluation.model.PostagemResposta
import com.tribo_mkt.evaluation.viewmodel.ComentariosViewModel
import com.tribo_mkt.evaluation.viewmodel.PostagensViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PostagemFragment : Fragment() {

    private val comentariosViewModel: ComentariosViewModel by viewModel()
    private val postagensViewModel: PostagensViewModel by viewModel()

    var postagens: Resposta? = null
    var comentarios: Resposta? = null
    lateinit var usuarioNome: String

    data class Resposta(val lista: List<Any>?)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_postagem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usuarioId = arguments?.getString("usuarioId")!!
        usuarioNome = arguments?.getString("usuarioNome")!!
        setActionBar(usuarioNome)
        setUpPostsUserList(usuarioId)
        setUpComentsUserList(usuarioId)
    }

    private fun setActionBar(usuarioNome: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.message_posts_prefix) + usuarioNome
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setUpComentsUserList(userId: String) {
        comentariosViewModel.comentariosData.observe(viewLifecycleOwner, Observer {
            it?.let { comments ->
                val commentsList = comments.toList()
                finalizar(null, PostagemFragment.Resposta(commentsList))
            }
        })
        comentariosViewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { errors ->
                if (errors) {
                    finalizar(null, null)
                }
            }
        })
        comentariosViewModel.getCommentsPerUser(userId.toInt())
    }

    private fun setUpPostsUserList(userId: String) {
        postagensViewModel.postagemData.observe(viewLifecycleOwner, Observer {
            it?.let { posts ->
                val postsList = posts.toList()
                finalizar(PostagemFragment.Resposta(postsList), null)
            }
        })
        postagensViewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { errors ->
                if (errors) {
                    finalizar(null, null)
                }
            }
        })
        postagensViewModel.getPostsPerUser(userId.toInt())
    }

    fun finalizar(postagens: PostagemFragment.Resposta?, comentarios: PostagemFragment.Resposta?) {

        if (postagens != null) {
            this.postagens = postagens
        }

        if (comentarios != null) {
            this.comentarios = comentarios
        }

        if (this.postagens != null && this.comentarios != null) {

            val postagensLista = this.postagens!!.lista
            val comentariosLista = this.comentarios!!.lista

            if (postagensLista != null) {
                (postagensLista as List<PostagemResposta>).forEach {
                    if (comentariosLista != null) {
                        it.comentarios =
                            (comentariosLista as List<ComentarioResposta>).filter { comment -> comment.postagemId == it.id }.size
                    }
                }

                val lista = view?.findViewById<RecyclerView>(R.id.lista)!!
                val adapter = activity?.let { PostagensAdapter(it, postagensLista, usuarioNome) }
                lista.layoutManager = LinearLayoutManager(context)
                lista.adapter = adapter
                view?.findViewById<View>(R.id.loading)!!.visibility = View.GONE
            } else {
                view?.findViewById<View>(R.id.loading)!!.visibility = View.GONE
                Toast.makeText(context, getString(R.string.message_error_load), Toast.LENGTH_LONG)
                    .show()
            }
        }
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