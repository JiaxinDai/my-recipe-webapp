package recipewebapp.dao;

public class CommentDAO {
	
	private static CommentDAO instance;
	
	private CommentDAO() {
	}
	
	public static synchronized CommentDAO getInstance(){
		if(instance == null){
			instance = new CommentDAO();
		}
		return instance;
	}
	
	
}