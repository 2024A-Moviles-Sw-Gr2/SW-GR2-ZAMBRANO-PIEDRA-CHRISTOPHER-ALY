package org.example

fun main() {
    val gestor = GestorMecanica("listaMecanica.txt", "listaAuto.txt")

    do {
        println("Que desea hacer?:")
        println("1: Crear Mecanica")
        println("2: Crear Auto")
        println("3: Mostrar Mecanicas")
        println("4: Mostrar Autos")
        println("5: Actualizar Mecanica")
        println("6: Actualizar Auto")
        println("7: Eliminar Mecanica")
        println("8: Eliminar Auto")
        println("0: Salir")
        println("Ingrese su opcion:")
        var option = readLine()?.toInt()

        when (option){
            0 -> {
                println("Saliendo...")
            }
            1 -> {
                println("*------Crear Mecanica------*")
                println("Nombre:")
                val nombre = readLine() ?: ""
                println("Sector:")
                val sector = readLine() ?: ""
                println("Valor en caja actual:")
                val valorEnCaja = readLine() ?: ""
                gestor.nuevaMecanica(nombre, sector, valorEnCaja.toDouble())
            }

            2 -> {
                println("*------Crear Auto------*")
                println("idMecanica:")
                val idMecanica = readLine() ?: ""
                println("Marca:")
                val marca = readLine() ?: ""
                println("Modelo:")
                val modelo = readLine() ?: ""
                println("Valor Cotizacion:")
                val valorCotizacion = readLine() ?: ""

                gestor.nuevoAuto(idMecanica.toInt(), marca, modelo, valorCotizacion.toDouble())
            }

            3 -> {
                println("*------Mostrar Mecanicas------*")
                gestor.mostrarMecanicas()
            }

            4 -> {
                println("*------Mostrar Autos------*")
                gestor.mostrarAutos()
            }

            5 -> {
                println("*------Actualizar Mecanica------*")
                println("ID de la Mecanica:")
                val id = readLine() ?: ""
                gestor.mostrarMecanicaPorId(id.toInt())
                println("Disponible?[Y/N]:")
                val disponible = if ((readLine() ?: "") == "Y") true else false
                println("Nuevo Nombre:")
                val nombre = readLine() ?: ""
                println("Nuevo Sector:")
                val sector = readLine() ?: ""
                println("Nuevo Valor en Caja:")
                val valorEnCaja = readLine() ?: ""
                gestor.actualizarMecanica(id.toInt(),disponible,nombre,sector,valorEnCaja.toDouble())
            }

            6 -> {
                println("*------Actualizar Auto------*")
                println("ID del Auto:")
                val id = readLine() ?: ""
                gestor.mostrarAutoPorId(id.toInt())
                println("Nuevo idMecanica:")
                val mecanicaId = readLine() ?: ""
                println("Nueva Marca:")
                val marca = readLine() ?: ""
                println("Nuevo Modelo:")
                val modelo = readLine() ?: ""
                println("Listo para la entrega?[Y/N]:")
                val listoParaEntrega = if ((readLine() ?: "") == "Y") true else false
                println("Nuevo Valor de Cotizacion:")
                val valorCotizacion = readLine() ?: ""
                println("Asignado a un Mecanico?[Y/N]:")
                val asignadoAUnMecanico = if ((readLine() ?: "") == "Y") true else false
                gestor.actualizarAuto(id.toInt(),mecanicaId.toInt(),marca,modelo,listoParaEntrega,valorCotizacion.toDouble(),asignadoAUnMecanico)
            }

            7 -> {
                println("*------Eliminar Mecanica------*")
                println("ID de la mecanica a ELIMINAR:")
                val id = readLine() ?: ""
                gestor.eliminarMecanicaPorID(id.toInt())
            }

            8 -> {
                println("*------Eliminar Auto------*")
                println("ID del auto a ELIMINAR:")
                val id = readLine() ?: ""
                gestor.eliminarAutoPorID(id.toInt())
            }

            else -> {
                println("*------Valor no Soportado------*")
            }
        }
    }while (option != 0)

}