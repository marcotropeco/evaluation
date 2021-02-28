package com.tribo_mkt.evaluation.ui.postagens

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.model.PostagemResposta

class PostagensAdapter(
    val activity: Activity,
    var items: List<PostagemResposta>,
    var usuarioNome: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        view.titulo.text = items[position].titulo
        view.fundo.setOnClickListener {
            val bundle = bundleOf(
                "postagemId" to items[position].id,
                "usuarioNome" to usuarioNome
            )
            val navController: NavController =
                Navigation.findNavController(activity, R.id.nav_host_fragment)
            navController.navigate(R.id.action_postagemFragment_to_comentariosFragment, bundle)
        }
        view.comentarios.text =
            activity.getString(R.string.message_num_comments_prefix) + items[position].comentarios.toString()
        if (items[position].comentarios == null) {
            view.comentarios.visibility = View.GONE
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fundo = itemView.findViewById<View>(R.id.fundo)!!
        val titulo = itemView.findViewById<TextView>(R.id.titulo)!!
        val comentarios = itemView.findViewById<TextView>(R.id.comentarios)!!
    }
}