package logica;
import java.util.Scanner;

public class Genero {
	private int id_genero;
	private String nombre;
	private char abreviatura;
	Scanner scanner = new Scanner(System.in);
	
	public Genero(int id_genero, String nombre, char abreviatura) {
		super();
		this.id_genero = id_genero;
		this.nombre = nombre;
		this.abreviatura = abreviatura;
	}
	
	public Genero() {
		super();
		System.out.println("-----------------");
		System.out.println("CARGA DE GENERO");
		System.out.println("-----------------");
		System.out.println("ID DEL GENERO:");
		try {
			this.id_genero=scanner.nextInt();
		}catch(Exception e) {
			System.out.println("No ingreso tipo de dato correcto!");
		}
		scanner.nextLine();
		System.out.println("NOMBRE DEL GENERO:");
		this.nombre=scanner.nextLine();
		System.out.println("ABREVIATURA:");
		this.abreviatura =scanner.nextLine().charAt(0);
	}

	public int getId_genero() {
		return id_genero;
	}

	public void setId_genero(int id_genero) {
		this.id_genero = id_genero;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(char abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	
	
}
