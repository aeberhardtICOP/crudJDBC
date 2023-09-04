package logica;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import persistencia.*;
public class ControladoraLogica {
	private ControladoraPersistencia controlPersis;
	private ArrayList<Localidad>localidades=new ArrayList();
	private ArrayList<Genero>generos=new ArrayList();
	private ArrayList<Domicilio>domicilios=new ArrayList();
	private ArrayList<Persona>personas=new ArrayList();
	Scanner scanner = new Scanner(System.in);
	
	public ControladoraLogica(ControladoraPersistencia controlPersis) {
		this.controlPersis=controlPersis;
	}
	
	//ABRIR Y CERRAR CONEXION
	public void abrirConexion() {
		controlPersis.abrirConexion();
	}
	
	public void cerrarConexion() {
		controlPersis.cerrarConexion();
	}
	//TRAER A MEMORIA LISTAS
	public void traerAMemoria() {
		try {
			this.localidades=controlPersis.traerLocalidades();
			this.generos=controlPersis.traerGeneros();
			this.domicilios=controlPersis.traerDomicilios();
			this.personas=controlPersis.traerPersonas();
			System.out.println("DATOS EN MEMORIA!");
		}catch(Exception e) {
			System.out.println("ERROR AL TRAER DATOS A MEMORIA :(");
			e.getCause();
			e.printStackTrace();
		}
	}
	//CARGA DE PERSONA
	public void cargarPersona() {
		Persona perso = new Persona();
		Domicilio dom = new Domicilio();
		System.out.println("GENERO:");
		System.out.println("****************");
		System.out.println("SELECCIONE UNA OPCION");
		mostrarGeneros();
		try {
			int indiceGen = scanner.nextInt();
			perso.setGenero(generos.get(indiceGen));
		}catch(InputMismatchException e) {
			System.out.println("No ingreso un numero!");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("No existe ese indice!");
		}
		System.out.println("LOCALIDAD:");
		System.out.println("****************");
		System.out.println("SELECCIONE UNA OPCION");
		mostrarLocalidades();
		try {
			int indice = scanner.nextInt();
			dom.setLocalidad(localidades.get(indice));
			perso.setDomicilio(dom);
			controlPersis.cargarPersona(perso);
		}catch(InputMismatchException e) {
			System.out.println("No ingreso un numero!");
		}catch(IndexOutOfBoundsException ex) {
			System.out.println("No existe ese indice!");
		}catch(SQLException e){
			System.out.println("Error de base de datos :(");
		}
}
	//ELIMINAR PERSONA
	public void eliminarPersona() {
		System.out.println("------------------");
		System.out.println("ELIMINAR PERSONA");
		System.out.println("*******************");
		System.out.println("INGRESE ID DE LA PERSONA QUE DESEA ELIMINAR: "
				+ "(INGRESE -1 SI DESEA VER LA LISTA DE LAS PERSONAS REGISTRADAS)");
		try {
			int id=scanner.nextInt();
			if(id==-1) {
				mostrarPersonas();
				System.out.println("INGRESE ID DE LA PERSONA QUE DESEA ELIMINAR:");
				id=scanner.nextInt();
			}
			int idDom=personas.get(buscarEnPersonas(id)).getDomicilio().getId_domicilio();
			String call= personas.get(buscarEnPersonas(id)).getDomicilio().getCalle();
			int nro = personas.get(buscarEnPersonas(id)).getDomicilio().getNumero();
			controlPersis.borrarPersona(id);
			System.out.println("DESEA BORRAR EL DOMICILIO REGISTRADO CON ESTA PERSONA? ("+call+" "+nro+")");
			System.out.println("1) SI\n2) NO");
			int op=scanner.nextInt();
			switch(op) {
			case 1:
				controlPersis.borrarDomicilio(idDom);
				break;
			case 2:
				break;
			}
		}catch(InputMismatchException e) {
			System.out.println("No ingreso un numero!");
		}catch(IndexOutOfBoundsException e){
			System.out.println("No existe elemento con tal id.");
		}
	}
	//EDITAR DATOS
	//EDITAR PERSONA
	public void editarPersona() {
		System.out.println("------------------");
		System.out.println("EDITAR PERSONA");
		System.out.println("*******************");
		System.out.println("INGRESE ID DE LA PERSONA QUE DESEA EDITAR: "
				+ "(INGRESE -1 SI DESEA VER LA LISTA DE LAS PERSONAS REGISTRADAS)");
		try {
			int opcion=0;
			int id=scanner.nextInt();
			if (id==-1) {
				mostrarPersonasDet();
				System.out.println("INGRESE ID DE LA PERSONA QUE DESEA EDITAR:");
				id=scanner.nextInt();	
			}
			while(opcion!=-1) {
				personas.get(buscarEnPersonas(id)).mostrar();
				System.out.println("Que desea modificar? (PRESIONE -1 PARA DEJAR DE MODIFICAR)"
						+ "\n1) Nombre"
						+ "\n2) Apellido"
						+ "\n3) Id"
						+ "\n4) Genero"
						+ "\n5) Domicilio");
				opcion=scanner.nextInt();
				switch(opcion) {
				case 1:
					scanner.nextLine();
					System.out.println("//ENTRANDO A MODIFICAR NOMBRE CONTROLOGIC");
					mostrarPersonasDet();
					System.out.println("MODIFICAR NOMBRE");
					System.out.println("******************");
					System.out.println("El nombre actual es: "+personas.get(buscarEnPersonas(id)).getNombre());
					System.out.println("Ingrese el nuevo nombre: ");
					String nombre=scanner.nextLine();
					personas.get(buscarEnPersonas(id)).setNombre(nombre);
					System.out.println("NOMBRE ACTUALIZADO!!!");
					controlPersis.editarPersona(personas.get(buscarEnPersonas(id)));
					System.out.println("###########################################");
					System.out.println("//FIN DE METODO CONTROLADORA LOGICA");
					mostrarPersonasDet();
					break;
				case 2:
					scanner.nextLine();
					System.out.println("MODIFICAR APELLIDO");
					System.out.println("******************");
					System.out.println("El apellido actual es: "+personas.get(buscarEnPersonas(id)).getApellido());
					System.out.println("Ingrese el nuevo apellido: ");
					String apellido=scanner.nextLine();
					personas.get(buscarEnPersonas(id)).setApellido(apellido);
					System.out.println("APELLIDO ACTUALIZADO!!!");
					controlPersis.editarPersona(personas.get(buscarEnPersonas(id)));
					break;
				case 3:
					scanner.nextLine();
					System.out.println("MODIFICAR ID");
					System.out.println("******************");
					System.out.println("El ID actual es: "+personas.get(buscarEnPersonas(id)).getId_persona());
					System.out.println("Ingrese el nuevo ID: ");
					int id_pers=scanner.nextInt();
					personas.get(buscarEnPersonas(id)).setId_persona(id_pers);
					System.out.println("ID ACTUALIZADO!!!");
					controlPersis.editarPersona(personas.get(buscarEnPersonas(id)));
					id=id_pers;
					break;
				case 4:
					scanner.nextLine();
					System.out.println("MODIFICAR GENERO");
					System.out.println("******************");
					System.out.println("El genero actual es: "+personas.get(buscarEnPersonas(id)).getGenero().getNombre());
					System.out.println("Ingrese el nuevo genero: ");
					mostrarGeneros();
					int indice=scanner.nextInt();
					personas.get(buscarEnPersonas(id)).setGenero(generos.get(buscarEnGeneros(indice)));
					System.out.println("GENERO ACTUALIZADO!!!");
					System.out.println("---"+generos.get(buscarEnGeneros(indice)).getNombre() +"---");
					controlPersis.editarPersona(personas.get(buscarEnPersonas(id)));
					System.out.println("LISTA DE PERSONAS EN CO>NTROLADORA LOGICA");
					mostrarPersonas();
					break;
				case 5:
					editarDomicilio(id);
					
					break;
				case -1:
					System.out.println("SALIENDO DE MODIFICACION DE PERSONA...");
					default:
						System.out.println("No ingreso opcion valida");
					break;
				}
			}
		}catch(InputMismatchException e) {
			System.out.println("No ingreso un id !");
		}catch(IndexOutOfBoundsException e) {
			System.out.println("No existe persona con ese id :(");
		}
		
	}
	//EDITAR DOMICILIO
	public void editarDomicilio(int id) {
		scanner.nextLine();
		System.out.println("MODIFICAR DOMICILIO");
		System.out.println("******************");
		int opcion=0;
		
		while(opcion!=-1) {
			Domicilio dom = personas.get(buscarEnPersonas(id)).getDomicilio();
			System.out.println("El domicilio actual es: ");
			personas.get(buscarEnPersonas(id)).mostrar();
			System.out.println("Ingrese que desea modificar: \n1) Calle\n2) Numero \n3) Localidad\n-1) Dejar de modificar");
			opcion = scanner.nextInt();
			switch(opcion) {
			case 1:
				scanner.nextLine();
				System.out.println("MODIFICAR DOMICILIO = CALLE");
				System.out.println("******************");
				System.out.println("Ingrese calle!");
				String calle=scanner.nextLine();
				dom.setCalle(calle);
				controlPersis.editarDomicilio(dom);
				System.out.println("###DOMICILIO EDITADO");
				personas.get(buscarEnPersonas(id)).setDomicilio(dom);
				controlPersis.editarPersona(personas.get(buscarEnPersonas(id)));
				break;
			}
		}
		
		
	}
	//BUSQUEDA POR ID - DEVOLVIENDO INDICE
	//LOCALIDADES
public int buscarEnLocalidades(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==localidades.get(i).getId_localidad()) {
			return i;
		}
		i++;
	}while(flag!=true&&i<localidades.size());
	return -1;
}
//BUSCAR EN GENERO POR ID
public int buscarEnGeneros(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==generos.get(i).getId_genero()) {
			return i;
		}
		i++;
	}while(flag!=true&&i<generos.size());
	return -1;
}
//BUSCAR EN PERSONAS POR ID
public int buscarEnPersonas(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==personas.get(i).getId_persona()) {
			return i;
		}
		i++;
	}while(flag!=true&&i<personas.size());
	return -1;
}
public int buscarEnDomicilios(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==domicilios.get(i).getId_domicilio()) {
			return i;
		}
		i++;
	}while(flag!=true&&i<domicilios.size());
	return -1;
}
//BUSQUEDA POR ID - DEVOLVIENDO INDICE
//LOCALIDADES
public Localidad traerDeLocalidades(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==localidades.get(i).getId_localidad()) {
			return localidades.get(i);
		}
		i++;
	}while(flag!=true&&i<localidades.size());
	return null;
}
//BUSCAR EN GENERO POR ID
public Genero traerDeGeneros(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==generos.get(i).getId_genero()) {
			return generos.get(i);
		}
		i++;
	}while(flag!=true&&i<generos.size());
	return null;
}
//BUSCAR EN PERSONAS POR ID
public Persona traerDePersonas(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==personas.get(i).getId_persona()) {
			return personas.get(i);
		}
		i++;
	}while(flag!=true&&i<personas.size());
	return null;
}
public Domicilio traerDeDomicilios(int id) {
	int i=0;
	boolean flag=false;
	do {
		if(id==domicilios.get(i).getId_domicilio()) {
			return domicilios.get(i);
		}
		i++;
	}while(flag!=true&&i<domicilios.size());
	return null;
}
//MOSTRAR ARRAYS:
//MOSTRAR PERSONAS
public void mostrarPersonas() {
	for(int i=0;i<personas.size();i++) {
		System.out.println(i+") "+personas.get(i).getNombre()+
				" "+personas.get(i).getApellido()
				+"/ID: "+personas.get(i).getId_persona());
	}
}
//MOSTRAR PERSONAS: DETALLADO
public void mostrarPersonasDet() {
	for(int i=0;i<personas.size();i++) {
		System.out.println("ID: "+personas.get(i).getId_persona()+") "+personas.get(i).getNombre()+
				" "+personas.get(i).getApellido()
				+"\nGENERO: (ID "+personas.get(i).getGenero().getId_genero()+")"+personas.get(i).getGenero().getNombre()
				+"\nDOMICILIO: (ID  "+personas.get(i).getDomicilio().getId_domicilio()+")"
				+"\n"+personas.get(i).getDomicilio().getCalle()+" "+personas.get(i).getDomicilio().getNumero()
				+"\nLOCALIDAD: (ID "+personas.get(i).getDomicilio().getLocalidad().getId_localidad()+")"
				+"\n"+personas.get(i).getDomicilio().getLocalidad().getDescripcion()+" "+personas.get(i).getDomicilio().getLocalidad().getCodigo_postal());
		System.out.println("-----------------------------------");
	}
}
//MOSTRAR GENEROS
public void mostrarGeneros() {
	for(int i=0;i<generos.size();i++) {
		System.out.println(i+") "+generos.get(i).getAbreviatura()
				+"/"+generos.get(i).getNombre()
				+"/ID: "+generos.get(i).getId_genero());
	}
}

//MOSTRAR LOCALIDADES
public void mostrarLocalidades() {
	for(int i=0;i<localidades.size();i++) {
		System.out.println(i+") "+localidades.get(i).getDescripcion()
				+"/"+localidades.get(i).getCodigo_postal()
				+"/ID: "+localidades.get(i).getId_localidad());
	}
}

//MOSTRAR DOMICILIOS
public void mostrarDomicilios() {
	for(int i=0;i<domicilios.size();i++) {
		System.out.println(i+") "+domicilios.get(i).getCalle()
				+" "+domicilios.get(i).getNumero()
				+"/"+domicilios.get(i).getLocalidad().getDescripcion() 
				+"/ID: "+domicilios.get(i).getId_domicilio());
	}
}


//AGREGAR A ARRAYLIST
//LOCALIDADES
public void agregarALocalidades(Localidad loc) {
	this.localidades.add(loc);
}
//GENEROS
public void agregarAGeneros(Genero gen) {
	this.generos.add(gen);
}
//DOMICILIOS
public void agregarADomicilios(Domicilio dom) {
	this.domicilios.add(dom);
}
public void agregarADomicilios(Domicilio dom, int pos) {
	this.domicilios.add(pos, dom);
}
//PERSONAS
public void agregarAPersonas(Persona pers) {
	this.personas.add(pers);
}
public void agregarAPersonas(Persona pers, int pos) {
	this.personas.add(pos, pers);
}

//ELIMINAR DE ARRAYLIST
//LOCALIDADES
public void eliminarDeLocalidades(Localidad loc) {
	this.localidades.remove(loc);
}
//GENEROS
public void eliminarDeGeneros(Genero gen) {
	this.generos.remove(gen);
}
//DOMICILIOS
public void eliminarDeDomicilios(Domicilio dom) {
	this.domicilios.remove(dom);
}
//PERSONAS
public void eliminarDePersonas(Persona pers) {
	this.personas.remove(pers);
}
public void eliminarDePersonas(int pos) {
	this.personas.remove(pos);
}

//GETTERS Y SETTERS
public void setControladoraPersistencia(ControladoraPersistencia contrpers) {
	this.controlPersis=contrpers;
}
}


