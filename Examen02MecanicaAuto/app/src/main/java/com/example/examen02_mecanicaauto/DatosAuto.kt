package com.example.examen02_mecanicaauto

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class DatosAuto : AppCompatActivity() {

    private lateinit var modeloAuto: EditText
    private lateinit var estaListo: EditText
    private lateinit var valorCotizacion: EditText
    private lateinit var idMecanica: EditText
    private lateinit var guardarAuto: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_auto)

        initializeViews()
        val autoSeleccionado = getParcelableExtraCompat<AutoModel>("autoSeleccionado")
        val mecanicaSeleccionada = getParcelableExtraCompat<MecanicaModel>("mecanicaSeleccionada")
        val create = intent.getBooleanExtra("create", true)

        if (create) {
            idMecanica.setText(mecanicaSeleccionada?.idMecanica.toString())
            guardarAuto.setOnClickListener {
                BaseDatosCompanion.tables?.crearAuto(
                    modeloAuto.text.toString(),
                    estaListo.text.toString().toBoolean(),
                    valorCotizacion.text.toString().toDouble(),
                    idMecanica.text.toString().toInt()
                )
                if (mecanicaSeleccionada != null) {
                    goToActivity(ListaAutos::class.java, mecanicaSeleccionada)
                }
            }
        } else {
            autoSeleccionado?.let {
                modeloAuto.setText(it.modeloAuto)
                estaListo.setText(it.estaListo.toString())
                valorCotizacion.setText(it.valorCotizacion.toString())
                idMecanica.setText(it.idMecanica.toString())
            }
            guardarAuto.setOnClickListener {
                autoSeleccionado?.let {
                    BaseDatosCompanion.tables?.actualizarAuto(
                        it.idAuto,
                        modeloAuto.text.toString(),
                        estaListo.text.toString().toBoolean(),
                        valorCotizacion.text.toString().toDouble(),
                        idMecanica.text.toString().toInt()
                    )
                }
                goToActivity(ListaAutos::class.java, mecanicaSeleccionada!!)
            }
        }
    }

    private fun initializeViews() {
        modeloAuto = findViewById(R.id.modelo_Auto)
        estaListo = findViewById(R.id.estaListo)
        valorCotizacion = findViewById(R.id.valor_cotizacion)
        idMecanica = findViewById(R.id.id_Mecanica)
        guardarAuto = findViewById(R.id.btn_guardar_Auto)
    }

    private inline fun <reified T : Parcelable> getParcelableExtraCompat(key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(key, T::class.java)
        } else {
            intent.getParcelableExtra(key)
        }
    }

    private fun goToActivity(activityClass: Class<*>, dataToPass: MecanicaModel) {
        val intent = Intent(this, activityClass).apply {
            putExtra("mecanicaSeleccionada", dataToPass)
        }
        startActivity(intent)
    }
}
