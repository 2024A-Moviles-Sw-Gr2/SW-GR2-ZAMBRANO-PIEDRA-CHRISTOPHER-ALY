package com.example.examen02_mecanicaauto

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class DatosMecanica : AppCompatActivity() {

    private lateinit var nombreMecanica: EditText
    private lateinit var sectorMecanica: EditText
    private lateinit var numeroEmpleadosMecanica: EditText
    private lateinit var abierto: EditText
    private lateinit var latitudMecanica: EditText
    private lateinit var longitudMecanica: EditText
    private lateinit var guardarMecanica: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_mecanica)

        initializeViews()
        val mecanicaSeleccionada = getParcelableExtraCompat<MecanicaModel>("mecanicaSeleccionada")

        if (mecanicaSeleccionada != null) {
            populateFields(mecanicaSeleccionada)
            guardarMecanica.setOnClickListener {
                BaseDatosCompanion.tables?.actualizarMecanica(
                    mecanicaSeleccionada.idMecanica,
                    nombreMecanica.text.toString(),
                    sectorMecanica.text.toString(),
                    numeroEmpleadosMecanica.text.toString().toInt(),
                    abierto.text.toString().toBoolean(),
                    latitudMecanica.text.toString().toDouble(),
                    longitudMecanica.text.toString().toDouble()
                )
                goToActivity(MainActivity::class.java)
            }
        } else {
            guardarMecanica.setOnClickListener {
                BaseDatosCompanion.tables?.crearMecanica(
                    nombreMecanica.text.toString(),
                    sectorMecanica.text.toString(),
                    numeroEmpleadosMecanica.text.toString().toInt(),
                    abierto.text.toString().toBoolean(),
                    latitudMecanica.text.toString().toDouble(),
                    longitudMecanica.text.toString().toDouble()
                )
                goToActivity(MainActivity::class.java)
            }
        }
    }

    private fun initializeViews() {
        nombreMecanica = findViewById(R.id.nombre_Mecanica)
        sectorMecanica = findViewById(R.id.sector_Mecanica)
        numeroEmpleadosMecanica = findViewById(R.id.numeroEmpleados_Mecanica)
        abierto = findViewById(R.id.abierto)
        latitudMecanica = findViewById(R.id.latitud_Mecanica)
        longitudMecanica = findViewById(R.id.longitud_Mecanica)
        guardarMecanica = findViewById(R.id.btn_guardar_mecanica)
    }

    private fun populateFields(mecanica: MecanicaModel) {
        nombreMecanica.setText(mecanica.nombreMecanica)
        sectorMecanica.setText(mecanica.sectorMecanica)
        numeroEmpleadosMecanica.setText(mecanica.numeroEmpleadosMecanica.toString())
        abierto.setText(mecanica.abierto.toString())
        latitudMecanica.setText(mecanica.latitudMecanica.toString())
        longitudMecanica.setText(mecanica.longitudMecanica.toString())
    }

    private inline fun <reified T : Parcelable> getParcelableExtraCompat(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, T::class.java)
        } else {
            intent.getParcelableExtra(key)
        }
    }

    private fun goToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}
