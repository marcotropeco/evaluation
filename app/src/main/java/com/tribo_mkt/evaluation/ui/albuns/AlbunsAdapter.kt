package com.tribo_mkt.evaluation.ui.albuns

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
import com.tribo_mkt.evaluation.model.AlbumResposta

class AlbunsAdapter(
        val activity: Activity,
        var items: List<AlbumResposta>,
        val usuarioNome: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.album_view, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ViewHolder
        view.album.text = items[position].titulo
        view.fundo.setOnClickListener {
            val bundle = bundleOf("albumId" to items[position].id, "usuarioNome" to usuarioNome)
            val navController: NavController =
                Navigation.findNavController(activity, R.id.nav_host_fragment)
            navController.navigate(R.id.action_albunsFragment_to_fotosFragment, bundle)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val album = itemView.findViewById<TextView>(R.id.album)!!
        val fundo = itemView.findViewById<View>(R.id.fundo)!!
    }
}