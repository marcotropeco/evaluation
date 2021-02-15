package com.tribo_mkt.evaluation.ui

import android.app.Activity
import android.content.Intent
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.model.PostagemResposta

class PostagensAdapter(
        val activity: Activity,
        var items: List<PostagemResposta>,
        var usuarioNome: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_view, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        view.titulo.text = items[position].titulo
        view.fundo.setOnClickListener {
            val intent = Intent(activity, ComentariosActivity::class.java)
            intent.putExtra("postagemId", items[position].id)
            intent.putExtra("usuarioNome", usuarioNome)
            activity.startActivity(intent)
        }
        view.comentarios.text = activity.getString(R.string.message_num_comments_prefix) + items[position].comentarios.toString()
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