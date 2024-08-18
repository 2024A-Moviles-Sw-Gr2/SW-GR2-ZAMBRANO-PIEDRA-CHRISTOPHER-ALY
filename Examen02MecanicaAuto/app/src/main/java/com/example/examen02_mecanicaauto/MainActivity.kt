package com.example.examen02_mecanicaauto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private var mecanicas: ArrayList<MecanicaModel> = arrayListOf()
    private var adapter: ArrayAdapter<MecanicaModel>? = null
    private var posicion = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseDatosCompanion.tables = SQLiteHelper(this)
        val listaMecanicas = findViewById<ListView>(R.id.lista_Mecanicas)
        mecanicas = BaseDatosCompanion.tables!!.obtenerMecanicas()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mecanicas
        )

        listaMecanicas.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val irCrearMecanicaForm = findViewById<Button>(R.id.agregar_Mecanica)
        irCrearMecanicaForm.setOnClickListener {
            goToActivity(DatosMecanica::class.java)
        }

        /*
        val botonMapa = findViewById<Button>(R.id.btn_ir_mapa)
        botonMapa.setOnClickListener {
            goToActivity(GoogleMaps::class.java)
        }
        */
        registerForContextMenu(listaMecanicas)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_mecanica, menu)

        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_Mecanica -> {
                goToActivity(DatosMecanica::class.java, mecanicas[posicion])
                true
            }
            R.id.borrar_Mecanica -> {
                openDialog(mecanicas[posicion].idMecanica)
                true
            }
            R.id.ver_Autos -> {
                goToActivity(ListaAutos::class.java, mecanicas[posicion])
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: MecanicaModel? = null
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.apply {
                putExtra("mecanicaSeleccionada", dataToPass)
            }
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar la mecanica?")
        builder.setPositiveButton("Eliminar") { _, _ ->
            BaseDatosCompanion.tables!!.borrarMecanica(registerIndex)
            mecanicas.clear()
            mecanicas.addAll(BaseDatosCompanion.tables!!.obtenerMecanicas())
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }
}
