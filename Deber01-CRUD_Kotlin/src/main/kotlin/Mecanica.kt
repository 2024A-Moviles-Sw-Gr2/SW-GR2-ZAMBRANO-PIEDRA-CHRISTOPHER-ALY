package org.example

data class Mecanica(
    var id: Int,
    var disponible: Boolean,
    var nombre: String,
    var sector: String,
    var valorEnCaja: Double,
    var autos: MutableList<Auto> = mutableListOf()
)