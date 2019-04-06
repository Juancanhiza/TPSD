package py.una.sd.tp1.entidad;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.Serializable;
public class PaqueteEnvio{
	private Mensaje sms;

	public Mensaje getSms() {
		return sms;
	}

	public void setSms(Mensaje sms) {
		this.sms = sms;
	}
	
	
	//Convierte un objetoJson a StringJson
	public static String objetoString(Mensaje sms) {	
		JSONObject obj = new JSONObject();
		obj.put("nick", sms.getNick());
		obj.put("ip", sms.getIp());
		obj.put("mensaje",sms.getMensaje());
		obj.put("destinatario",sms.getDestino());
		return obj.toJSONString();
	}
	
	//Convierte un StringJson a objetoJson
	 public Mensaje stringObjeto(String str) throws Exception {
	    Mensaje p = new Mensaje();
	    JSONParser parser = new JSONParser();
	    Object obj = parser.parse(str.trim());
	    JSONObject jsonObject = (JSONObject) obj;
	    p.setNick((String)jsonObject.get("nick"));
	    p.setIp((String)jsonObject.get("ip"));
	    p.setMensaje((String)jsonObject.get("mensaje"));
	    p.setDestino((String)jsonObject.get("destinatario"));   
	    return p;
	}
}