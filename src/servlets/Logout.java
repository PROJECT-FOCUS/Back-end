package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import services.LogoutService;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
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
			// get session from request
			HttpSession session = request.getSession(false);
			// create JSONObject "output" to show if the logout is successful or not
			JSONObject output = new JSONObject();
			LogoutService service = new LogoutService();
			if (service.verifyLogout(session, output))	{
				session.invalidate();
				output.put("status", "OK");			// append response status
			} else {
				output.put("status", "User Already Logged Out");		// append response status
				response.setStatus(401);
			}
			JsonHelper.writeJsonObject(response, output);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
