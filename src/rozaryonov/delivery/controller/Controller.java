package rozaryonov.delivery.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rozaryonov.delivery.commands.ActionCommand;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ActionFactory f = new ActionFactory();
		ActionCommand cmd = f.defineCommand(request);
		String page = cmd.execute(request);
		if (page != null) {
			if (page.toLowerCase().contains("view")) {
				response.sendRedirect(page);
			} else {
				request.getRequestDispatcher(page).forward(request, response);
			}
		} else {
			request.setAttribute("errorDescription", "Target page is null after command executing.");
			request.getRequestDispatcher("view/error.jsp").forward(request, response);
		}
	}

}
