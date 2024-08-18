package com.example.examen02_mecanicaauto

import android.os.Parcel
import android.os.Parcelable

data class AutoModel(
    val idAuto: Int,
    var modeloAuto: String,
    var estaListo: Boolean,
    var valorCotizacion: Double,
    var idMecanica: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idAuto)
        parcel.writeString(modeloAuto)
        parcel.writeByte(if (estaListo) 1 else 0)
        parcel.writeDouble(valorCotizacion)
        parcel.writeInt(idMecanica)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AutoModel> {
        override fun createFromParcel(parcel: Parcel): AutoModel {
            return AutoModel(parcel)
        }

        override fun newArray(size: Int): Array<AutoModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        var estado: String = if (estaListo == true) "Listo" else "Pendiente"
        return "Modelo: $modeloAuto Precio Cotizado: $valorCotizacion Estado: $estado"
    }
}