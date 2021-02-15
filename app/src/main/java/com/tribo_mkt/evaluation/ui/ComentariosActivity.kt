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
import com.tribo_mkt.evaluation.viewmodel.ComentariosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComentariosActivity : AppCompatActivity() {

    private val comentariosViewModel: ComentariosViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentarios)

        val postagemId = intent.extras!!.getString("postagemId")!!
        val usuarioNome = intent.extras!!.getString("usuarioNome")!!

        supportActionBar!!.title = getString(R.string.message_comment_prefix) + usuarioNome
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        setUpComentsPostList(postagemId)
    }

    private fun setUpComentsPostList(postId: String) {
        comentariosViewModel.comentariosPostData.observe(this, Observer {
            it?.let { comments ->
                val todosComentarios = comments.toList()
                val lista = findViewById<RecyclerView>(R.id.lista)!!
                val adapter = ComentariosAdapter(todosComentarios)
                lista.layoutManager = LinearLayoutManager(this)
                lista.adapter = adapter
                findViewById<View>(R.id.loading)!!.visibility = View.GONE
            }
        })
        comentariosViewModel.error.observe(this, Observer {
            it?.let { errors ->
                if (errors) {
                    findViewById<View>(R.id.loading)!!.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.message_error_load), Toast.LENGTH_LONG).show()
                }
            }
        })
        comentariosViewModel.getCommentsPerPost(postId.toInt())
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