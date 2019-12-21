package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import services.LoginService;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		//doGet(request, response);
		try {
			JSONObject input = JsonHelper.readJSONObject(request);
			JSONObject output = new JSONObject();
			LoginService  service = new LoginService();
			if (service.verifyLogin(input, output))	{
				HttpSession session = request.getSession();
				session.setAttribute("user_id", output.getString("user_id"));
				session.setMaxInactiveInterval(600);
				output.put("status", "OK");			// append response status
			} else {
				output.put("status", "User Doesn't Exist");		// append response status
				response.setStatus(401);
			}
			JsonHelper.writeJsonObject(response, output);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
