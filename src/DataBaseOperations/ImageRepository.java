/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Date;

/**
 *
 * @author Harsha
 */
public class ImageRepository {

    public boolean AddImage(Images img) {
        boolean rtrn = false;//initial return state
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "INSERT INTO photos(user_id, photo_url, OtherDetails, TechDetails, categories, Location,Public) VALUES  (?, ?, ?, ?,?,?,?)";

            stmt = conn.prepareCall(sql);
            stmt.setInt(1, img.getUserID());
            stmt.setString(2, img.getImgPath());
            stmt.setString(3, img.getOtherDetails());
            stmt.setString(4, img.getTechDetails());
            stmt.setString(5, img.getCategory());
            stmt.setString(6, img.getLocation());
            stmt.setBoolean(7, img.isVilibleAll());

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

    public Images UpdateImage(String location, String category, String otherDetails, String techDetails, boolean visibleAll, int imgID) {

        Images rtrn = null;//initial return state
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "UPDATE photos SET OtherDetails = ?, TechDetails = ?, categories = ?, Location = ?,Public = ? WHERE photo_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, otherDetails);
            stmt.setString(2, techDetails);
            stmt.setString(3, category);
            stmt.setString(4, location);
            stmt.setBoolean(5, visibleAll);
            stmt.setInt(6, imgID);

            int effectedRowCount = stmt.executeUpdate();//get number of effected rows in DB

            if (effectedRowCount > 0) {
                //if opreation done correctly return set to true
                conn.commit();//commit result

                String getSql = "SELECT * FROM photos WHERE photo_id = ? ";

                PreparedStatement statement = conn.prepareStatement(getSql);
                statement.setInt(1, imgID);

                rs = statement.executeQuery();

                Images image = new Images();
                while (rs.next()) {

                    image.setImgID(rs.getInt("photo_id"));
                    image.setUserID(rs.getInt("user_id"));
                    image.setImgPath(rs.getString("photo_url"));
                    image.setLocation(rs.getString("Location"));
                    image.setCategory(rs.getString("Categories"));
                    image.setOtherDetails(rs.getString("OtherDetails"));
                    image.setTechDetails(rs.getString("TechDetails"));
                    image.setDate(rs.getDate("timestamp"));
                    image.setVilibleAll(rs.getBoolean("Public"));
                    image.setFavourite(rs.getBoolean("Favourite"));
                }

                rtrn = image;
            } else {
                conn.rollback();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Rollback failed: " + ex.getMessage());
                }
            }
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

        return rtrn;
    }

    public boolean SetAddFavouriteStatus(int imgID, boolean favourite) {

        boolean rtrn = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "UPDATE photos SET Favourite = ? WHERE photo_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setBoolean(1, favourite);
            stmt.setInt(2, imgID);

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
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Rollback failed: " + ex.getMessage());
                }
            }
        } finally {
            // Properly close all resources
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

    public boolean DeleteImage(int imgID) {

        boolean rtrn = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "DELETE FROM photos WHERE photo_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, imgID);

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
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Rollback failed: " + ex.getMessage());
                }
            }
        } finally {
            // Properly close all resources
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

    public List<Images> SearchImagesUsingExactDate(int userID, Date date, String location, String category) {

        List<Images> images = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String[] locations = location.split(", ");
        String inClause = String.join(",", java.util.Collections.nCopies(locations.length, "?"));

        String[] categories = category.split(", ");
        String inClauseCat = String.join(",", java.util.Collections.nCopies(categories.length, "?"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT * FROM photos WHERE user_id = ? AND timestamp = ? AND Categories IN (" + inClauseCat + ") AND Location IN (" + inClause + ")";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);
            stmt.setDate(2, date);

            int index = 3;

            for (String cat : categories) {
                stmt.setString(index++, cat.trim());
            }

            for (String loc : locations) {
                stmt.setString(index++, loc.trim());
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Images image = new Images();

                image.setImgID(rs.getInt("photo_id"));
                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setLocation(rs.getString("Location"));
                image.setCategory(rs.getString("Categories"));
                image.setOtherDetails(rs.getString("OtherDetails"));
                image.setTechDetails(rs.getString("TechDetails"));
                image.setDate(rs.getDate("timestamp"));
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
                images.add(image);
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

        return images;
    }

    public List<Images> SearchImagesUsingDateRange(int userID, Date startDate, Date endDate, String location, String category) {

        List<Images> images = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String[] locations = location.split(", ");
        String inClause = String.join(",", java.util.Collections.nCopies(locations.length, "?"));

        String[] categories = category.split(", ");
        String inClauseCat = String.join(",", java.util.Collections.nCopies(categories.length, "?"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT * FROM photos WHERE user_id = ? AND timestamp BETWEEN ? AND ? AND Categories IN (" + inClauseCat + ") AND Location IN (" + inClause + ")";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);
            stmt.setDate(2, startDate);
            stmt.setDate(3, endDate);

            int index = 4;

            for (String cat : categories) {
                stmt.setString(index++, cat.trim());
            }

            for (String loc : locations) {
                stmt.setString(index++, loc.trim());
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Images image = new Images();

                image.setImgID(rs.getInt("photo_id"));
                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setLocation(rs.getString("Location"));
                image.setCategory(rs.getString("Categories"));
                image.setOtherDetails(rs.getString("OtherDetails"));
                image.setTechDetails(rs.getString("TechDetails"));
                image.setDate(rs.getDate("timestamp"));
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
                images.add(image);
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

        return images;
    }

    public List<Images> SearchImagesWithoutDate(int userID, String location, String category) {

        List<Images> images = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String[] locations = location.split(", ");
        String inClause = String.join(",", java.util.Collections.nCopies(locations.length, "?"));

        String[] categories = category.split(", ");
        String inClauseCat = String.join(",", java.util.Collections.nCopies(categories.length, "?"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT * FROM photos WHERE user_id = ? AND Categories IN (" + inClauseCat + ") AND Location IN (" + inClause + ")";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);

            int index = 2;

            for (String cat : categories) {
                stmt.setString(index++, cat.trim());
            }

            for (String loc : locations) {
                stmt.setString(index++, loc.trim());
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Images image = new Images();

                image.setImgID(rs.getInt("photo_id"));
                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setLocation(rs.getString("Location"));
                image.setCategory(rs.getString("Categories"));
                image.setOtherDetails(rs.getString("OtherDetails"));
                image.setTechDetails(rs.getString("TechDetails"));
                image.setDate(rs.getDate("timestamp"));
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
                images.add(image);
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

        return images;
    }

    public List<Images> DisplayFavouriteImages(int userID) {

        List<Images> images = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT * FROM photos WHERE user_id = ? AND Favourite = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);
            stmt.setBoolean(2, true);//Only if image favourite is true

            rs = stmt.executeQuery();

            while (rs.next()) {
                Images image = new Images();

                image.setImgID(rs.getInt("photo_id"));
                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setLocation(rs.getString("Location"));
                image.setCategory(rs.getString("Categories"));
                image.setOtherDetails(rs.getString("OtherDetails"));
                image.setTechDetails(rs.getString("TechDetails"));
                image.setDate(rs.getDate("timestamp"));
                image.setVilibleAll(rs.getBoolean("Public"));
                image.setFavourite(rs.getBoolean("Favourite"));
                images.add(image);
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
        return images;
    }

    //get total image count
    public int TotalImageCount(int userID) {
        int count = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT photo_url FROM photos WHERE user_id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            //increase the count only if image exist relevant location
            while (rs.next()) {
                String imgPath = rs.getString("photo_url");
                File file = new File(imgPath);
                if (file.exists()) {
                    count++;
                }
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

        return count;
    }

    public int FavouriteImageCount(int userID) {
        int count = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT photo_url FROM photos WHERE user_id = ? AND Favourite = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);
            stmt.setBoolean(2, true);

            rs = stmt.executeQuery();

            //increase the count only if image exist relevant location
            while (rs.next()) {
                String imgPath = rs.getString("photo_url");
                File file = new File(imgPath);
                if (file.exists()) {
                    count++;
                }
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

        return count;
    }

    public int PublicImageCount(int userID) {
        int count = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT photo_url FROM photos WHERE user_id = ? AND Public = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);
            stmt.setBoolean(2, true);

            rs = stmt.executeQuery();

            //increase the count only if image exist relevant location
            while (rs.next()) {
                String imgPath = rs.getString("photo_url");
                File file = new File(imgPath);
                if (file.exists()) {
                    count++;
                }
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
        return count;
    }

    public List<Images> DisplayRecentImages(int userID) {

        List<Images> images = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            //String sql = "SELECT * FROM photos WHERE user_id = ? ORDER BY timestamp DESC Limit 6";
            String sql2 = "SELECT * FROM photos WHERE user_id = ? ORDER BY timestamp DESC";

            stmt = conn.prepareStatement(sql2);

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Images image = new Images();
                image.setImgPath(rs.getString("photo_url"));
                image.setDate(rs.getDate("timestamp"));
                images.add(image);
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

        return images;
    }

    public List<Images> DisplayPublicImages() {

        List<Images> images = new ArrayList<>();
        String sql = "SELECT * FROM photos WHERE Public = TRUE";

        try (Connection conn = CreateConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            while (rs.next()) {
                Images image = new Images();

                image.setUserID(rs.getInt("user_id"));
                image.setImgPath(rs.getString("photo_url"));
                image.setDate(rs.getDate("timestamp"));
                images.add(image);
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return images;
    }
}
