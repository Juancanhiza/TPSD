package py.una.sd.tp1.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

import py.una.sd.tp1.entidad.MensajeServidor;


public class MensajeDAO { //Clase que representa a la tabla mensaje de la base de datos
	
	public List<MensajeServidor> seleccionar() {
		//creamos el query que hace la consulta a la base de datos
		String query = "SELECT idmensaje, emisor, receptor, ipenvio, mensaje, fecha, error, estado FROM mensaje";
		//creamos una lista que contendra los mensajes guardados en la base de datos
		List<MensajeServidor> lista = new ArrayList<MensajeServidor>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect(); //hacemos conexion con la bd
        	ResultSet rs = conn.createStatement().executeQuery(query);

        	while(rs.next()) { 
        		//creamos un objeto mensaje
        		MensajeServidor m = new MensajeServidor();
        		//guardamos en el objeto mensaje los datos que traemos de la bd
        		m.setNick(rs.getString(2));
        		m.setIp(rs.getString(4));
        		m.setMensaje(rs.getString(5));
        		m.setDestino(rs.getString(3));
        		m.setMensaje_error(rs.getString(7));
        		m.setEstado(rs.getInt(8));
        		//guardamos el objeto en la lista
        		lista.add(m);
        	}
        	
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}
	
	/*public List<Mensaje> seleccionarPorCedula(long cedula) {
		String SQL = "SELECT cedula, nombre, apellido FROM Mensaje WHERE cedula = ? ";
		
		List<Mensaje> lista = new ArrayList<Mensaje>();
		
		Connection conn = null; 
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
        	pstmt.setLong(1, cedula);
        	
        	ResultSet rs = pstmt.executeQuery();

        	while(rs.next()) {
        		Mensaje p = new Mensaje();
        		p.setCedula(rs.getLong(1));
        		p.setNombre(rs.getString(2));
        		p.setApellido(rs.getString(3));
        		
        		lista.add(p);
        	}
        	
        } catch (SQLException ex) {
            System.out.println("Error en la seleccion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
		return lista;

	}*/
	
    public long insertar(MensajeServidor m) throws SQLException {

        String SQL = "INSERT INTO mensaje(emisor, receptor, ipenvio, mensaje, fecha, error, estado) "
                + "VALUES(?,?,?,?,?,?,?)";
 
        int id = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, m.getNick());
            pstmt.setString(2, m.getDestino());
            pstmt.setString(3, m.getIp());
            pstmt.setString(4, m.getMensaje());
            pstmt.setString(5, dtf.format(now));
            pstmt.setString(6, m.getMensaje_error());
            pstmt.setInt(7, m.getEstado());
            
            System.out.println("asdasdasd");
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la insercion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        	
        return id;
    	
    	
    }
	

   /* public long actualizar(Mensaje p) throws SQLException {

        String SQL = "UPDATE mensaje SET nombre = ? , apellido = ? WHERE cedula = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, p.getNombre());
            pstmt.setString(2, p.getApellido());
            pstmt.setLong(3, p.getCedula());
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la actualizacion: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        return id;
    }
    
    public long borrar(long cedula) throws SQLException {

        String SQL = "DELETE FROM persona WHERE cedula = ? ";
 
        long id = 0;
        Connection conn = null;
        
        try 
        {
        	conn = Bd.connect();
        	PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setLong(1, cedula);
 
            int affectedRows = pstmt.executeUpdate();
            // check the affected rows 
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error en la eliminación: " + ex.getMessage());
        }
        finally  {
        	try{
        		conn.close();
        	}catch(Exception ef){
        		System.out.println("No se pudo cerrar la conexion a BD: "+ ef.getMessage());
        	}
        }
        return id;
    }*/

}

