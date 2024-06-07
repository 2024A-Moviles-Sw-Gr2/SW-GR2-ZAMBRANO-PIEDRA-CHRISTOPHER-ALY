package org.example

import java.io.File

class GestorMecanica(val mecanicaFile: String, val autosFile: String) {

    private val listaMecanica: MutableList<Mecanica> = mutableListOf();
    private val listaAuto: MutableList<Auto> = mutableListOf();

    init {
        leerArchivos()
    }

    private fun leerArchivos(){
        listOf(mecanicaFile,autosFile).forEach {
            val archivo = File(it)
            if (archivo.exists() == false) {
                archivo.createNewFile()
            }
        }

        File(mecanicaFile).useLines {lineas -> lineas.map { it.split(";") }
            .filter { it.size == 5 }
            .forEach {dato -> listaMecanica.add(Mecanica(
                id = dato[0].toInt(),
                disponible = dato[1].toBoolean(),
                nombre = dato[2],
                sector = dato[3],
                valorEnCaja = dato[4].toDouble(),
            )) }
        }

        File(autosFile).useLines {linea -> linea.map { it.split(";") }
            .filter { it.size == 7 }
            .forEach {dato -> val auto = Auto(
                id = dato[0].toInt(),
                mecanicaId = dato[1].toInt(),
                marca = dato[2],
                modelo = dato[3],
                listoParaEntrega = dato[4].toBoolean(),
                valorCotizacion = dato[5].toDouble(),
                asignadoAUnMecanico = dato[6].toBoolean()
            )
            listaAuto.add(auto)
            listaMecanica.find { it.id == auto.mecanicaId }?.autos?.add(auto)
            }
        }
    }

    fun mostrarMecanicas (){
        println("Todos los Talleres:")
        listaMecanica.forEach { println("ID: ${it.id}, Disponible?: ${it.disponible}, Nombre: ${it.nombre}, Sector: ${it.sector}, Valor en Caja: ${it.valorEnCaja}") }
    }

    fun mostrarMecanicaPorId(mecanicaId: Int){
        val mecanicaConsultada = listaMecanica.find { it.id == mecanicaId }
        if (mecanicaConsultada != null) {
            println("ID: ${mecanicaConsultada.id}, Disponible?: ${mecanicaConsultada.disponible}, Nombre: ${mecanicaConsultada.nombre}, Sector: ${mecanicaConsultada.sector}, Valor en Caja: ${mecanicaConsultada.valorEnCaja}")
        } else {
            println("(i) id de Mecanica [${mecanicaId}] no encontrado.")
        }
    }

    fun mostrarAutos (){
        println("Todos los Autos:")
        listaAuto.forEach { println("ID: ${it.id}, MecanicaId: ${it.mecanicaId}, Marca: ${it.marca}, Modelo: ${it.modelo}, Listo para entregarse?: ${it.listoParaEntrega}, Valor de la Cotizacion: ${it.valorCotizacion}, Asignado a un Mecanico?: ${it.asignadoAUnMecanico}") }
    }

    fun mostrarAutoPorId(autoId: Int){
        val autoConsultado = listaAuto.find { it.id == autoId }
        if (autoConsultado != null) {
            println("ID: ${autoConsultado.id}, MecanicaId: ${autoConsultado.mecanicaId}, Marca: ${autoConsultado.marca}, Modelo: ${autoConsultado.modelo}, Listo para entregarse?: ${autoConsultado.listoParaEntrega}, Valor de la Cotizacion: ${autoConsultado.valorCotizacion}, Asignado a un Mecanico?: ${autoConsultado.asignadoAUnMecanico}")
        } else {
            println("(i) id del Auto [${autoId}] no encontrado.")
        }
    }

    fun nuevaMecanica(nombre: String, sector: String, valorEnCaja: Double) {
        val id = ((listaMecanica.maxOfOrNull { it.id } ?: /*si no hay */ 0) + 1)
        listaMecanica.add(Mecanica(id, true, nombre, sector, valorEnCaja))
        guardarCambiosMecanica()
        println("(i) Mecanica Agregada")
    }

    private fun guardarCambiosMecanica() {
        File(mecanicaFile).printWriter().use { out ->
            listaMecanica.forEach { mecanica ->
                out.println("${mecanica.id};${mecanica.disponible};${mecanica.nombre};${mecanica.sector};${mecanica.valorEnCaja}")
            }
        }
    }

    fun nuevoAuto(mecanicaId: Int, marca: String, modelo: String, valorCotizacion: Double) {
        val mecanicaPoseedora = listaMecanica.find { it.id == mecanicaId }
        if (mecanicaPoseedora != null) {
            val id = (listaAuto.maxOfOrNull { it.id } ?: 0) + 1
            val nuevoAuto = Auto(id, mecanicaId, marca, modelo, false, valorCotizacion, false)
            listaAuto.add(nuevoAuto)
            mecanicaPoseedora.autos.add(nuevoAuto)
            guardarCambiosAuto()
            println("(i) Auto Agregado")
        } else {
            println("(i) id de Mecanica [${mecanicaId}] no encontrado.")
        }
    }

    private fun guardarCambiosAuto() {
        File(autosFile).printWriter().use { out ->
            listaAuto.forEach { auto ->
                out.println("${auto.id};${auto.mecanicaId};${auto.marca};${auto.modelo};${auto.listoParaEntrega};${auto.valorCotizacion};${auto.asignadoAUnMecanico}")
            }
        }
    }

    fun actualizarMecanica(id: Int, disponible: Boolean, nombre: String, sector: String, valorEnCaja: Double) {
        val mecanicaAActualizar = listaMecanica.find { it.id == id }
        if (mecanicaAActualizar != null) {
            mecanicaAActualizar.disponible = disponible
            mecanicaAActualizar.nombre = nombre
            mecanicaAActualizar.sector = sector
            mecanicaAActualizar.valorEnCaja = valorEnCaja
            guardarCambiosMecanica()
            println("(i) Archivo [listaMecanica.txt] Actualizado.")
        } else {
            println("(i) id de Mecanica [${id}] no encontrado.")
        }
    }

    fun actualizarAuto(id: Int,mecanicaId: Int, marca: String, modelo: String, listoParaEntrega: Boolean, valorCotizacion: Double, asignadoAUnMecanico: Boolean) {
        val autoAActualizar = listaAuto.find { it.id == id }
        if (autoAActualizar != null) {
            autoAActualizar.mecanicaId = mecanicaId
            autoAActualizar.marca = marca
            autoAActualizar.modelo = modelo
            autoAActualizar.listoParaEntrega = listoParaEntrega
            autoAActualizar.valorCotizacion = valorCotizacion
            autoAActualizar.asignadoAUnMecanico = asignadoAUnMecanico
            guardarCambiosAuto()
            println("(i) Archivo [listaAuto.txt] Actualizado.")
        } else {
            println("(i) id de Auto [${id}] no encontrado.")
        }
    }

    fun eliminarMecanicaPorID(mecanicaId: Int) {
        val mecanicaAEliminar = listaMecanica.find { it.id == mecanicaId }
        if (mecanicaAEliminar != null) {
            mecanicaAEliminar.autos?.forEach {auto ->
                if (auto.mecanicaId == mecanicaId){
                    listaAuto.remove(auto)
                }
            }
            listaMecanica.remove(mecanicaAEliminar)
            guardarCambiosMecanica()
            guardarCambiosAuto()
            println("(i) Mecanica eliminada exitosamente.")
        } else {
            println("(i) id de Mecanica [${mecanicaId}] no encontrado.")
        }
    }

    fun eliminarAutoPorID(autoId: Int) {
        val autoAEliminar = listaAuto.find { it.id == autoId }
        if (autoAEliminar != null) {
            val mecanicaAModificar = listaMecanica.find { it.id == autoAEliminar.mecanicaId }
            mecanicaAModificar?.autos?.remove(autoAEliminar)
            listaAuto.remove(autoAEliminar)
            guardarCambiosMecanica()
            guardarCambiosAuto()
            println("(i) Auto eliminado exitosamente.")
        } else {
            println("(i) id de Auto [${autoId}] no encontrado.")
        }
    }
}