package recipewebapp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
   @SuppressWarnings("unchecked")
public List<User> getAllUsers(){
      List<User> userList = null;
      User user = new User(1, "Harini", "HMovieLabs");
      userList = new ArrayList<User>();
      userList.add(user);
      saveUserList(userList);		
      return userList;
   }

   private void saveUserList(List<User> userList){
      try {
         File file = new File("Users.dat");
         FileOutputStream fos;
         fos = new FileOutputStream(file);

         ObjectOutputStream oos = new ObjectOutputStream(fos);
         oos.writeObject(userList);
         oos.close();
      } catch (FileNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }   
}