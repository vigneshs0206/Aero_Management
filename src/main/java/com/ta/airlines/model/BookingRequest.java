package com.ta.airlines.model;

import java.sql.Timestamp;

public class BookingRequest {
		private int userId;
		private int flightId;
		private Timestamp bookingDate;

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getFlightId() {
			return flightId;
		}

		public void setFlightId(int flightId) {
			this.flightId = flightId;
		}

		public Timestamp getBookingDate() {
			return bookingDate;
		}

		public void setBookingDate(Timestamp bookingDate) {
			this.bookingDate = bookingDate;
		}
	}