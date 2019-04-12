package py.una.sd.tp1.entidad;


/*
 * La clase sirve para recibir información de las personas
 * que están conectadas en el servidor
 * 
 * 
 * Esta clase tiene 4 atributos
 *  private String user;          nombre del usuario
 *	private int disponibilidad;   1:disponible  , 0:no disponible
 *	private int estado;           respuesta del servidor
 *	private String mensaje_error; mensaje de error del servidor
 *  
 *  tiene metodos getters y setters con respecto a los atributos ya mencionados
 * */

public class Persona_disponible {
	
	/*Atributos para el envio/recibo de información del servidor*/
	private String user;          //nombre del usuario
	private int disponibilidad;   //1:disponible  , 0:no disponible
	
	/*Atributos para saber el estado de la respuesta del Api*/
	private int estado;
	private String mensaje_error;
	
	/*Constructor de Persona_disponible*/
	public Persona_disponible() {
		this.user      		= "";
		this.disponibilidad = 0;
		this.estado		   	= 0;
		this.mensaje_error 	= "";
	}
	
	//Metodos getters y setters
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getMensaje_error() {
		return mensaje_error;
	}
	public void setMensaje_error(String mensaje_error) {
		this.mensaje_error = mensaje_error;
	}
	
}
