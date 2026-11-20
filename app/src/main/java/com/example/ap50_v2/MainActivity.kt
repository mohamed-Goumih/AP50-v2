package com.example.ap50_v2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ap50_v2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    private val calculLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        if (result.resultCode == RESULT_OK && data != null) {
            val forme = data.getStringExtra("FORME")
            val surface = data.getDoubleExtra("SURFACE", 0.0)
            val ligne = "$forme : ${":%.2f".format(surface)}"

            val texteActuel = binding.tvVide.text.toString()
            val aucunCalculTexte = getString(R.string.aucun_calcul)

            // Si c'haffichait "Aucun calcul" ou était vide, on remplace. Sinon, on ajoute en haut.
            if (texteActuel.isEmpty() || texteActuel == aucunCalculTexte) {
                binding.tvVide.text = ligne
            } else {
                binding.tvVide.text = "$ligne\n$texteActuel"
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Afficher le message par défaut au démarrage
        binding.tvVide.text = getString(R.string.aucun_calcul)

        binding.cardCarre.setOnClickListener { ouvrir("Carré") }
        binding.cardRectangle.setOnClickListener { ouvrir("Rectangle") }
        binding.cardTriangle.setOnClickListener { ouvrir("Triangle") }
        binding.cardCercle.setOnClickListener { ouvrir("Cercle") }

        // Bouton Effacer configuré une seule fois ici
        binding.btnEffacer.setOnClickListener {
            binding.tvVide.text = getString(R.string.aucun_calcul)
        }
    }

    private fun ouvrir(forme: String) {
        val intent = Intent(this, CalculActivity::class.java)
        intent.putExtra("FORME", forme)
        calculLauncher.launch(intent)
    }
}