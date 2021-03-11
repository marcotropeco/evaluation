package com.tribo_mkt.evaluation.ui.postagens

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.databinding.PostViewBinding
import com.tribo_mkt.evaluation.model.PostagemResposta

class PostagensAdapter(
    val activity: Activity,
    var items: List<PostagemResposta>,
    var usuarioNome: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingViewPostagens =
            PostViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(bindingViewPostagens)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        val postagemResposta: PostagemResposta = items[position];
        view.titulo.text = postagemResposta.titulo
        view.fundo.setOnClickListener {
            val bundle = bundleOf(
                "postagemId" to postagemResposta.id,
                "usuarioNome" to usuarioNome
            )
            val navController: NavController = Navigation.findNavController(activity, R.id.nav_host_fragment)
            navController.navigate(R.id.action_postagemFragment_to_comentariosFragment, bundle)
        }
        val textPostagem = activity.getString(R.string.message_num_comments_prefix) + postagemResposta.comentarios.toString()
        view.comentarios.text = textPostagem

        if (postagemResposta.comentarios == null) {
            view.comentarios.visibility = View.GONE
        }
    }

    class ViewHolder(binding: PostViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val fundo = binding.fundo
        val titulo = binding.titulo
        val comentarios = binding.comentarios
    }
}