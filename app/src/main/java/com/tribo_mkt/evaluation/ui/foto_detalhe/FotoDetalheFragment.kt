package com.tribo_mkt.evaluation.ui.foto_detalhe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.tribo_mkt.evaluation.R

class FotoDetalheFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_foto_detalhe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fotoUrl = arguments?.getString("fotoUrl")!!
        val fotoNome = arguments?.getString("fotoNome")!!
        setActionBar()
        setElements(fotoUrl, fotoNome)
    }

    private fun setElements(fotoUrl: String, fotoNome: String) {
        view?.findViewById<TextView>(R.id.imagemNome)?.text = fotoNome
        Picasso.get().load(fotoUrl).into(view?.findViewById<ImageView>(R.id.imagem))
    }

    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.message_photo_detail_prefix)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
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