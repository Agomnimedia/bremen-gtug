package org.gtugs.bremen.eventmanagement.android.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.bremen.eventmanagement.android.persistence.PersistUpdate;
import org.gtugs.bremen.eventmanagement.android.server.Update;
import org.json.JSONException;
import org.json.JSONObject;


public class LastUpdateServlet extends HttpServlet{

	/**
	 * generated serialVersionUID.
	 */
	private static final long serialVersionUID = 8296690224144611226L;
	
	private static final String KIND = "kind";
	
//	private static final String ERROR = "error";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		final int kind = Integer.parseInt(req.getParameter(KIND));
		final JSONObject result = new JSONObject();
		final String jsonKey = KIND + "_" + kind;
		
		switch(kind){
			case Update.ALL_EVENTS:
				try{
					final Update update = new PersistUpdate().findLastUpdate(kind);
					result.put(jsonKey, this.parseUpdate2JSOObjectLight(update));
				}catch(JSONException je){
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			break;
			default:
				// TODO return 'unknown kind'
		}
		resp.setContentType("text/plain");
		resp.getWriter().append(result.toString());
	}
	
	public JSONObject parseUpdate2JSOObjectLight(final Update update) throws JSONException{
		final JSONObject result = new JSONObject();
		result.put("timestamp", update.getDate().getTime());
		result.put("additionalInfo", update.getAdditionalInfo());
		return result;
	}
}