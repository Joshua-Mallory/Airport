package objects;

public class Booking_User {

	private Integer user_id;
	private Integer booking_id;

	public Booking_User() {
	}

	public Booking_User(Integer booking_id, Integer user_id) {
		this.user_id = user_id;
		this.booking_id = booking_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(Integer booking_id) {
		this.booking_id = booking_id;
	}

}
