package com.example.recyclerview

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclerview.databinding.ActivityNuevoMonteBinding

class NuevoMonte : AppCompatActivity() {
private lateinit var binding : ActivityNuevoMonteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityNuevoMonteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnGuardar.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("id",binding.edId.text.toString())
            bundle.putString("nombre",binding.edNombre.text.toString())
            bundle.putString("continente",binding.edDescripcion.text.toString())
            bundle.putString("altura",binding.edAltura.text.toString())
            bundle.putString("foto",binding.edURL.text.toString())
            bundle.putString("url",binding.edurldescrip.toString())
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}