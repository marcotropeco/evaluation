package com.tribo_mkt.evaluation.ui.fotos

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
import com.tribo_mkt.evaluation.databinding.FragmentFotosBinding
import com.tribo_mkt.evaluation.util.Message
import com.tribo_mkt.evaluation.viewmodel.FotosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FotosFragment : Fragment() {

    private lateinit var bindingFotos: FragmentFotosBinding
    private val viewModel: FotosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingFotos = FragmentFotosBinding.inflate(inflater, container, false)
        return bindingFotos.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var albumId: String
        lateinit var usuarioNome: String

        arguments?.getString("albumId")?.let {
            albumId = it
        }
        arguments?.getString("usuarioNome")?.let {
            usuarioNome = it
        }

        setActionBar(usuarioNome)
        setUpPhotosList(albumId)
    }

    private fun setActionBar(usuarioNome: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.message_photo_prefix) + usuarioNome
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setUpPhotosList(albumID: String) {
        viewModel.fotosData.observe(viewLifecycleOwner, Observer {
            it?.let { photos ->
                val todasAsFotos = photos.toList()
                val lista = bindingFotos.lista
                val adapter = activity?.let { it1 -> FotosAdapter(it1, todasAsFotos) }
                lista.layoutManager = LinearLayoutManager(context)
                lista.adapter = adapter
                bindingFotos.loading.visibility = View.GONE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { errors ->
                if (errors) {
                    bindingFotos.loading.visibility = View.GONE
                    Message.showMessage(context, getString(R.string.message_error_load))
                }
            }
        })
        viewModel.getPhotosPerAlbum(albumID.toInt())
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