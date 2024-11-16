/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.*;

public class UserRepository {

    //this method use to handle login option
    //this method return User type result
    public User login(String userName, String password) {

        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "select * from users where username = ? and password = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            if (rs.next()) {
                //get user details to newly created user object
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("profile_pic"));
                user.setUserID(rs.getInt("user_id"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            // Properly close all resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("ResultSet close failed: " + e.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Statement close failed: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Connection close failed: " + e.getMessage());
                }
            }
        }
        return user;
    }

    //this method use to handle create account option
    public boolean createAccount(User user) {
        boolean rtrn = false;//initial return states
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "INSERT INTO users (username, password, email, profile_pic) VALUES (?, ?, ?, ?)";

            stmt = conn.prepareCall(sql);
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            if (user.getProfilePic() == null || user.getProfilePic().trim().isEmpty()) {
                //if user didn't select profile picture set this default image
                stmt.setString(4, "D:\\Sem 1\\OOP\\Assignment_Codes\\NetBeanPractical\\Photography partner\\src\\image\\user_2.png");
            } else {
                //if user select image use it
                stmt.setString(4, user.getProfilePic());
            }

            int effectedRowCount = stmt.executeUpdate();//get number of effected rows in DB

            if (effectedRowCount > 0) {
                //if opreation done correctly return set to true
                conn.commit();//commit result
                rtrn = true;
            } else {
                conn.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback on exceptions
                } catch (SQLException ex) {
                    System.out.println("Rollback failed: " + ex.getMessage());
                }
            }
        } finally {
            // Close all resources properly
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Statement close failed: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Connection close failed: " + e.getMessage());
                }
            }
        }

        return rtrn;
    }

    public User getUserName(int userID) {

        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "select username from users where user_id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                user = new User();  // Assuming you have a default constructor
                user.setUserName(username);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            // Properly close all resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("ResultSet close failed: " + e.getMessage());
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Statement close failed: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Connection close failed: " + e.getMessage());
                }
            }
        }

        return user;
    }

}
