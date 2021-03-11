package com.tribo_mkt.evaluation.ui.albuns

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.databinding.AlbumViewBinding
import com.tribo_mkt.evaluation.model.AlbumResposta

class AlbunsAdapter(
    val activity: Activity,
    var items: List<AlbumResposta>,
    val usuarioNome: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingViewAlbuns =
            AlbumViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(bindingViewAlbuns)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        val albumResposta: AlbumResposta = items[position]
        view.album.text = albumResposta.titulo
        view.fundo.setOnClickListener {
            val bundle = bundleOf("albumId" to albumResposta.id, "usuarioNome" to usuarioNome)
            val navController: NavController =
                Navigation.findNavController(activity, R.id.nav_host_fragment)
            navController.navigate(R.id.action_albunsFragment_to_fotosFragment, bundle)
        }
    }

    class ViewHolder(binding: AlbumViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val album = binding.album
        val fundo = binding.fundo
    }
}