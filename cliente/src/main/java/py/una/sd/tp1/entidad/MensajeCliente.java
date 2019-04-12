package py.una.sd.tp1.entidad;

import org.json.simple.JSONObject;


/**
 * Atributos para el manejo de mensajes
 * - Nick	 : Nombre del emisor
 * - ip  	 : direccion del receptor con quien desea conectarse
 * - mensaje : Mensaje que se transmite entre emisor y receptor
 * - destino : Nombre del receptor(Opcional porque ya tenemos ip)
 * 
 * Atributos para el informe de errores
 * - mensaje_error:
◦	 Palabra “ok” si no existe error.
◦ 	 El detalle el error si existe.

 * - Estado, donde:
◦ 	"0" corresponde a una transacción exitosa.
◦ 	"-1" transacción indeterminada.
◦ 	Mayor a "1" un código de error o mensaje relacionado a la transacción.
 * */
public class MensajeCliente {
	//Atributos
	private String nick,ip,mensaje,destino;
	private String mensaje_error;
	private Integer estado;
	
	//Constructor
	public MensajeCliente() {
		this.nick    		= new String("");
		this.ip      		= new String("");
		this.mensaje 		= new String("");
		this.destino 		= new String("");
		this.mensaje_error 	= new String("");
		this.estado			= 0;
	}
	
	
	//Metodos Getters y Setters
	public String getMensaje_error() {
		return mensaje_error;
	}

	public void setMensaje_error(String mensaje_error) {
		this.mensaje_error = mensaje_error;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
		
}
