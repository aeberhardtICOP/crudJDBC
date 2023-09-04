package main;
import java.sql.SQLException;
import java.util.Scanner;

import logica.*;
import persistencia.*;
public class Principal {
	private static String eleccion="";
	private static void mostrarMenu()
	{
		System.out.println("Menu: ");
		System.out.println("1: Cargar persona");
		System.out.println("2: Mostrar personas");
		System.out.println("3: Eliminar persona");
		System.out.println("4: Editar persona");
		System.out.println("5: Cargar nueva localidad");
		System.out.println("6: Cargar nuevo genero");
		System.out.println("7: Ver lista de localidades");
		System.out.println("8: Ver lista de generos");
		System.out.println("9: Ver lista de domicilios");
		System.out.println("10: Ver metadata de BD");
		System.out.println("q: Salir");	
		System.out.println("");
		Scanner scanner = new Scanner(System.in);
		System.out.print("Opción: ");
		eleccion = scanner.nextLine();
	}
	public static void main(String[] args) throws SQLException {
		ControladoraLogica controlLogic = new ControladoraLogica(null);
		ControladoraPersistencia controlPersis = new ControladoraPersistencia(controlLogic);
		controlLogic.setControladoraPersistencia(controlPersis);
		controlLogic.abrirConexion();
		controlLogic.traerAMemoria();
		/*
		controlPersis.bd();
		controlPersis.estructuraTabla("persona");
		controlPersis.estructuraTabla("domicilio");
		controlPersis.estructuraTabla("localidad");
		controlPersis.traerGeneros();
		controlPersis.traerLocalidades();
		controlPersis.traerPersonas();
		controlPersis.traerDomicilios();
		Genero gen = new Genero(1, "Masculino", 'M');
		Genero gen2 = new Genero(2, "Femenino", 'F');
		Localidad loc = new Localidad(1, 3000, "Santa Fe");
		Localidad loc2 = new Localidad(2, 3009,"Franck");
		Domicilio dom = new Domicilio(1, "i.falchini",786, loc);
		Domicilio dom2 = new Domicilio(2, "gdor. galvez", 3001, loc2);
		Persona perso = new Persona(1, "alejandro","eberhardt",gen,dom);
		Persona perso2 = new Persona(2,"Maria", "vogt", gen, dom);
		controlPersis.borrarPersona(1);
		try {
			controlPersis.cargarPersona(perso);
			controlPersis.cargarPersona(perso2);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//controlPersis.editarPersona(perso2);


		controlPersis.cerrarConexion();*/
		while(eleccion!="q") {
		mostrarMenu();	
		switch (eleccion){

		case "1":
			System.out.println("Eligio la opcion 1 -> Cargar persona");
			controlLogic.cargarPersona();
			System.out.println("");
			break;
		case "2":
			System.out.println("Elegi la opcion 2 -> Mostrar personas");
			controlLogic.mostrarPersonasDet();
			System.out.println("");
			break;
		case "3":
			System.out.println("Elegi la opcion 3 -> Eliminar persona");
			controlLogic.eliminarPersona();
			System.out.println("");
			break;
		case "4":
			System.out.println("Elegi la opcion 4 -> Editar persona");
			controlLogic.editarPersona();
			System.out.println("");
			break;
		case "5":
			System.out.println("Eligio la opcion 5 -> Cargar nueva localidad");
			System.out.println("");
			break;
		case "6":
			System.out.println("Eligio la opcion 6 -> Cargar nuevo genero");
			System.out.println("");
			break; 
		case "7":
			System.out.println("Eligio la opcion 7 -> Ver lista de localidades");
			controlLogic.mostrarLocalidades();
			System.out.println("");
			break;
		case "8":
			System.out.println("Eligió la opcion 8 -> Ver lista de generos");
			System.out.println("");
			break;
		case "9":
			System.out.println("Eligió la opcion 9 -> Ver lista de domicilios");
			controlLogic.mostrarDomicilios();
			System.out.println("");
			break;
		case "10":
			System.out.println("Eligio la opcion 10 -> Ver metadata de BD");
			System.out.println("");
			break;
		case "777":
			controlLogic.traerAMemoria();
			break;
		case "q":
			System.out.println("Saliendo del sistema...");
			break;
		default:
			System.out.println("Ingreso opcion incorrecta.");
		}  
		}
		controlLogic.cerrarConexion();
	}		
}
