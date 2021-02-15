package com.tribo_mkt.evaluation.ui.albuns

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.viewmodel.AlbunsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbunsActivity : AppCompatActivity() {

    private val viewModel: AlbunsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albuns)

        val usuarioId = intent.extras!!.getString("usuarioId")!!
        val usuarioNome = intent.extras!!.getString("usuarioNome")!!

        supportActionBar!!.title = getString(R.string.message_album_prefix) + usuarioNome
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        setUpAlbunsUserList(usuarioId, usuarioNome)
    }

    private fun setUpAlbunsUserList(userId: String, userName: String) {
        viewModel.albunsData.observe(this, Observer {
            it?.let { todosAlbuns ->
                val albunsList = todosAlbuns.toList()
                val lista = findViewById<RecyclerView>(R.id.lista)!!
                val adapter = AlbunsAdapter(this, albunsList, userName)
                lista.layoutManager = LinearLayoutManager(this)
                lista.adapter = adapter
                findViewById<View>(R.id.loading)!!.visibility = View.GONE
            }
        })
        viewModel.error.observe(this, Observer {
            it?.let { errors ->
                if (errors) {
                    findViewById<View>(R.id.loading)!!.visibility = View.GONE
                    Toast.makeText(this, getString(R.string.message_error_load), Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.getAlbunsPerUser(userId.toInt())
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