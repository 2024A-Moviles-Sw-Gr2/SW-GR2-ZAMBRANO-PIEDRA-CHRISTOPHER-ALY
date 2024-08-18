package com.example.examen02_mecanicaauto

import android.os.Parcel
import android.os.Parcelable

data class MecanicaModel(
    val idMecanica: Int,
    var nombreMecanica: String,
    var sectorMecanica: String,
    var numeroEmpleadosMecanica: Int,
    var abierto: Boolean,
    var latitudMecanica: Double,
    var longitudMecanica: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idMecanica)
        parcel.writeString(nombreMecanica)
        parcel.writeString(sectorMecanica)
        parcel.writeInt(numeroEmpleadosMecanica)
        parcel.writeByte(if (abierto) 1 else 0)
        parcel.writeDouble(latitudMecanica)
        parcel.writeDouble(longitudMecanica)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MecanicaModel> {
        override fun createFromParcel(parcel: Parcel): MecanicaModel {
            return MecanicaModel(parcel)
        }

        override fun newArray(size: Int): Array<MecanicaModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Nombre: $nombreMecanica, Numero de Empleados: $numeroEmpleadosMecanica, " +
                "Coordenadas: Latitud( $latitudMecanica) Longitud( $longitudMecanica)"
    }
}