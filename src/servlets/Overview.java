package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import services.OverviewService;
import services.UpdateService;

/**
 * Servlet implementation class Overview
 */
@WebServlet("/overview")
public class Overview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Overview() {
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
			OverviewService service = new OverviewService();
			if (service.verifyOverview(input, output)) {
				;
			} else {
				output.put("status", "User Data Not Found");		// append response status
				response.setStatus(401);
			}
			JsonHelper.writeJsonObject(response, output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
