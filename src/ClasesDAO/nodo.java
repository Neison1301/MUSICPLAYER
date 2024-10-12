package ClasesDAO;

public class nodo {

    String nombre, direccion, imagenPortada;
    nodo siguiente, anterior;

    public nodo(String nombre, String direccion, String imagenPortada) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.imagenPortada = imagenPortada;
    }

    public nodo(String nombre, String direccion) {
        this(nombre, direccion, null);  // Se inicializa imagenPortada como null
    }
}
