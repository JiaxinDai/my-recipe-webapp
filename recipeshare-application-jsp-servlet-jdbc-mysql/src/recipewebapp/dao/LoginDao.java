package recipewebapp.dao;



import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.logging.Level;

import java.util.logging.Logger;



import com.compro.model.User;



class LoginDAO {

    

    static Connection con = null;

    

  

    

    static User login(String email, String passWord) throws SQLException

    {

        boolean flag = false;

        User user = null; 

        try

        {

            

        flag =  validateRegistrationData(email);

         if(!flag)

            return null;

         

         flag = isPasswordMatch(passWord);

         if(!flag)

            return null;

        

        

        ConnectionManager connectionMan = new ConnectionManager();

        con = ConnectionManager.dcConnect();

        

            con.setAutoCommit(false);



            Statement statement = null;

            String SQL = "Select * from user "

                    + "where email = '"+ email +"'"

                    + " And pasword = '" + passWord +"'"; 

            statement = con.createStatement();

            ResultSet rs = statement.executeQuery(SQL);

            if (rs != null) 

            {

               user = new User();

               boolean flag1 = false;

               // retrieving the data from result set

                while (rs.next())

                {

                    user.setId(rs.getInt("id"));

                    user.setName(rs.getString("fName"));

                    user.setSurname(rs.getString("lName"));

                    user.setMiddlename(rs.getString("mName"));

                    user.setEmail(rs.getString("email"));

                    user.setPassword(rs.getString("pasword"));

                    user.setRole(rs.getString("role"));

                    flag = true;



                }

                if(!flag){

                     return null;

                }

            } 

          

            con.commit();

            con.close();

        } 

        catch (Exception e) 

        {

            System.out.println(e.getMessage());

            if (con != null) 

            {

                try

                {               

                    con.rollback();

                } 

                catch (SQLException ex)

                {

                    Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);

                }

               

            }



        } 

        

        return user;

     } 

    

    

    static boolean validateRegistrationData(String email)

    {

        

        try

        {

        ConnectionManager connectionMan = new ConnectionManager();

        con = connectionMan.dcConnect();

        

            con.setAutoCommit(false);



            Statement stmt = null;

            String SQL = "Select email from user "

                    + "where email = " + "'" + email + "'";

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);

            if (rs != null) 

            {

               boolean flag =false;

               while (rs.next())

                {

                    if(rs.getString("email").equalsIgnoreCase(email));

                    flag = true;



                }

                if(!flag){

                     return false;

                }

            } 

          

            con.commit();

            con.close();

        } 

        catch (Exception e) 

        {

            System.out.println(e.getMessage());

            if (con != null) 

            {

                try

                {               

                    con.rollback();

                } 

                catch (SQLException ex)

                {

                    Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);

                }

               

            }



        } 

        

        

        return true;

    }

    

    

     static boolean isPasswordMatch(String password)

    {

        

        try

        {

        ConnectionManager connectionMan = new ConnectionManager();

        con = connectionMan.dcConnect();

        

            con.setAutoCommit(false);



            Statement stmt = null;

            String SQL = "Select pasword from user  "

                    + "where pasword = " + "'" + password + "'";

            stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(SQL);

            if (rs != null) 

            {

               boolean flag =false;

               while (rs.next())

                {

                    if(rs.getString("pasword").equalsIgnoreCase(password));

                    flag = true;



                }

                if(!flag){

                     return false;

                }

            } 

          

            con.commit();

            con.close();

        } 

        catch (Exception e) 

        {

            System.out.println(e.getMessage());

            if (con != null) 

            {

                try

                {               

                    con.rollback();

                } 

                catch (SQLException ex)

                {

                    Logger.getLogger(LoginDAO.class.getName()).log(Level.SEVERE, null, ex);

                }

               

            }



        } 

        

        

        return true;

    }

    

    

    

}
