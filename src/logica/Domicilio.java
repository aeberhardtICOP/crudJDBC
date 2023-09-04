package logica;
import java.util.Scanner;

public class Domicilio {
	private int id_domicilio;
	private String calle;
	private int numero;
	private Localidad localidad;
	Scanner scanner = new Scanner(System.in);
	
	public Domicilio(int id_domicilio, String calle, int numero, Localidad localidad) {
		super();
		this.id_domicilio = id_domicilio;
		this.calle = calle;
		this.numero = numero;
		this.localidad = localidad;
	}

	public Domicilio() {
		super();
		System.out.println("-----------------");
		System.out.println("CARGA DE DOMICILIO");
		System.out.println("-----------------");
		System.out.println("ID DEL DOMICILIO:");
		try {
			this.id_domicilio=scanner.nextInt();
		}catch(Exception e) {
			System.out.println("No ingreso tipo de dato correcto!");
		}
		scanner.nextLine();
		System.out.println("CALLE:");
		this.calle=scanner.nextLine();
		System.out.println("NUMERO:");
		try {
			this.numero =scanner.nextInt();
		}catch(Exception e) {
			System.out.println("No ingreso el tipo de dato correcto!!");
		}
			
	}

	public int getId_domicilio() {
		return id_domicilio;
	}

	public void setId_domicilio(int id_domicilio) {
		this.id_domicilio = id_domicilio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	public void mostrar() {
		System.out.println("-----------------------");
		System.out.println("DOMICILIO ID: "+this.id_domicilio);
		System.out.println("-----------------------");
		System.out.println(this.getCalle()+" "+this.getNumero());
		System.out.println(this.getLocalidad().getDescripcion()+" "+this.getLocalidad().getCodigo_postal());
		
		
	}
	
	
}
