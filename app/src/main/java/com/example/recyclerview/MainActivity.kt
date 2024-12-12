package com.example.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var montesList: MutableList<Montes>
    private lateinit var adapter: MontesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        montesList = getListFromJson()
        initRecyclerView()
        initRetrieve()
        binding.svMonte.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (!newText.isNullOrBlank()) {
                    val filteredList: MutableList<Montes> =
                        montesList.filter {
                            it.nombre.lowercase().contains(newText.lowercase())
                        }.toMutableList()

                    adapter.filterByName(filteredList)
                } else {
                    adapter.filterByName(montesList)
                }
                return false
            }
        })

    }

    private fun initRecyclerView() {
        adapter = MontesAdapter(
            montesList = montesList,
            OnClickDelete = { position ->
                onDeleteItem(position)
            }
        )
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvMonte.layoutManager = manager
        binding.rvMonte.adapter = adapter
        binding.rvMonte.addItemDecoration(decoration)
        binding.btnAnadir.setOnClickListener {
            val intent = Intent(this, NuevoMonte::class.java)
            startActivity(intent)
        }
    }

    private fun initRetrieve() {
        val bundle = intent.extras
        if (bundle != null) {
            val idMonte = bundle.getInt("id")
            val nombreMonte = bundle.getString("nombre").toString()
            val continenteMonte = bundle.getString("continente").toString()
            val alturaMonte = bundle.getString("altura").toString()
            val fotoMonte = bundle.getString("foto").toString()
            val urlMonte = bundle.getString("url").toString()
            val newMonte =
                Montes(idMonte, nombreMonte, alturaMonte, continenteMonte, fotoMonte, urlMonte)
            montesList.add(newMonte)
            adapter.notifyItemInserted(montesList.size - 1)
        }
    }

    private fun onDeleteItem(position: Int) {
        if (position >= 0 && position < montesList.size) {
            montesList.removeAt(position)
            //Notificamos que hemos borrado un elemento de la lista
            adapter.notifyItemRemoved(position)
        }
    }


    fun getListFromJson(): MutableList<Montes> {
        val json: String? = getJsonFromAssets(this, "Montes.json")
        val movieList = Gson().fromJson(json, Array<Montes>::class.java).toMutableList()
        return movieList
    }

    private fun getJsonFromAssets(context: Context, file: String): String? {
        var json = ""
        val stream: InputStream = context.assets.open(file)
        val size: Int = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        json = String(buffer, Charset.defaultCharset())
        return json
    }

}