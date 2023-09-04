package logica;
import java.util.Scanner;
public class Persona {
	private int id_persona;
	private String nombre;
	private String apellido;
	private Genero genero;
	private Domicilio domicilio;
	Scanner scanner = new Scanner(System.in);
	
	public Persona(int id_persona, String nombre, String apellido, Genero genero, Domicilio domicilio) {
		super();
		this.id_persona = id_persona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.genero = genero;
		this.domicilio = domicilio;
	}

	public Persona() {
		super();
		System.out.println("-----------------");
		System.out.println("CARGA DE PERSONA");
		System.out.println("-----------------");
		System.out.println("ID DE LA PERSONA:");
		this.id_persona=scanner.nextInt();
		scanner.nextLine();
		System.out.println("NOMBRE:");
		this.nombre=scanner.nextLine();
		System.out.println("APELLIDO:");
		this.apellido=scanner.nextLine();
	}

	public int getId_persona() {
		return id_persona;
	}

	public void setId_persona(int id_persona) {
		this.id_persona = id_persona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public void mostrar() {
		System.out.println("-----------------");
		System.out.println("ID PERSONA N° "+this.getId_persona());
		System.out.println("-----------------");
		System.out.println(this.nombre +" "+ this.apellido+";");
		System.out.println(this.genero.getNombre()+";");
		System.out.println("-----------------");
		System.out.println(this.domicilio.getCalle()+" "+this.domicilio.getNumero()+";");
		System.out.println(this.domicilio.getLocalidad().getDescripcion()+";");
	}
	
	
}
