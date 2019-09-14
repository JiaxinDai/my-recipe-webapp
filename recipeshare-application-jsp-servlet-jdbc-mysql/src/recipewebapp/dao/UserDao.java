package recipewebapp.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.bind.DatatypeConverter;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import model.Show;
import model.User;

public class UserDAO {
	
	private static UserDAO instance;
	private static final HashMap<String, User> allUsers = new HashMap<>(); //username  - > user
	
	private UserDAO() {
	}
	
	public static synchronized UserDAO getInstance(){
		if(instance == null){
			instance = new UserDAO();
		}
		return instance;
	}
	
	public synchronized void addUser(User u) throws SQLException {
		String sql = "INSERT INTO users	(user_name, age, country, email, password) VALUES(?, ?, ?, ?, md5(?))";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		st.setString(1, u.getUserName());
		st.setInt(2, u.getAge());
		st.setString(3, u.getCountry());
		st.setString(4, u.getEmail());
		st.setString(5, u.getPassword());
		st.execute();
		ResultSet rs = st.getGeneratedKeys();
		rs.next();
		u.setUserId(rs.getLong(1));
		getAllUsers().put(u.getUserName(), u);
		
	}
	
	public HashMap<String, User> getAllUsers() throws SQLException{
		if(allUsers.isEmpty()){
			String sql = "SELECT user_id, user_name, age, country, email, password FROM users";
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				User u = new User(rs.getString("user_name"), rs.getInt("age"),
						rs.getString("country"),rs.getString("email"), rs.getString("password"));
				u.setUserId(rs.getLong("user_id"));
				allUsers.put(u.getUserName(), u);
			}
		}
		return allUsers;
	}
	
	public boolean isEmailFree(String email) throws SQLException{
		String sql = "SELECT email FROM users WHERE email='"+email+"'";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		if (!rs.next()) {    
		    return true; 
		} 
		return false;
	}
	
	public void sendFriendRequest(User sender, User reciever) throws SQLException{
		String sql1 = "SELECT user_one_id, user_two_id, status, action_user_id, action_user_id FROM relationship "
					+ "WHERE (user_one_id = "+sender.getUserId()+" AND user_two_id = " + reciever.getUserId() + ") "
					+ "OR (user_one_id = "+ reciever.getUserId() +" AND user_two_id = "+ sender.getUserId() + ")";
		PreparedStatement st1 = DBManager.getInstance().getConnection().prepareStatement(sql1);
		if(!st1.execute()){			
			String sql = "INSERT INTO `relationship` (`user_one_id`, `user_two_id`, `status`, `action_user_id`) VALUES(?, ?, ?, ?)";
			PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
			st.setLong(1, sender.getUserId());
			st.setLong(2, reciever.getUserId());
			st.setInt(3, 0);
			st.setLong(4, sender.getUserId());
			st.executeUpdate();
		}else{
			System.out.println(sender  + " has already a relationship with " + reciever);
		}
	}
	
	public void acceptFriendRequest(User sender, User reciever) throws SQLException{
		String sql = "UPDATE relationship SET status = 1, action_user_id = " + reciever.getUserId() +  " "
					+ "WHERE (user_one_id = "+sender.getUserId()+" AND user_two_id = " + reciever.getUserId() + ") "
					+ "OR (user_one_id = "+ reciever.getUserId() +" AND user_two_id = "+ sender.getUserId() + ")";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.executeUpdate();
	}
	
	public HashMap<String, User> getAllFriends(User u) throws SQLException{
		String sql = "SELECT user_two_id FROM relationship WHERE user_one_id = "+u.getUserId()+" AND status = 1";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		ResultSet set = null;
		String sql1 = "SELECT user_one_id FROM relationship WHERE user_two_id = "+u.getUserId()+" AND status = 1";
		PreparedStatement st1 = DBManager.getInstance().getConnection().prepareStatement(sql1);
		set = st.executeQuery();
		while(set.next()){
			String sql2 = "SELECT user_name FROM users WHERE user_id = "+set.getLong(1);
			PreparedStatement st2 = DBManager.getInstance().getConnection().prepareStatement(sql2);
			ResultSet rs  = st2.executeQuery();
			while(rs.next()){
				u.getFriends().put(rs.getString(1), getAllUsers().get(rs.getString(1)));
			}
		}
		set = st1.executeQuery();
		while(set.next()){
			String sql2 = "SELECT user_name FROM users WHERE user_id = "+set.getLong(1);
			PreparedStatement st2 = DBManager.getInstance().getConnection().prepareStatement(sql2);
			ResultSet rs  = st2.executeQuery();
			while(rs.next()){
				u.getFriends().put(rs.getString(1), getAllUsers().get(rs.getString(1)));
			}
		}
		return u.getFriends();
	}
	
	public void removeFriends(User s, User r) throws SQLException{
		System.out.println(s.getUserName());
		String sql = "DELETE FROM relationship WHERE  (user_one_id = "+s.getUserId()+" AND user_two_id = " + r.getUserId() + ") "
					+ "OR (user_one_id = "+ r.getUserId() +" AND user_two_id = "+ s.getUserId() + ")";
		PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(sql);
		statement.executeUpdate();
		System.out.println(r.getUserName());
	    UserDAO.getInstance().getAllFriends(s).remove(r.getUserName());
	    UserDAO.getInstance().getAllFriends(r).remove(s.getUserName());
	}
	
	public TreeMap<String, Show> getFollowingShows(User u) throws SQLException{
		if(u.getMyFollowing().isEmpty()){
			String sql = "SELECT shows_show_id FROM users_has_shows WHERE users_user_id = "+ u.getUserId();
			PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				String sql1 = "SELECT name FROM shows WHERE show_id = "+ rs.getLong(1);
				PreparedStatement st1= DBManager.getInstance().getConnection().prepareStatement(sql1);
				ResultSet rs1 = st1.executeQuery();
				while(rs1.next()){
					String name = rs1.getString(1);
					u.getMyFollowing().put(name, ShowDAO.getInstance().getAllShows().get(name));
				}
			}
		}
		return u.getMyFollowing();
	}
	
	public synchronized void addToMyFollowing(User u, Show s) throws SQLException{
		String sql = "INSERT INTO users_has_shows (users_user_id, shows_show_id) VALUES(?, ?)";
		PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(sql);
		st.setLong(1, u.getUserId());
		st.setLong(2, s.getShowId());
		st.executeUpdate();
		getFollowingShows(u).put(s.getShowName(), s);
	}
	
	public synchronized boolean validLogin(String username, String password) throws SQLException{
		if(getAllUsers().containsKey(username)){
			MessageDigest m;
			try {
				m = MessageDigest.getInstance("MD5");
				m.update(password.getBytes());
				byte[] digest = m.digest();
				String hashtext = DatatypeConverter.printHexBinary(digest).toLowerCase();
				return allUsers.get(username).getPassword().equals(hashtext);
				
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Problem with hashing the password.");
			}
		}
		return false;
	}
}
