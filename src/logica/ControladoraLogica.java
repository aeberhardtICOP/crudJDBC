package logica;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import persistencia.*;
public class ControladoraLogica {
	private ControladoraPersistencia controlPersis;
	private HashMap<Integer,Localidad>localidades=new HashMap();
	private HashMap<Integer,Genero>generos=new HashMap();
	private HashMap<Integer,Domicilio>domicilios=new HashMap();
	private HashMap<Integer,Persona>personas=new HashMap();
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
				mostrarPersonasDet();
				System.out.println("INGRESE ID DE LA PERSONA QUE DESEA ELIMINAR:");
				id=scanner.nextInt();
			}
			int idDom=personas.get(id).getDomicilio().getId_domicilio();
			String call= personas.get(id).getDomicilio().getCalle();
			int nro = personas.get(id).getDomicilio().getNumero();
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
	//CARGAR LOCALIDAD
	public void cargarLocalidad() {
		Localidad loc = new Localidad();
		try {
			controlPersis.cargarLocalidad(loc);
		} catch (SQLException e) {
			System.out.println("ERROR AL CARGAR LOCALIDAD :(");
			e.printStackTrace();
		}
	}
	//CARGAR GENERO
	public void cargarGenero() {
		Genero gen = new Genero();
		try {
			controlPersis.cargarGenero(gen);
		}catch (SQLException e) {
			System.out.println("ERROR AL CARGAR GENERO :(");
			e.printStackTrace();
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
				personas.get(id).mostrar();
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
					System.out.println("MODIFICAR NOMBRE");
					System.out.println("******************");
					System.out.println("El nombre actual es: "+personas.get(id).getNombre());
					System.out.println("Ingrese el nuevo nombre: ");
					String nombre=scanner.nextLine();
					personas.get(id).setNombre(nombre);
					System.out.println("NOMBRE ACTUALIZADO!!!");
					controlPersis.editarPersona(personas.get(id));
					break;
				case 2:
					scanner.nextLine();
					System.out.println("MODIFICAR APELLIDO");
					System.out.println("******************");
					System.out.println("El apellido actual es: "+personas.get(id).getApellido());
					System.out.println("Ingrese el nuevo apellido: ");
					String apellido=scanner.nextLine();
					personas.get(id).setApellido(apellido);
					System.out.println("APELLIDO ACTUALIZADO!!!");
					controlPersis.editarPersona(personas.get(id));
					break;
				case 3:
					scanner.nextLine();
					System.out.println("MODIFICAR ID");
					System.out.println("******************");
					System.out.println("El ID actual es: "+personas.get(id).getId_persona());
					System.out.println("Ingrese el nuevo ID: ");
					int id_pers=scanner.nextInt();
					personas.get(id).setId_persona(id_pers);
					System.out.println("ID ACTUALIZADO!!!");
					controlPersis.editarPersona(personas.get(id));
					id=id_pers;
					break;
				case 4:
					scanner.nextLine();
					System.out.println("MODIFICAR GENERO");
					System.out.println("******************");
					System.out.println("El genero actual es: "+personas.get(id).getGenero().getNombre());
					System.out.println("Ingrese el nuevo genero: ");
					mostrarGeneros();
					int id_gen=scanner.nextInt();
					personas.get(id).setGenero(generos.get(id_gen));
					System.out.println("GENERO ACTUALIZADO!!!");
					System.out.println("---"+generos.get(id_gen).getNombre() +"---");
					controlPersis.editarPersona(personas.get(id));
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
			Domicilio dom = personas.get(id).getDomicilio();
			System.out.println("El domicilio actual es: ");
			personas.get(id).mostrar();
			System.out.println("Ingrese que desea modificar: \n1) Calle\n2) Numero \n3) Localidad\n-1) Dejar de modificar");
			opcion = scanner.nextInt();
			switch(opcion) {
			case 1:
				scanner.nextLine();
				System.out.println("MODIFICAR DOMICILIO = CALLE");
				System.out.println("******************");
				System.out.println("Ingrese calle! Actual: " +dom.getCalle());
				String calle=scanner.nextLine();
				dom.setCalle(calle);
				controlPersis.editarDomicilio(dom);
				personas.get(id).setDomicilio(dom);
				controlPersis.editarPersona(personas.get(id));
				System.out.println("DOMICILIO EDITADO");
				break;
			case 2:
				scanner.nextLine();
				System.out.println("MODIFICAR DOMICILIO = NUMERO");
				System.out.println("******************");
				System.out.println("Ingrese Nro! Actual: " +dom.getNumero());
				int num=scanner.nextInt();
				dom.setNumero(num);
				controlPersis.editarDomicilio(dom);
				personas.get(id).setDomicilio(dom);
				controlPersis.editarPersona(personas.get(id));
				System.out.println("DOMICILIO EDITADO");
				break;
			case 3:
				scanner.nextLine();
				System.out.println("MODIFICAR DOMICILIO = LOCALIDAD");
				System.out.println("******************");
				System.out.println("Ingrese opcion! Actual: " +dom.getLocalidad().getDescripcion());
				mostrarLocalidades();
				int o=scanner.nextInt();
				dom.setLocalidad(localidades.get(o));
				controlPersis.editarDomicilio(dom);
				personas.get(id).setDomicilio(dom);
				controlPersis.editarPersona(personas.get(id));
				System.out.println("DOMICILIO EDITADO");
				break;
			case -1: 
				System.out.println("SALIENDO DE EDICION DE DOMICILIO....");
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
/*public int buscarEnPersonas(int id) {
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
}*/
//HASHMAP CONTAINS
public boolean personasContains(int key) {
	if(personas.containsKey(key)) {
		return true;
	}
	else {
		return false;
	}
}
public boolean generosContains(int key) {
	if(generos.containsKey(key)) {
		return true;
	}
	else {
		return false;
	}
}
public boolean localidadesContains(int key) {
	if(localidades.containsKey(key)) {
		return true;
	}
	else {
		return false;
	}
}
public boolean domiciliosContains(int key) {
	if(domicilios.containsKey(key)) {
		return true;
	}
	else {
		return false;
	}
}

//BUSQUEDA POR KEY - DEVOLVIENDO VALUE
//LOCALIDADES
public Localidad traerDeLocalidades(int key) {
	return localidades.get(key);
}
//BUSCAR EN GENERO POR ID
public Genero traerDeGeneros(int key) {
	return generos.get(key);
}
//BUSCAR EN PERSONAS POR ID
public Persona traerDePersonas(int key) {
	return personas.get(key);
}
public Domicilio traerDeDomicilios(int key) {
	return domicilios.get(key);
}
//MOSTRAR HASHMAPS:
//MOSTRAR PERSONAS
public void mostrarPersonas() {
	for(Integer key : personas.keySet()) {
		System.out.println(key+") "+personas.get(key).getNombre()+
				" "+personas.get(key).getApellido()
				+"/ID: "+personas.get(key).getId_persona());
	}
}
//MOSTRAR PERSONAS: DETALLADO
public void mostrarPersonasDet() {
	for(Integer key : personas.keySet()) {
		System.out.println("ID: "+personas.get(key).getId_persona()+") "+personas.get(key).getNombre()+
				" "+personas.get(key).getApellido()
				+"\nGENERO: (ID "+personas.get(key).getGenero().getId_genero()+")"+personas.get(key).getGenero().getNombre()
				+"\nDOMICILIO: (ID  "+personas.get(key).getDomicilio().getId_domicilio()+")"
				+"\n"+personas.get(key).getDomicilio().getCalle()+" "+personas.get(key).getDomicilio().getNumero()
				+"\nLOCALIDAD: (ID "+personas.get(key).getDomicilio().getLocalidad().getId_localidad()+")"
				+"\n"+personas.get(key).getDomicilio().getLocalidad().getDescripcion()+" "+personas.get(key).getDomicilio().getLocalidad().getCodigo_postal());
		System.out.println("-----------------------------------");
	}
}
//MOSTRAR GENEROS
public void mostrarGeneros() {
	for(Integer key : generos.keySet()) {
		System.out.println(key+") "+generos.get(key).getAbreviatura()
				+"/"+generos.get(key).getNombre()
				+"/ID: "+generos.get(key).getId_genero());
	}
}

//MOSTRAR LOCALIDADES
public void mostrarLocalidades() {
	for(Integer key : localidades.keySet()) {
		System.out.println(key+") "+localidades.get(key).getDescripcion()
				+"/"+localidades.get(key).getCodigo_postal()
				+"/ID: "+localidades.get(key).getId_localidad());
	}
}

//MOSTRAR DOMICILIOS
public void mostrarDomicilios() {
	for(Integer key : domicilios.keySet()) {
		System.out.println(key+") "+domicilios.get(key).getCalle()
				+" "+domicilios.get(key).getNumero()
				+"/"+domicilios.get(key).getLocalidad().getDescripcion() 
				+"/ID: "+domicilios.get(key).getId_domicilio());
	}
}


//AGREGAR A HASHMAP
//LOCALIDADES
public void agregarALocalidades(Localidad loc, int id) {
	this.localidades.put(id,loc);
}
//GENEROS
public void agregarAGeneros(Genero gen, int id) {
	this.generos.put(id, gen);
}
//DOMICILIOS
public void agregarADomicilios(Domicilio dom, int id) {
	this.domicilios.put(id,dom);
}
//PERSONAS
public void agregarAPersonas(Persona pers, int id) {
	this.personas.put(id, pers);
}

//ELIMINAR DE HASMAP
//LOCALIDADES
public void eliminarDeLocalidades(int key) {
	this.localidades.remove(key);
}
//GENEROS
public void eliminarDeGeneros(int key) {
	this.generos.remove(key);
}
//DOMICILIOS
public void eliminarDeDomicilios(int key) {
	this.domicilios.remove(key);
}
//PERSONAS
public void eliminarDePersonas(int key) {
	this.personas.remove(key);
}

//GETTERS Y SETTERS
public void setControladoraPersistencia(ControladoraPersistencia contrpers) {
	this.controlPersis=contrpers;
}
public void verMetadataBD() throws SQLException {
	System.out.println("Ingrese opcion: \n1) Ver caracteristicas BD\n2) Ver Estructura de tabla \n3) Ver cualquier tabla");
	int opcion = scanner.nextInt();
	switch(opcion) {
	case 1:
		controlPersis.bd();
		break;
	case 2:
		controlPersis.estructuraTabla("localidad");
		System.out.println("--------------------");
		controlPersis.estructuraTabla("genero");
		System.out.println("--------------------");
		controlPersis.estructuraTabla("domicilio");
		System.out.println("--------------------");
		controlPersis.estructuraTabla("persona");
		break;
	case 4:
		controlPersis.verCualquierTabla("localidad");
		System.out.println("--------------------");
		controlPersis.verCualquierTabla("genero");
		System.out.println("--------------------");
		controlPersis.verCualquierTabla("domicilio");
		System.out.println("--------------------");
		controlPersis.verCualquierTabla("persona");
		break;
		
	}
}
}


