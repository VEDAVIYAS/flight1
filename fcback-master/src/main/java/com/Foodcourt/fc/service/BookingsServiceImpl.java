package com.Foodcourt.fc.service;

import com.Foodcourt.fc.Entity.Bookings;
import com.Foodcourt.fc.Entity.Flight;
import com.Foodcourt.fc.Repository.BookingsRepository;
import com.Foodcourt.fc.Repository.FlightRepository;
import com.Foodcourt.fc.dto.BookingsDTO;
import com.Foodcourt.fc.dto.UserBookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingsServiceImpl implements BookingsService{

    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    UserService userService;

    @Autowired
    FlightService flightService;

    @Override
    public String saveBooking(BookingsDTO bookingsDTO) {
        System.out.println(bookingsDTO);
        Bookings bookings=new Bookings();
        bookings.setBookingCost(bookingsDTO.getBookingCost());
        bookings.setFlightId(bookingsDTO.getFlightId());
        bookings.setUser(userService.getCurrentUser());
        bookings.setTicketCounts(bookingsDTO.getTicketCounts());
        String s= flightService.ticketCount(bookingsDTO.getFlightId(),bookingsDTO.getTicketCounts());
        if(s.equals("booked")) {
            bookingsRepository.save(bookings);
        }
        return s;

    }

    @Override
    public List<UserBookingDTO> getUserBooking() {
        List<UserBookingDTO> userBookingDTOS=new ArrayList<>();


        List<Bookings> bookings=bookingsRepository.findAllByUser(userService.getCurrentUser());
        for(Bookings bookings1:bookings)
        {
            Flight flight11=flightService.getFlight(bookings1.getFlightId());
            UserBookingDTO userBookingDTO=new UserBookingDTO();
            userBookingDTO.setFlightId(bookings1.getFlightId());
            userBookingDTO.setBookingCost((bookings1.getBookingCost()));
            userBookingDTO.setTicketCounts(bookings1.getTicketCounts());
            userBookingDTO.setDestination(flight11.getDestination());
            userBookingDTO.setDepartureTime(flight11.getDepartureTime());
            userBookingDTO.setLandingTime(flight11.getLandingTime());
            userBookingDTO.setFlightName(flight11.getFlightName());
            userBookingDTO.setStartingLocation(flight11.getStartingLocation());
            userBookingDTOS.add(userBookingDTO);
        }
        return userBookingDTOS;
    }

    @Override
    public List<Bookings> getFlightBooking(int flightId) {
        return bookingsRepository.findAllByFlightId(flightId);
    }
}
