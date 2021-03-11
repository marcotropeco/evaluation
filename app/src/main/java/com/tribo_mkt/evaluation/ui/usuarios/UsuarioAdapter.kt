package com.tribo_mkt.evaluation.ui.usuarios

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tribo_mkt.evaluation.R
import com.tribo_mkt.evaluation.databinding.UsuarioViewBinding
import com.tribo_mkt.evaluation.model.UsuarioResposta

class UsuarioAdapter(
    val activity: Activity,
    var items: List<UsuarioResposta>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val bindingViewUsers =
            UsuarioViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(bindingViewUsers)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val view = holder as ViewHolder
        val usuarioResposta: UsuarioResposta = items[position]
        view.nome.text = usuarioResposta.nome
        view.usuarioNome.text = usuarioResposta.usuarioNome
        view.telefone.text = usuarioResposta.telefone
        view.email.text = usuarioResposta.email
        view.letra.text = usuarioResposta.nome.substring(0, 2).toUpperCase()
        if ((position - 1) % 2 == 0) {
            view.fundo.setBackgroundColor(ContextCompat.getColor(activity, R.color.fundo))
        }
        view.albunsBotao.setOnClickListener {
            navigateListener(usuarioResposta, R.id.action_usuarioFragment_to_albunsFragment)
        }
        view.postagensBotao.setOnClickListener {
            navigateListener(usuarioResposta, R.id.action_usuarioFragment_to_postagemFragment)
        }
    }

    private fun navigateListener(usuarioResposta: UsuarioResposta, action: Int) {
        val bundle = bundleOf(
            "usuarioId" to usuarioResposta.id,
            "usuarioNome" to usuarioResposta.usuarioNome
        )
        val navController: NavController =
            Navigation.findNavController(activity, R.id.nav_host_fragment)
        navController.navigate(action, bundle)
    }

    class ViewHolder(binding: UsuarioViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val nome = binding.nome
        val usuarioNome = binding.usuarioNome
        val telefone = binding.telefone
        val email = binding.email
        val fundo = binding.fundo
        val letra = binding.letra
        val albunsBotao = binding.albunsBotao
        val postagensBotao = binding.postagensBotao
    }
}