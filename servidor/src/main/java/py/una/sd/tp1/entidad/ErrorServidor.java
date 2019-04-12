package py.una.sd.tp1.entidad;
public class ErrorServidor {
		private String mensaje_error;
		private int estado;
		private String solicitud; //Para pedir al servidor hacer algo
		
		public ErrorServidor() {
			this.mensaje_error 	= new String("");
			this.solicitud 	= new String("control_error");
			this.estado			= 0;
		}
		
		public String getSolicitud() {
			return solicitud;
		}

		public void setSolicitud(String solicitud) {
			this.solicitud = solicitud;
		}
 
		public String getMensaje_error() {
			return mensaje_error;
		}
		public void setMensaje_error(String mensaje_error) {
			this.mensaje_error = mensaje_error;
		}
		public int getEstado() {
			return estado;
		}
		public void setEstado(int estado) {
			this.estado = estado;
		}
		
}

