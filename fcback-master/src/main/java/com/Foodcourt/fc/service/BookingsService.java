package com.Foodcourt.fc.service;

import com.Foodcourt.fc.Entity.Bookings;
import com.Foodcourt.fc.dto.BookingsDTO;
import com.Foodcourt.fc.dto.UserBookingDTO;

import java.util.List;

public interface BookingsService {
    public String saveBooking(BookingsDTO bookingsDTO);

    public List<UserBookingDTO> getUserBooking();

    public List<Bookings> getFlightBooking(int flightId);
}
