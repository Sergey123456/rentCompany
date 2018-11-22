package telran.cars.dto;

import java.time.LocalDate;

public class DatesForReturnByDates {
	LocalDate from;
	LocalDate to;
	
	
	public DatesForReturnByDates(LocalDate from, LocalDate to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public DatesForReturnByDates() {

	}


	public LocalDate getFrom() {
		return from;
	}
	
	public LocalDate getTo() {
		return to;
	}
	
	
	

}
