package com.Foodcourt.fc.service;

import com.Foodcourt.fc.Entity.Flight;
import com.Foodcourt.fc.Repository.FlightRepository;
import com.Foodcourt.fc.dto.FilteringDTO;
import com.Foodcourt.fc.dto.FlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService{
    @Autowired
    FlightRepository flightRepository;

    @Override
    public void saveFlight(FlightDTO flightDTO) {
        Flight flight=new Flight();
        flight.setFlightName(flightDTO.getFlightName());
        flight.setStartingLocation(flightDTO.getStartingLocation());
        flight.setDestination(flightDTO.getDestination());
        flight.setDepartureTime(flightDTO.getDepartureTime());
        flight.setLandingTime(flightDTO.getLandingTime());
        flight.setTotalSeats(flightDTO.getTotalSeats());
        flight.setAvailableSeats(flightDTO.getAvailableSeats());
        flight.setTicketCost(flightDTO.getTicketCost());
        flightRepository.save(flight);
    }

    @Override
    public List<Flight> fetchAll()
    {
        return flightRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        flightRepository.deleteById(id);
    }

    @Override
    public List<Flight> fetchDeparture(Date departure_time) {
         return flightRepository.findAllByDepartureTime(departure_time);
    }

    @Override
    public String ticketCount(int id,int ticketCounts) {
        Flight flight=flightRepository.findById(id);
        int n=flight.getAvailableSeats()-ticketCounts;
        if(n<0)
        {
            return "seats not enough";
        }
        flight.setAvailableSeats(n);
        flightRepository.save(flight);
        return "booked";
    }

    @Override
    public List<Flight> getFlightsByDate(Date date) {
        List<Flight> res = new ArrayList<>();
        List<Flight> allFlights = flightRepository.findAll();
        for(Flight flight:allFlights){
            Date flightDate = flight.getDepartureTime();
            if(flightDate.getTime()-date.getTime()>0){
                res.add(flight);
            }
        }
        return res;
    }

    @Override
    public List<String> getNames() {
        return flightRepository.findDistinctFlightName();
    }

    @Override
    public List<Flight> filterByName(String flightName) {
        return flightRepository.findAllByFlightName(flightName);
    }

    @Override
    public List<Flight> filteringByAll(FilteringDTO filteringDTO,Date date){
        List<Flight> name=new ArrayList<>();
        List<Flight> time=new ArrayList<>();
        List<Flight> arrival=new ArrayList<>();
        List<Flight> destination=new ArrayList<>();
        if(filteringDTO.getFlightName()==null) {
            name = flightRepository.findAll();
        }
        else
        {
            name=flightRepository.findAllByFlightName(filteringDTO.getFlightName());
        }
        if(filteringDTO.getStartingLocation()==null)
        {
            arrival = flightRepository.findAll();
        }
        else
        {
            arrival=flightRepository.findAllByStartingLocation(filteringDTO.getStartingLocation());
        }
        if(filteringDTO.getDestination()==null)
        {
            destination = flightRepository.findAll();
        }
        else
        {
            destination=flightRepository.findAllByDestination(filteringDTO.getDestination());
        }
        if(filteringDTO.getDepartureTime()==null)
        {
            time = flightRepository.findAll();
        }
        else {
            List<Flight> allFlights = flightRepository.findAll();
            for(Flight flight:allFlights){
                Date flightDate = flight.getDepartureTime();
                System.out.println(flightDate.getTime()-date.getTime());
                if(flightDate.getTime()-date.getTime()>0){
                    time.add(flight);
                }
            }
        }
        Map<Flight,Integer> map=new HashMap<>();
        for(Flight flight:name)
        {
            map.put(flight,map.getOrDefault(flight,0)+1);
        }
        for(Flight flight:destination)
        {
            if(map.getOrDefault(flight,0)==1)
            {
                map.put(flight,map.getOrDefault(flight,0)+1);
            }
        }
        for(Flight flight:arrival)
        {
            if(map.getOrDefault(flight,0)==2)
            {
                map.put(flight,map.getOrDefault(flight,0)+1);
            }
        }
        List<Flight> result=new ArrayList<>();
        for(Flight flight:time)
        {
            if(map.getOrDefault(flight,0)==3)
            {
                result.add(flight);
            }
        }
        return result;
    }

    @Override
    public Flight getFlight(int id) {
        return flightRepository.findById(id);
    }
}
