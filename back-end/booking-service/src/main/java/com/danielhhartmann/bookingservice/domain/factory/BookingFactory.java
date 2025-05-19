package com.danielhhartmann.bookingservice.domain.factory;

import com.danielhhartmann.bookingservice.domain.entity.Booking;
import com.danielhhartmann.bookingservice.domain.enums.BookingStatus;
import com.danielhhartmann.bookingservice.domain.vo.Period;

import java.time.LocalDateTime;

public class BookingFactory {

    public static Booking createBooking(Long userId, Long roomId, LocalDateTime start, LocalDateTime end) {
        Booking booking = new Booking();

        booking.setUserId(userId);
        booking.setRoomId(roomId);
        booking.setPeriod(new Period(start, end));
        booking.setStatus(BookingStatus.PENDING);

        return booking;
    }
}
