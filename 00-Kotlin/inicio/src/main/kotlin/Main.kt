fun main(){
    println("Hola mundo")
    //Inmutables(no se oueden Re Asignar "=")
    val inmutable: String = "Chris"
    // inmutable = "Aly" // ERROR

    //Mutable
    var mutable: String = "Aly"
    mutable = "Chris" // OK
    //VAL ES MEJOR QUE VAR

    //Duck Typing
    var ejemploVariable = "Adrian Eguez"
    val edadEjemplo: Int = 12
    val nombre:String = "Adrian"
    val sueldo:Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C")->{
            println("Casado")
        }
        ("S")->{
            println("Soltero")
            var esSoltero:Boolean = true
        }
    }

    val coqueteo = if (esSoltero) "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    //Named parameters
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

}

fun imprimirNombre(nombre:String): Unit{
    Println("Nombre: ${nombre}") //template Strings
}

fun calcularSueldo(
    sueldo:Double, // requerida
    tasa: Double = 12.00, //opcional
    bonoEspecial:Double? = null
    //Variable? -> "?" Es Nullable es decir puede ser nula
):Double{
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println(Inicializado)
    }
}

abstract class Numeros( //Constructor Primario
    //Caso 1) Parametro normal
    //uno:Int , (parametro (sin modificar acceso))

    //Caso 2) Parametro y propiedad (atributo) (private)
    //private var uno: Int (propiedad "instancia.numero")

    protected val numeroUno: Int, //instancia.numeroUno
    protected val numeroDos: Int, //instancia.numeroDos
    // parametroInutil:String, //Parametro
){
    init { //bloque constructor primario
        this.numeroUno
        this.numeroDos
        //  this.parametroInutil //ERROR NO EXISTE
        println("Inicializando")

    }
}

class Suma( // Constructor Primario)
    unoParametro: Int,
    dosParametro: Int,
): Numeros( //Clase papa, Numeros(extendiendo))
    unoParametro,
    dosParametro,
){
    // this.unoParametro //ERROR no existe
    this.numeroUno
    this.numeroDos
    numeroUno //this. OPCIONAL (propiedades, metodos)
    numeroDos //this. OPCIONAL (propiedades, metodos)
    this.soyPublicoExplicito
    soyPublicoImplicito  //this. OPCIONAL (propiedades, metodos)
}

//public fun sumar()Int{ modificar "public es opcional"
fun sumar():Int{
    val total = numeroUno + numeroDos
    Suma.agregarHistorial(total)
    agregarHistorial(total)
}