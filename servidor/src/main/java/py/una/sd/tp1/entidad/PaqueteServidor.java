package py.una.sd.tp1.entidad;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
public class PaqueteServidor{
	private ArrayList <String> listaip;
	private MensajeServidor sms;
	private String solicitud; //Para pedir al servidor hacer algo
	private ErrorServidor error; 
	
	public PaqueteServidor() {
		this.listaip 	= new ArrayList<String>();
		this.sms     	= new MensajeServidor();
		this.solicitud	= new String("");
		this.error      = new ErrorServidor(); 
	}
	
	public ErrorServidor getError() {
		return error;
	}

	public void setError(ErrorServidor error) {
		this.error = error;
	}

	public String getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}
	
	public ArrayList<String> getListaip() {
		return listaip;
	}

	public void setListaip(ArrayList<String> listaip) {
		this.listaip = listaip;
	}

	public MensajeServidor getSms() {
		return sms;
	}

	public void setSms(MensajeServidor sms) {
		this.sms = sms;
	}
	
	
	
	//Para trabajar con el envio de errores
	/*Convierte un objetoJson a StringJson
	 * Recibe un objeto de tipo ErrorServidor
	 * */
	public String objetoStringError(ErrorServidor e) {	
		JSONObject obj = new JSONObject();
		obj.put("estado", e.getEstado());
		obj.put("mensaje", e.getMensaje_error());
		return obj.toJSONString();
	}
	
	//Convierte un StringJson a objetoJson del objeto ErrorServidor
	public void stringObjetoError(String str) throws Exception {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(str.trim());
		JSONObject jsonObject = (JSONObject) obj;
		error.setEstado((Integer)jsonObject.get("estado"));
		error.setMensaje_error((String)jsonObject.get("mensaje"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*Convierte un objetoJson a StringJson
	 * Recibe un objeto de tipo Mensaje
	 * Recibe una lista de ip
	 * */
	public String objetoStringChat(MensajeServidor sms, ArrayList<String> listaip) {	
		JSONObject obj = new JSONObject();
		obj.put("nick", sms.getNick());
		obj.put("ip", sms.getIp());
		obj.put("mensaje",sms.getMensaje());
		obj.put("destinatario",sms.getDestino());
		obj.put("solicitud",this.solicitud);
		obj.put("estado", sms.getEstado());
		obj.put("mensaje_error", sms.getMensaje_error());
		
		
		//JSONArray list = new JSONArray();
	   
		// mientras al iterador queda proximo juego
	/*	for(String temp: listaip ){
	    	list.add(temp);
	     } 
	     obj.put("conectados", list);*/
		return obj.toJSONString();
	}
	
	//Convierte un StringJson a objetoJson
	public void stringObjetoChat(String str) throws Exception {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(str.trim());
		JSONObject jsonObject = (JSONObject) obj;
		sms.setNick((String)jsonObject.get("nick"));
		sms.setIp((String)jsonObject.get("ip"));
		sms.setMensaje((String)jsonObject.get("mensaje"));
		sms.setDestino((String)jsonObject.get("destinatario"));  
		this.setSolicitud((String)jsonObject.get("solicitud"));
	//	sms.setEstado((String)jsonObject.get("estado"));
		sms.setMensaje_error((String)jsonObject.get("mensaje_error"));
	   /* JSONArray ip_con = (JSONArray) jsonObject.get("conectados");
	    Iterator<String> iterator = ip_con.iterator();
	    while (iterator.hasNext()) {
	       listaip.add(iterator.next());
	    }*/
	}
}