package com.tribo_mkt.evaluation.ui.fotos

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.viewmodel.FotosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FotosActivity : AppCompatActivity() {

    private val viewModel: FotosViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotos)

        val albumId = intent.extras!!.getString("albumId")!!
        val usuarioNome = intent.extras!!.getString("usuarioNome")!!

        supportActionBar!!.title = getString(R.string.message_photo_prefix) + usuarioNome
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        setUpPhotosList(albumId)
    }

    private fun setUpPhotosList(albumID: String) {
        viewModel.fotosData.observe(this, Observer {
            it?.let { photos ->
                val todasAsFotos = photos.toList()
                val lista = findViewById<RecyclerView>(R.id.lista)!!
                val adapter = FotosAdapter(this, todasAsFotos)
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
        viewModel.getPhotosPerAlbum(albumID.toInt())
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