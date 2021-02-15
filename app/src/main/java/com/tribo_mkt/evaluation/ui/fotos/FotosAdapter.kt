package com.tribo_mkt.evaluation.ui.fotos

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.model.FotoResposta

class FotosAdapter(
        val activity: Activity,
        var items: List<FotoResposta>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_view, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        view.titulo.text = items[position].titulo
        view.fundo.setOnClickListener {
            val intent = Intent(activity, FotoDetalheActivity::class.java)
            intent.putExtra("fotoUrl", items[position].url)
            intent.putExtra("fotoNome", items[position].titulo)
            activity.startActivity(intent)
        }

        Picasso.get().load(items[position].thumbnailUrl).into(view.thumb)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fundo = itemView.findViewById<View>(R.id.fundo)!!
        val thumb = itemView.findViewById<ImageView>(R.id.thumb)!!
        val titulo = itemView.findViewById<TextView>(R.id.titulo)!!
    }
}