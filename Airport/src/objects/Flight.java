package objects;

import java.sql.Time;
import java.util.Date;

public class Flight {
	private Integer id;
	private Integer route_id;
	private Integer airplane_id;
	private Time departure_time_time;
	private Date departure_time_date;
	private Time arrival_time_time;
	private Date arrival_time_date;
	private Integer reserved_econ;
	private Integer reserved_bus;
	private Integer reserved_first;
	private float econ_price;
	private float bus_price;
	private float first_price;

	public Flight() {
	}

	public Flight(Integer id, Integer route_id, Integer airplane_id, Date departure_time_date, Time departure_time_time,
			Date arrival_time_date, Time arrival_time_time, Integer reserved_econ, Integer reserved_bus,
			Integer reserved_first, float econ_price, float bus_price, float first_price) {
		this.id = id;
		this.route_id = route_id;
		this.airplane_id = airplane_id;
		this.departure_time_time = departure_time_time;
		this.departure_time_date = departure_time_date;
		this.arrival_time_time = arrival_time_time;
		this.arrival_time_date = arrival_time_date;
		this.reserved_econ = reserved_econ;
		this.reserved_bus = reserved_bus;
		this.reserved_first = reserved_first;
		this.econ_price = econ_price;
		this.bus_price = bus_price;
		this.first_price = first_price;

	}

	public Time getArrival_time_time() {
		return arrival_time_time;
	}

	public void setArrival_time_time(Time arrival_time_time) {
		this.arrival_time_time = arrival_time_time;
	}

	public Date getArrival_time_date() {
		return arrival_time_date;
	}

	public void setArrival_time_date(Date arrival_time_date) {
		this.arrival_time_date = arrival_time_date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoute_id() {
		return route_id;
	}

	public void setRoute_id(Integer route_id) {
		this.route_id = route_id;
	}

	public Integer getAirplane_id() {
		return airplane_id;
	}

	public void setAirplane_id(Integer airplane_id) {
		this.airplane_id = airplane_id;
	}

	public Time getDeparture_time_time() {
		return departure_time_time;
	}

	public void setDeparture_time_time(Time departure_time_time) {
		this.departure_time_time = departure_time_time;
	}

	public Date getDeparture_time_date() {
		return departure_time_date;
	}

	public void setDeparture_time_date(Date departure_time_date) {
		this.departure_time_date = departure_time_date;
	}

	public Integer getReserved_econ() {
		return reserved_econ;
	}

	public void setReserved_econ(Integer reserved_econ) {
		this.reserved_econ = reserved_econ;
	}

	public Integer getReserved_bus() {
		return reserved_bus;
	}

	public void setReserved_bus(Integer reserved_bus) {
		this.reserved_bus = reserved_bus;
	}

	public Integer getReserved_first() {
		return reserved_first;
	}

	public void setReserved_first(Integer reserved_first) {
		this.reserved_first = reserved_first;
	}

	public float getEcon_price() {
		return econ_price;
	}

	public void setEcon_price(float econ_price) {
		this.econ_price = econ_price;
	}

	public float getBus_price() {
		return bus_price;
	}

	public void setBus_price(float bus_price) {
		this.bus_price = bus_price;
	}

	public float getFirst_price() {
		return first_price;
	}

	public void setFirst_price(float first_price) {
		this.first_price = first_price;
	}

}
