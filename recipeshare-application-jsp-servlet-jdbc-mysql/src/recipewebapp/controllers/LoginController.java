package recipewebapp.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import recipewebapp.dao.LoginDao;
import recipewebapp.dao.RecipeDao;
import recipewebapp.dao.RecipeDaoImpl;
import recipewebapp.model.LoginBean;

/**
 * @email Ramesh Fadatare
 */

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;
	private RecipeDao recipeDAO;

	public void init() {
		loginDao = new LoginDao();
		recipeDAO = new RecipeDaoImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login/login.jsp");
	}
	
	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		LoginBean loginBean = new LoginBean();
		loginBean.setUsername(username);
		loginBean.setPassword(password);
		
		try {
			if (loginDao.validate(loginBean)) {
				request.setAttribute("listRecipe", recipeDAO.getAllRecipes());
				RequestDispatcher dispatcher = request.getRequestDispatcher("recipe/recipe-list.jsp");
			} else {
				HttpSession session = request.getSession();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}