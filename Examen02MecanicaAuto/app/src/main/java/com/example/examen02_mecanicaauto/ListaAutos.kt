package com.example.examen02_mecanicaauto

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ListaAutos : AppCompatActivity() {

    private var listaAutos: ArrayList<AutoModel> = arrayListOf()
    private var adapter: ArrayAdapter<AutoModel>? = null
    private var posicion = -1
    private var mecanicaSeleccionada: MecanicaModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_autos)

        mecanicaSeleccionada = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("mecanicaSeleccionada", MecanicaModel::class.java)
        } else {
            intent.getParcelableExtra<MecanicaModel>("mecanicaSeleccionada")
        }
        val autosMecanica = findViewById<TextView>(R.id.titulo_Lista_Autos)
        autosMecanica.text = mecanicaSeleccionada!!.nombreMecanica

        BaseDatosCompanion.tables = SQLiteHelper(this)
        val listaAutosView = findViewById<ListView>(R.id.lista_Autos)
        listaAutos = BaseDatosCompanion.tables!!.obtenerAutosPorMecanica(mecanicaSeleccionada!!.idMecanica)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaAutos
        )

        listaAutosView.adapter = adapter
        adapter!!.notifyDataSetChanged()

        val irCrearAuto = findViewById<Button>(R.id.crear_Auto)
        irCrearAuto.setOnClickListener {
            goToActivity(DatosAuto::class.java, null, mecanicaSeleccionada!!)
        }
        val regresar = findViewById<Button>(R.id.regresar)
        regresar.setOnClickListener {
            goToActivity(MainActivity::class.java)
        }

        registerForContextMenu(listaAutosView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_auto, menu)

        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        posicion = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editar_Auto -> {
                goToActivity(DatosAuto::class.java, listaAutos[posicion], mecanicaSeleccionada!!, false)
                true
            }
            R.id.borrar_Auto -> {
                openDialog(listaAutos[posicion].idAuto)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: AutoModel? = null,
        dataToPass2: MecanicaModel? = null,
        create: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if (dataToPass != null) {
            intent.apply {
                putExtra("autoSeleccionado", dataToPass)
            }
        }
        if (dataToPass2 != null) {
            intent.apply {
                putExtra("mecanicaSeleccionada", dataToPass2)
            }
        }
        intent.apply {
            putExtra("create", create)
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar auto?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            BaseDatosCompanion.tables!!.borrarAuto(registerIndex)
            listaAutos.clear()
            listaAutos.addAll(BaseDatosCompanion.tables!!.obtenerAutosPorMecanica(mecanicaSeleccionada!!.idMecanica))
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}
