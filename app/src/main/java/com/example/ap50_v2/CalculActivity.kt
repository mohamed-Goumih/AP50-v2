package com.example.ap50_v2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ap50_v2.databinding.ActivityCalculBinding

class CalculActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculBinding
    private var forme=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCalculBinding.inflate(layoutInflater)
        setContentView(binding.root)

        forme=intent.getStringExtra("FORME")?:"PAS DE FORME"

        //adapter les champs à la forme
        val hints=when(forme){
            "Carré"->listOf("Coté")
            "Rectangle"->listOf("Longueur","Largeur")
            "Cercle"->listOf("Rayon")
            else -> listOf("Base","Hauteur")
        }
        binding.etValeur1.hint=hints[0]
        if(hints.size==2) binding.etValeur2.hint=hints[1]
        else binding.etValeur2.visibility=View.GONE

        //button calculer
       binding.btnCalculer.setOnClickListener {
           calculer(hints.size)
       }


      //condition affiche image
        binding.imgForme.setImageResource(
            when(forme){
                "Carré"->R.drawable.ic_carre
                "Rectangle"->R.drawable.ic_rectangle
                "Cercle"->R.drawable.ic_cercle
                else -> R.drawable.ic_triangle
            }
        )

        //condition affiche titre de la forme
        binding.toolbar.setTitle(
            when(forme){
                "Carré"->getString(R.string.titre_carre)
                "Rectangle"->getString(R.string.titre_rectangle)
                "Cercle"->getString(R.string.titre_cercle)
                else -> getString(R.string.titre_triangle)
            }
        )

        //condition affiche titre de la forme
        binding.toolbar.setBackgroundColor(
            when(forme){
                "Carré"->Color.BLUE
                "Rectangle"->Color.GREEN
                "Cercle"->Color.MAGENTA
                else -> Color.DKGRAY
            }
        )

        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun calculer(nbrChamps:Int)
    {
        val a=binding.etValeur1.text.toString().toDoubleOrNull()
        val b=binding.etValeur2.text.toString().toDoubleOrNull()

       if(a==null || (nbrChamps==2 && b==null)){
           Toast.makeText(this,"${nbrChamps}:Saisir toutes les champs", Toast.LENGTH_LONG).show()
           return
       }
        val surface=when(forme){
            "Carré"->a*a
            "Rectangle"->a*b!!
            "Cercle"->Math.PI*a*a
            else -> a*b!!/2
        }

        //Toast.makeText(this,"Champs: :$surface", Toast.LENGTH_LONG).show()
        val data= Intent()
        data.putExtra("FORME",forme)
        data.putExtra("SURFACE",surface)
        setResult(RESULT_OK,data)
        finish()

    }
}