/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseOperations;

import java.sql.Date;
import java.util.List;

public class TripService {
    
    private static TripRepository tripRepository = new TripRepository();
    public static List<Trip> trips; 
    
    public static boolean PlanTrip(int userID,String location,Date startDate,Date endDate,String otherDetails){
        
        Trip trip = new Trip(userID, location, startDate, endDate, otherDetails);
        
        //call planTrip method using tripRepository object and return boolean value
        return tripRepository.planTrip(trip);  
    }
    
    public static int getTripCount(int userID){
        return tripRepository.GetTripCount(userID);
    }
    
    public static void getPlannedTrips(int userID){    
        trips = tripRepository.DisplayPlannedTrips(userID);
    }
    
    public static boolean setAsCompleted(int tripID){
        return tripRepository.SetStatusAsCompleted(tripID);
    }
    
    public static boolean setAsCancelled(int tripID){
        return tripRepository.SetStatusAsCancelled(tripID);
    }
    
    public static boolean setAsNotCancelled(String otherDetails, String Location, Date startDate, Date endDate,int tripID){ 
        return tripRepository.SetStatusAsNotCancelled(otherDetails, Location, startDate, endDate, tripID);
    }
    
    public static boolean RemoveTrip(int tripID){
        return tripRepository.RemoveTrip(tripID);
    }
    
    public static boolean UpdateTripDetails(String otherDetails, String Location, Date startDate, Date endDate,int tripID){ 
        return tripRepository.UpdateTripDetails(otherDetails, Location, startDate, endDate, tripID);
    }
    
    public static void getCompletedTrips(int userID){
        trips = tripRepository.DisplayCompletedTrips(userID);
    }
    
    public static void getCancelledTrips(int userID){
        trips = tripRepository.DisplayCancelledTrips(userID);
    }
}
