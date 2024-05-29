/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.Date;

public class TripService {
    
    private static TripRepository tripRepository = new TripRepository();
    
    public static boolean PlanTrip(int userID,String location,Date startDate,Date endDate,String otherDetails){
        
        Trip trip = new Trip(userID, location, startDate, endDate, otherDetails);
        
        //call planTrip method using tripRepository object and return boolean value
        return tripRepository.planTrip(trip);  
    }
    
    public static int getTripCount(int userID){
    
        return tripRepository.GetTripCount(userID);
    }
}
