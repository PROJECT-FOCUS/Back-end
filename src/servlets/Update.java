package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.UpdateService;

/**
 * Servlet implementation class Update
 */
@WebServlet("/update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// convert request body into JSONobject "input"
			JSONObject input = JsonHelper.readJSONObject(request);
			// create JSONobject "output" to show if the update is successful or not
			JSONObject output = new JSONObject();
			UpdateService service = new UpdateService();
			if (service.verifyUpdate(input, output)) {
				output.put("status", "OK");			// append response status
			} else {
				output.put("status", "Update Expected Usage Time or App Category Failed");		// append response status
				response.setStatus(401);
			}
			JsonHelper.writeJsonObject(response, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
