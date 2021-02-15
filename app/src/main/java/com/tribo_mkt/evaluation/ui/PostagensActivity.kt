package com.tribo_mkt.evaluation.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.model.ComentarioResposta
import com.tribo_mkt.evaluation.model.PostagemResposta
import com.tribo_mkt.evaluation.viewmodel.ComentariosViewModel
import com.tribo_mkt.evaluation.viewmodel.PostagensViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostagensActivity : AppCompatActivity() {

    private val comentariosViewModel: ComentariosViewModel by viewModel()
    private val postagensViewModel: PostagensViewModel by viewModel()

    var postagens: Resposta? = null
    var comentarios: Resposta? = null
    lateinit var usuarioNome: String

    data class Resposta(val lista: List<Any>?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postagens)

        val usuarioId = intent.extras!!.getString("usuarioId")!!
        usuarioNome = intent.extras!!.getString("usuarioNome")!!

        supportActionBar!!.title = getString(R.string.message_posts_prefix) + usuarioNome
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        setUpPostsUserList(usuarioId)
        setUpComentsUserList(usuarioId)
    }

    private fun setUpComentsUserList(userId: String) {
        comentariosViewModel.comentariosData.observe(this, Observer {
            it?.let { comments ->
                val commentsList = comments.toList()
                finalizar(null, Resposta(commentsList))
            }
        })
        comentariosViewModel.error.observe(this, Observer {
            it?.let { errors ->
                if (errors) {
                    finalizar(null, null)
                }
            }
        })
        comentariosViewModel.getCommentsPerUser(userId.toInt())
    }

    private fun setUpPostsUserList(userId: String) {
        postagensViewModel.postagemData.observe(this, Observer {
            it?.let { posts ->
                val postsList = posts.toList()
                finalizar(Resposta(postsList), null)
            }
        })
        postagensViewModel.error.observe(this, Observer {
            it?.let { errors ->
                if (errors) {
                    finalizar(null, null)
                }
            }
        })
        postagensViewModel.getPostsPerUser(userId.toInt())
    }

    fun finalizar(postagens: Resposta?, comentarios: Resposta?) {

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
                        it.comentarios = (comentariosLista as List<ComentarioResposta>).filter { comment -> comment.postagemId == it.id }.size
                    }
                }

                val lista = findViewById<RecyclerView>(R.id.lista)!!
                val adapter = PostagensAdapter(this, postagensLista, usuarioNome)
                lista.layoutManager = LinearLayoutManager(this)
                lista.adapter = adapter
                findViewById<View>(R.id.loading)!!.visibility = View.GONE
            } else {
                findViewById<View>(R.id.loading)!!.visibility = View.GONE
                Toast.makeText(this, getString(R.string.message_error_load), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}