package org.example

data class Auto(
    var id: Int,
    var mecanicaId: Int,
    var marca: String,
    var modelo: String,
    var listoParaEntrega: Boolean,
    var valorCotizacion: Double,
    var asignadoAUnMecanico: Boolean
)
