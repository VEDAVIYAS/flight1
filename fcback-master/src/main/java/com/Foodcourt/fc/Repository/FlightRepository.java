package com.Foodcourt.fc.Repository;

import com.Foodcourt.fc.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {


    public List<Flight> findAllByDepartureTime(Date departure_time);

    public List<Flight> findAll();

    public Flight findById(int id);

    @Query("SELECT DISTINCT a.flightName FROM Flight a")
    public List<String> findDistinctFlightName();

    public List<Flight> findAllByFlightName(String flightName);

    public List<Flight> findAllByStartingLocation(String startingLocation);

    public List<Flight> findAllByDestination(String destination);
}
