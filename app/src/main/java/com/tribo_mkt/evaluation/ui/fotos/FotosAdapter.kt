package com.tribo_mkt.evaluation.ui.fotos

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.databinding.PhotoViewBinding
import com.tribo_mkt.evaluation.model.FotoResposta

class FotosAdapter(
    val activity: Activity,
    var items: List<FotoResposta>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingViewFotos =
            PhotoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(bindingViewFotos)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        view.titulo.text = items[position].titulo
        view.fundo.setOnClickListener {
            val bundle =
                bundleOf("fotoUrl" to items[position].url, "fotoNome" to items[position].titulo)
            val navController: NavController =
                Navigation.findNavController(activity, R.id.nav_host_fragment)
            navController.navigate(R.id.action_fotosFragment_to_fotoDetalheFragment, bundle)
        }
        Picasso.get().load(items[position].thumbnailUrl).into(view.thumb)
    }

    class ViewHolder(binding: PhotoViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val fundo = binding.fundo
        val thumb = binding.thumb
        val titulo = binding.titulo
    }
}