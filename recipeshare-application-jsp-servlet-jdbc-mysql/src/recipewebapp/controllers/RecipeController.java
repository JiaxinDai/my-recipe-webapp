package recipewebapp.controllers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import recipewebapp.dao.RecipeDao;
import recipewebapp.dao.RecipeDaoImpl;
import recipewebapp.model.Recipe;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the recipe.
 * 
 */

@WebServlet("/")
public class RecipeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RecipeDao recipeDAO;
	private String imageFolder;

	public void init() {
		recipeDAO = new RecipeDaoImpl();
	    imageFolder = System.getProperty("user.home") + "/Desktop/uploaded_files/";
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		
		request.setAttribute("currentUser", request.getParameter("currentUser"));

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertRecipe(request, response);
				break;
			case "/delete":
				deleteRecipe(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateRecipe(request, response);
				break;
			case "/list":
				listRecipe(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listRecipe(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Recipe> listRecipe = recipeDAO.getAllRecipes();
		request.setAttribute("listRecipe", listRecipe);
		RequestDispatcher dispatcher = request.getRequestDispatcher("recipe/recipe-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("recipe/recipe-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Recipe existingRecipe = recipeDAO.getRecipeById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("recipe/recipe-form.jsp");
		request.setAttribute("recipe", existingRecipe);
		dispatcher.forward(request, response);

	}
	
	private void insertRecipe(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String title = null;
		String owner = request.getParameter("currentUser");
		String description = null;
		String filename = null;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		
		if (!isMultipartContent) {
			return;
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List < FileItem > fields = upload.parseRequest(request);
			Iterator < FileItem > it = fields.iterator();
			if (!it.hasNext()) {
				return;
			}
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					if (title == null) {
						title = fileItem.getString();
					} else if (description == null) {
						description = fileItem.getString();
					}
				} else {
					if (fileItem.getSize() > 0) {
						filename = fileItem.getName();
						File outputFile = new File(filename);
						fileItem.write(new File(imageFolder, outputFile.getName()));
					}
					
					Recipe newRecipe = new Recipe(title, owner, filename, description, LocalDate.now());
					recipeDAO.insertRecipe(newRecipe);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			List<Recipe> listRecipe = recipeDAO.getAllRecipes();
			request.setAttribute("listRecipe", listRecipe);
			RequestDispatcher dispatcher = request.getRequestDispatcher("recipe/recipe-list.jsp");
			dispatcher.forward(request, response);
			out.close();
		}
	}
	
	private void updateRecipe(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String owner = request.getParameter("currentUser");
		int id = -1;
		String filename = null;
		String description = null;
		String title = null;
		// String filename = null;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		
		if (!isMultipartContent) {
			return;
		}
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List < FileItem > fields = upload.parseRequest(request);
			Iterator < FileItem > it = fields.iterator();
			if (!it.hasNext()) {
				return;
			}
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				boolean isFormField = fileItem.isFormField();
				if (isFormField) {
					if (id == -1) {
						id = Integer.parseInt(fileItem.getString());
					} else if (filename == null) {
						filename = fileItem.getString();
					} else if (title == null) {
						title = fileItem.getString();
					} else if (description == null) {
						description = fileItem.getString();
					}
				}
			}
			
			Recipe updateRecipe = new Recipe(id, title, owner, filename, description, LocalDate.now());
			recipeDAO.updateRecipe(updateRecipe);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			List<Recipe> listRecipe = recipeDAO.getAllRecipes();
			request.setAttribute("listRecipe", listRecipe);
			RequestDispatcher dispatcher = request.getRequestDispatcher("recipe/recipe-list.jsp");
			dispatcher.forward(request, response);
			out.close();
		}
	}

	private void deleteRecipe(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		// delete the food image from the local file system
		Files.deleteIfExists(Paths.get(imageFolder + recipeDAO.getRecipeById(id).getFilename()));
				
		recipeDAO.deleteRecipe(id);
		
		List<Recipe> listRecipe = recipeDAO.getAllRecipes();
		request.setAttribute("listRecipe", listRecipe);
		RequestDispatcher dispatcher = request.getRequestDispatcher("recipe/recipe-list.jsp");
		dispatcher.forward(request, response);
	}
}