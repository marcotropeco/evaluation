package com.tribo_mkt.evaluation.ui.comentarios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.databinding.CommentViewBinding
import com.tribo_mkt.evaluation.model.ComentarioResposta

class ComentariosAdapter(
    var items: List<ComentarioResposta>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingViewComentarios =
            CommentViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(bindingViewComentarios)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        view.titulo.text = items[position].nome
        view.comentario.text = items[position].conteudo
    }

    class ViewHolder(binding: CommentViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val titulo = binding.titulo
        val comentario = binding.comentario
    }
}