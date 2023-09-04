package logica;
import java.util.Scanner;

public class Localidad {
	private int id_localidad;
	private int codigo_postal;
	private String descripcion;
	Scanner scanner = new Scanner(System.in);
	
	public Localidad(int id_localidad, int codigo_postal, String descripcion) {
		super();
		this.id_localidad = id_localidad;
		this.codigo_postal = codigo_postal;
		this.descripcion = descripcion;
	}

	public Localidad() {
		super();
		System.out.println("-----------------");
		System.out.println("CARGA DE LOCALIDAD");
		System.out.println("-----------------");
		System.out.println("ID DE LA LOCALIDAD:");
		try {
			this.id_localidad=scanner.nextInt();
		}catch(Exception e) {
			System.out.println("No ingreso tipo de dato correcto!");
		}
		scanner.nextLine();
		System.out.println("CODIGO POSTAL:");
		try {
		this.codigo_postal=scanner.nextInt();
		scanner.nextLine();
		System.out.println("NOMBRE: ");
		this.descripcion=scanner.nextLine();
		}catch(Exception e) {
			System.out.println("No ingreso el tipo de dato correcto!!!");
		}
		System.out.println("NOMBRE:");
		this.descripcion =scanner.nextLine();
		
	}

	public int getId_localidad() {
		return id_localidad;
	}

	public void setId_localidad(int id_localidad) {
		this.id_localidad = id_localidad;
	}

	public int getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(int codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	
}
