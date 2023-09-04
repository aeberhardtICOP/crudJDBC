package persistencia;
import logica.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ControladoraPersistencia {
	private ControladoraLogica controlLogic;
	Connection con;
	Statement st;
	ResultSet rs;
	
	public ControladoraPersistencia(ControladoraLogica controlLogic) {
		this.controlLogic=controlLogic;
	}
	
	
	public void abrirConexion() {
		try {
			String userName="postgres";
			String password="ale301201";
			String

			url="jdbc:postgresql://localhost:5432/personas";
			con = DriverManager.getConnection(url, userName, password);
			System.out.println("Conexión a la BD");
		}
		catch (Exception e) {
			System.out.println("Error en conexión ");
			System.out.println(e.getMessage());
		}
	}
	//Para cerrar la conexión una vez terminadas las consultas
	public void cerrarConexion() {
		try {
			con.close();
			System.out.println("Conexión cerrada");
		}
		catch (SQLException e) {
			System.out.println("Error al cerrar conexión");
		}
	}
//	public void mostrarDatosPersonas() {
//		try {
//			st =con.createStatement();
//			rs = st.executeQuery("SELECT p.id_persona, p.nombre, p.apellido,"
//					+ "g.id_genero, d.calle, d.numero, l.descripcion FROM persona p "
//					+ "JOIN domicilio d ON(p.id_domicilio=d.id_domicilio)"
//					+ "JOIN localidad l ON(d.id_localidad=l.id_localidad)"
//					+ "JOIN genero g ON(p.id_genero=g.id_genero)"
//					+ "ORDER BY id_persona DESC");
//			System.out.println("Tabla abierta");
//			try {
//			while (rs.next()) {
//				int id_persona = rs.getInt("id_persona");
//				String nombre = rs.getString("nombre");
//				String apellido = rs.getString("apellido");
//				String genero = rs.getString("genero");
//				String calle = rs.getString("calle");
//				int nro = rs.getInt("numero");
//				String descLocalidad = rs.getString("descripcion");
//				System.out.println("----------------------------");
//				System.out.println("ID: "+id_persona);
//				System.out.println("----------------------------");
//				System.out.println(nombre+" "+apellido+"\nGENERO: "+genero);
//				System.out.println("DOMICILIO: \n"+calle+" "+nro+"\n"+descLocalidad);
//			}
//			} catch (Exception e) {
//				System.out.println("Error al visualizar datos");
//			}	
//			
//		}catch (SQLException e) {
//			System.out.println("Error al Abrir tabla ");
//			e.printStackTrace();
//		}
//	}
	//BUSCAR EN ARRAYS:
	//BUSCAR EN LOCALIDADES POR ID:
	
	//TRAER COLECCIONES
	public ArrayList<Genero> traerGeneros() {
		ArrayList<Genero>generos=new ArrayList();
		try {
			st=con.createStatement();
			rs=st.executeQuery("SELECT id_genero, nombre, abreviatura FROM genero;");
			while (rs.next()) {
				int id_genero=rs.getInt("id_genero");
				String nombre = rs.getString("nombre");
				char abreviatura = rs.getString("abreviatura").charAt(0);
				generos.add(new Genero(id_genero, nombre, abreviatura));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generos;
	}
		public ArrayList<Localidad> traerLocalidades() {
			ArrayList<Localidad>localidades = new ArrayList();
			try {
				st=con.createStatement();
				rs=st.executeQuery("SELECT id_localidad, codigo_postal, descripcion FROM localidad;");
				while (rs.next()) {
					int id_localidad=rs.getInt("id_localidad");
					int codigo_postal = rs.getInt("codigo_postal");
					String descripcion = rs.getString("descripcion");
					localidades.add(new Localidad(id_localidad, codigo_postal, descripcion));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return localidades;
	}
		
		//TRAER LISTA PERSONAS / DOMICILIOS
			public ArrayList<Persona>  traerPersonas() {
				ArrayList<Persona>personas = new ArrayList();
				try {
					st =con.createStatement();
					rs = st.executeQuery("SELECT p.id_persona, p.nombre AS nombrePersona, p.apellido,"
							+ "g.id_genero, g.nombre, g.abreviatura, d.id_domicilio,"
							+ "d.calle, d.numero, l.id_localidad, l.codigo_postal,l.descripcion FROM persona p "
							+ "JOIN domicilio d ON(p.id_domicilio=d.id_domicilio)"
							+ "JOIN localidad l ON(d.id_localidad=l.id_localidad)"
							+ "JOIN genero g ON(p.id_genero=g.id_genero)"
							+ "ORDER BY id_persona ASC");
					while(rs.next()) {
						int id_persona = rs.getInt("id_persona");
						String nombre = rs.getString("nombrePersona");
						String apellido = rs.getString("apellido");
						int id_genero = rs.getInt("id_genero");
						String nombreGen = rs.getString("nombre");
						char abreviatura = rs.getString("abreviatura").charAt(0);
						int id_domicilio = rs.getInt("id_domicilio");
						String calle = rs.getString("calle");
						int nro = rs.getInt("numero");
						int id_localidad = rs.getInt("id_localidad");
						String descLocalidad = rs.getString("descripcion");
						int codigo_postal = rs.getInt("codigo_postal");
						Localidad loc;
						if(controlLogic.buscarEnLocalidades(id_localidad)!=-1) {
							loc = controlLogic.traerDeLocalidades(id_localidad);
						}else {
							loc = new Localidad(id_localidad,codigo_postal,descLocalidad);
							controlLogic.agregarALocalidades(loc);
						}
						Genero gen;
						if(controlLogic.buscarEnGeneros(id_genero)!=-1) {
							gen = controlLogic.traerDeGeneros(id_localidad);
						}else {
							gen = new Genero(id_genero, nombreGen, abreviatura);
							controlLogic.agregarAGeneros(gen);
						}
						Domicilio dom = new Domicilio(id_domicilio,calle,nro,loc);
						//controlLogic.agregarADomicilios(dom);
						Persona pers = new Persona(id_persona,nombre,apellido,gen,dom);
						personas.add(pers);
					}
				} catch (SQLException e) {
					System.out.println("ERROR AL CARGAR PERSONAS EN MEMORIA :(");
					e.printStackTrace();
				}
			return personas;
			}
	//DOMICILIO
			public ArrayList<Domicilio> traerDomicilios() {
				ArrayList<Domicilio>domicilios = new ArrayList();
				try {
					st =con.createStatement();
					rs = st.executeQuery("SELECT d.id_domicilio, d.calle, d.numero, l.id_localidad, l.codigo_postal, l.descripcion "
							+ "FROM domicilio d JOIN localidad l ON(d.id_localidad=l.id_localidad);");
					while(rs.next()) {
						int id_domicilio=rs.getInt("id_domicilio");
						String calle = rs.getString("calle");
						int nro = rs.getInt("numero");
						int id_localidad = rs.getInt("id_localidad");
						String descLocalidad = rs.getString("descripcion");
						int codigo_postal = rs.getInt("codigo_postal");
						Localidad loc;
						if(controlLogic.buscarEnLocalidades(id_localidad)!=-1) {
							loc = controlLogic.traerDeLocalidades(id_localidad);
						}else {
							loc = new Localidad(id_localidad,codigo_postal,descLocalidad);
							controlLogic.agregarALocalidades(loc);
						}
						Domicilio dom = new Domicilio(id_domicilio,calle,nro,loc);
						domicilios.add(dom);
						
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				return domicilios;	
			}
	
	//CARGA DE PERSONAS
	public void cargarPersona(Persona persona) throws SQLException {
		try {
		 st=con.createStatement();
		 if(controlLogic.buscarEnDomicilios(persona.getDomicilio().getId_domicilio())==-1) {
			 st.executeUpdate("INSERT INTO domicilio (id_domicilio,  calle, numero, id_localidad) VALUES('"+
			 			persona.getDomicilio().getId_domicilio()+"', '"+persona.getDomicilio().getCalle()+
			 			"', '"+persona.getDomicilio().getNumero()+"', '"+persona.getDomicilio().getLocalidad().getId_localidad()+"');");
			 controlLogic.agregarADomicilios(persona.getDomicilio());
		 }
		 st.executeUpdate("INSERT INTO persona (id_persona, nombre, apellido, id_genero, id_domicilio) VALUES('"
				 		+persona.getId_persona()+"', '"+persona.getNombre()+"', '"+persona.getApellido()+"', '"
				 		+persona.getGenero().getId_genero()+"', '"+persona.getDomicilio().getId_domicilio()+"');");
		 controlLogic.agregarAPersonas(persona);
		 System.out.println("PERSONA CARGADA!");
		}catch(SQLException e) {
			System.out.println("ERROR AL CARGAR PERSONA :(");
			e.printStackTrace();
		}
	}
	//EDITAR PERSONA:
	public void editarPersona(Persona persona) {
		try {
			if (controlLogic.buscarEnDomicilios(persona.getDomicilio().getId_domicilio())==-1) {
				st.executeUpdate("INSERT INTO domicilio (id_domicilio,  calle, numero, id_localidad) VALUES('"+
				 persona.getDomicilio().getId_domicilio()+"', '"+persona.getDomicilio().getCalle()+
				 "', '"+persona.getDomicilio().getNumero()+"', '"+persona.getDomicilio().getLocalidad().getId_localidad()+"');");
				controlLogic.agregarADomicilios(persona.getDomicilio());
				}
				
			st.executeUpdate("UPDATE persona SET nombre='"+ persona.getNombre() +
					"', apellido='"+persona.getApellido()+"', id_genero= '"+persona.getGenero().getId_genero()+"', id_domicilio ='"+persona.getDomicilio().getId_domicilio()
					+ "' WHERE id_persona = '" + persona.getId_persona() + "';");
			int pos=controlLogic.buscarEnPersonas(persona.getId_persona());
			//System.out.println("***"+controlLogic.traerDePersonas(pos).getGenero().getNombre() +"***");
			System.out.println("**********************");
			System.out.println("POSICION "+pos);
			System.out.println("**********************");
			System.out.println("ANTES DE ELIMINAR:");
			System.out.println("**********************");
			controlLogic.mostrarPersonasDet();
			controlLogic.eliminarDePersonas(controlLogic.buscarEnPersonas(pos));
			System.out.println("**********************");
			System.out.println("DESPUES DE ELIMINAR");
			System.out.println("**********************");
			controlLogic.mostrarPersonasDet();;
			controlLogic.agregarAPersonas(persona, pos);
			System.out.println("PERSONA MODIFICADA CORRECTAMENTE!");
		}catch (SQLException e)
		{
			System.out.println("ERROR AL MODIFICAR PERSONA :(");
			e.printStackTrace();
		}
	}
	//EDITAR DOMICILIO
	public void editarDomicilio(Domicilio domicilio) {
		try {
			if (controlLogic.buscarEnDomicilios(domicilio.getId_domicilio())==-1) {
				st.executeUpdate("INSERT INTO domicilio (id_domicilio,  calle, numero, id_localidad) VALUES('"+
			 			domicilio.getId_domicilio()+"', '"+domicilio.getCalle()+
			 			"', '"+domicilio.getNumero()+"', '"+domicilio.getLocalidad().getId_localidad()+"');");
				controlLogic.agregarADomicilios(domicilio);
			}
			st.executeUpdate("UPDATE domicilio SET calle='"+ domicilio.getCalle() +
					"', numero='"+domicilio.getNumero()+"', id_localidad= '"+domicilio.getLocalidad().getId_localidad()
					+ "' WHERE id_domicilio = '" + domicilio.getId_domicilio() + "';");
			int pos=controlLogic.buscarEnDomicilios(domicilio.getId_domicilio());
			//System.out.println("###POSICION"+ pos);
			//System.out.println("###"+controlLogic.traerDeDomicilios(pos).getCalle()+" "+controlLogic.traerDeDomicilios(pos).getNumero());
			controlLogic.eliminarDeDomicilios(controlLogic.traerDeDomicilios(controlLogic.buscarEnDomicilios(domicilio.getId_domicilio())));
			//System.out.println("///"+domicilio.getCalle()+" "+domicilio.getNumero());
			controlLogic.agregarADomicilios(domicilio, pos);
			System.out.println("DOMICILIO MODIFICADO CORRECTAMENTE!");
			
		}catch(SQLException e) {
		System.out.println("ERROR AL MODIFICAR DOMICILIO");	
		e.printStackTrace();
		}
		
	}
	//BORRAR PERSONA MEDIANTE SU ID:
	public void borrarPersona(int id){
		try{
			controlLogic.eliminarDePersonas(controlLogic.traerDePersonas(id));;
			Statement s2=con.createStatement();
			s2.executeUpdate(

					"DELETE FROM persona WHERE id_persona ="+id+";");

			System.out.println("Elemento Borrado");
		}catch(SQLException e)
		{
			System.out.println("Error al Borrar");
			e.printStackTrace();
		}
	}
	//BORRAR DOMICILIO MEDIANTE SU ID:
	public void borrarDomicilio(int id){
		try{
			controlLogic.eliminarDeDomicilios(controlLogic.traerDeDomicilios(id));;
			Statement s2=con.createStatement();
			s2.executeUpdate(

					"DELETE FROM domicilio WHERE id_domicilio ="+id+";");

			System.out.println("Elemento Borrado");
		}catch(SQLException e)
		{
			System.out.println("Error al Borrar");
			e.printStackTrace();
		}
	}
	
	//METADATA:::
	
	public void bd() throws SQLException{
		DatabaseMetaData dbMet = con.getMetaData();
		if (dbMet==null)
			System.out.println("No hay información de MetaData");
		else {
			System.out.println("Tipo de la BD: " + dbMet.getDatabaseProductName());
			System.out.println("Versión : " + dbMet.getDatabaseProductVersion());
			System.out.println("Cantidad máxima de conexiones activas: " +

			dbMet.getMaxConnections());
		}
	}
	public void estructuraTabla(String strTbl) {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from " + strTbl);
			//Obtiene el metadata del ResultSet
			ResultSetMetaData rsmeta = rs.getMetaData();
			//Obtiene la cantidad de columnas del ResultSet
			int col = rsmeta.getColumnCount();
			for (int i = 1; i <= col; i++) {
				System.out.println("Campo " +

			//Devuelve el nombre del campo i
			rsmeta.getColumnLabel(i) + "\t"
			//Devuelve el tipo del campo i
			+ "Tipo: " + rsmeta.getColumnTypeName(i));

			}
		}
		catch (Exception e) {
			System.out.println("Error en Metadata ");
		}
	}
	public void verCualquierTabla(String strTbl) {

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from " + strTbl);
			ResultSetMetaData meta = rs.getMetaData();
			int col = meta.getColumnCount();
			//Mientras haya registros
			while (rs.next()) {
				for (int i = 1; i <= col; i++) {
					//Mostrar el dato del campo i
					System.out.print(rs.getString(i)
							+ "\t");

				}
				System.out.println("");
			}
		}catch (Exception e) {
			System.out.println("Cualquier " + e.toString());
		}
	}
//GETTERS Y SETTERS
	public void setControladoraLogica(ControladoraLogica contlog) {
		this.controlLogic=contlog;
	}
}
	