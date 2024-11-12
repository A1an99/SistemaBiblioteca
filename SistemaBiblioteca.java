import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


class Libro {
    private String titulo;
    private String autor;
    private boolean disponible;

    public Libro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponible = true;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void prestar() {
        disponible = false;
    }

    public void devolver() {
        disponible = true;
    }

    @Override
    public String toString() {
        return titulo + " por " + autor + " - " + (disponible ? "Disponible" : "Prestado");
    }
}

class Biblioteca {
    private ArrayList<Libro> librosDisponibles;
    private Queue<String> colaSolicitudes;

    public Biblioteca() {
        librosDisponibles = new ArrayList<>();
        colaSolicitudes = new LinkedList<>();
    }

    public void agregarLibro(Libro libro) {
        librosDisponibles.add(libro);
    }

    public void solicitarLibro(String titulo, String usuario) {


        for (Libro libro : librosDisponibles) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                if (libro.isDisponible()) {
                    libro.prestar();
                    System.out.println("El libro \"" + titulo + "\" ha sido prestado a " + usuario + ".");
                } else {
                    colaSolicitudes.add(usuario);
                    System.out.println("El libro \"" + titulo + "\" no está disponible. " + usuario + " ha sido agregado a la cola de espera.");
                }
                return;
            }
        }
        System.out.println("El libro \"" + titulo + "\" no se encuentra en la biblioteca.");
    }


    public void devolverLibro(String titulo) {
        for (Libro libro : librosDisponibles) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                if (!libro.isDisponible()) {
                    libro.devolver();
                    System.out.println("El libro \"" + titulo + "\" ha sido devuelto.");

                   
                    if (!colaSolicitudes.isEmpty()) {
                        String siguienteUsuario = colaSolicitudes.poll();
                        libro.prestar();
                        System.out.println("El libro \"" + titulo + "\" ha sido prestado a " + siguienteUsuario + " de la cola de espera.");
                    }
                } else {
                    System.out.println("El libro \"" + titulo + "\" ya está disponible en la biblioteca.");
                }
                return;
            }
        }
        System.out.println("El libro \"" + titulo + "\" no se encuentra en la biblioteca.");
    }


    public void mostrarLibros() {
        System.out.println("Libros en la biblioteca:");
        for (Libro libro : librosDisponibles) {
            System.out.println(libro);
        }
    }
}

public class SistemaBiblioteca {
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();

        
        biblioteca.agregarLibro(new Libro("No puedes hacerme daño", "David Goggins"));
        biblioteca.agregarLibro(new Libro("El hombre en busca de sentido", "Viktor Frankl"));
        biblioteca.agregarLibro(new Libro("La sabiduría de los psicópatas", "Kevin Dutton"));
        biblioteca.agregarLibro(new Libro("Don Quijote de la Mancha", "Miguel de Cervantes"));

        Scanner scanner = new Scanner(System.in);
        String opcion;

        do {
            System.out.println("\nSistema de Biblioteca Virtual");
            System.out.println("1. Mostrar libros disponibles");
            System.out.println("2. Solicitar libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    biblioteca.mostrarLibros();
                    break;
                case "2":
                    System.out.print("Ingrese el título del libro que desea solicitar: ");
                    String tituloSolicitar = scanner.nextLine();
                    System.out.print("Ingrese su nombre: ");
                    String usuario = scanner.nextLine();
                    biblioteca.solicitarLibro(tituloSolicitar, usuario);
                    break;
                case "3":
                    System.out.print("Ingrese el título del libro que desea devolver: ");
                    String tituloDevolver = scanner.nextLine();
                    biblioteca.devolverLibro(tituloDevolver);
                    break;
                case "4":
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (!opcion.equals("4"));

        scanner.close();
    }
}
