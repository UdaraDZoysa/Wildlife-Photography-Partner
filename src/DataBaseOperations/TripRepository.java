/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author Harsha
 */
public class TripRepository {

    public boolean planTrip(Trip trip) {
        boolean rtrn = false;//initial return states
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "INSERT INTO trip (user_id, location, start_date, end_date,other_details) VALUES (?, ?, ?, ?, ?)";

            stmt = conn.prepareCall(sql);
            stmt.setInt(1, trip.getUserID());
            stmt.setString(2, trip.getLocation());
            stmt.setDate(3, trip.getStartDate());
            stmt.setDate(4, trip.getEndDate());
            stmt.setString(5, trip.getOtherDetails());

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

    public int GetTripCount(int userID) {
        int count = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT COUNT(*) AS tripCount FROM trip WHERE user_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("tripCount");
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

    public List<Trip> DisplayPlannedTrips(int userID) {

        List<Trip> trips = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT * FROM trip WHERE user_id = ? AND completed = FALSE AND cancelled = FALSE ORDER BY start_date";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Trip trip = new Trip();
                trip.setLocation(rs.getString("location"));
                trip.setStartDate(rs.getDate("start_date"));
                trip.setEndDate(rs.getDate("end_date"));
                trip.setOtherDetails(rs.getString("other_details"));
                trip.setTripID(rs.getInt("trip_id"));
                trip.setCompleted(rs.getBoolean("completed"));
                trip.setCancelled(rs.getBoolean("cancelled"));
                trips.add(trip);
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

        return trips;
    }

    public List<Trip> DisplayCompletedTrips(int userID) {

        List<Trip> trips = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT * FROM trip WHERE user_id = ? AND completed = TRUE ORDER BY start_date";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Trip trip = new Trip();
                trip.setLocation(rs.getString("location"));
                trip.setStartDate(rs.getDate("start_date"));
                trip.setEndDate(rs.getDate("end_date"));
                trip.setOtherDetails(rs.getString("other_details"));
                trip.setTripID(rs.getInt("trip_id"));
                trip.setCompleted(rs.getBoolean("completed"));
                trip.setCancelled(rs.getBoolean("cancelled"));
                trips.add(trip);
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

        return trips;
    }

    public List<Trip> DisplayCancelledTrips(int userID) {

        List<Trip> trips = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();

            String sql = "SELECT * FROM trip WHERE user_id = ? AND cancelled = TRUE ORDER BY start_date";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userID);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Trip trip = new Trip();
                trip.setLocation(rs.getString("location"));
                trip.setStartDate(rs.getDate("start_date"));
                trip.setEndDate(rs.getDate("end_date"));
                trip.setOtherDetails(rs.getString("other_details"));
                trip.setTripID(rs.getInt("trip_id"));
                trip.setCompleted(rs.getBoolean("completed"));
                trip.setCancelled(rs.getBoolean("cancelled"));
                trips.add(trip);
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

        return trips;
    }

    public boolean SetStatusAsCompleted(int tripID) {

        boolean rtrn = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "UPDATE trip SET completed = TRUE WHERE trip_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, tripID);

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

    public boolean SetStatusAsCancelled(int tripID) {

        boolean rtrn = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "UPDATE trip SET cancelled = TRUE WHERE trip_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, tripID);

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

    public boolean SetStatusAsNotCancelled(String otherDetails, String Location, Date startDate, Date endDate, int tripID) {

        boolean rtrn = false;//initial return state
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "UPDATE trip SET other_details = ?, Location = ?, start_date = ?, end_date = ?,cancelled = FALSE  WHERE trip_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, otherDetails);
            stmt.setString(2, Location);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setInt(5, tripID);

            int effectedRowCount = stmt.executeUpdate();//get number of effected rows in DB

            if (effectedRowCount > 0) {
                conn.commit();
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

    public boolean RemoveTrip(int tripID) {

        boolean rtrn = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "DELETE FROM trip WHERE trip_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, tripID);

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

    public boolean UpdateTripDetails(String otherDetails, String Location, Date startDate, Date endDate, int tripID) {

        boolean rtrn = false;//initial return state
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = CreateConnection.getConnection();
            conn.setAutoCommit(false);//auto commit off

            String sql = "UPDATE trip SET other_details = ?, Location = ?, start_date = ?, end_date = ?  WHERE trip_id = ?";

            stmt = conn.prepareStatement(sql);

            stmt.setString(1, otherDetails);
            stmt.setString(2, Location);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            stmt.setInt(5, tripID);

            int effectedRowCount = stmt.executeUpdate();//get number of effected rows in DB

            if (effectedRowCount > 0) {
                conn.commit();
                rtrn = true;
            } else {
                conn.rollback();
            }
        }catch (ClassNotFoundException | SQLException e) {
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

}
