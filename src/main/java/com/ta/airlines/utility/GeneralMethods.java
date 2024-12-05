package com.ta.airlines.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Component
public class GeneralMethods {
	   public byte[] generateTicketPdf(Map<String, Object> bookingDetails, Integer userId) {
	        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	            PdfWriter writer = new PdfWriter(outputStream);
	            PdfDocument pdfDocument = new PdfDocument(writer);
	            Document document = new Document(pdfDocument);

	            document.add(new Paragraph("Aeroplane Ticket Booking System").setBold());
	            document.add(new Paragraph("Ticket Number: " + bookingDetails.get("ticket_number")));
	            document.add(new Paragraph("User ID: " + userId));
	            document.add(new Paragraph("Booking Date: " + bookingDetails.get("booking_date")));
	            document.add(new Paragraph("Status: " + bookingDetails.get("status")));
	            document.add(new Paragraph("Flight Details:"));
	            document.add(new Paragraph("Flight Number: " + bookingDetails.get("flight_number")));
	            document.add(new Paragraph("From: " + bookingDetails.get("origin")));
	            document.add(new Paragraph("To: " + bookingDetails.get("destination")));
	            document.add(new Paragraph("Departure Time: " + bookingDetails.get("departure_time")));
	            document.add(new Paragraph("Arrival Time: " + bookingDetails.get("arrival_time")));
	            document.add(new Paragraph("Generated At: " + new Timestamp(System.currentTimeMillis())));

	            document.close();

	            return outputStream.toByteArray();
	        } catch (IOException e) {
	            throw new RuntimeException("Error generating PDF", e);
	        }
	    }
}
