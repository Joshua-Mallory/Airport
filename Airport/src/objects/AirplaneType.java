package objects;

public class AirplaneType {
	private Integer id;
	private Integer econ_capacity;
	private Integer bus_capacity;
	private Integer first_capacity;

	public AirplaneType() {

	}

	public AirplaneType(Integer id, Integer econ_capacity, Integer bus_capacity, Integer first_capacity) {
		this.id = id;
		this.econ_capacity = econ_capacity;
		this.bus_capacity = bus_capacity;
		this.first_capacity = first_capacity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEcon_capacity() {
		return econ_capacity;
	}

	public void setEcon_capacity(Integer econ_capacity) {
		this.econ_capacity = econ_capacity;
	}

	public Integer getBus_capacity() {
		return bus_capacity;
	}

	public void setBus_capacity(Integer bus_capacity) {
		this.bus_capacity = bus_capacity;
	}

	public Integer getFirst_capacity() {
		return first_capacity;
	}

	public void setFirst_capacity(Integer first_capacity) {
		this.first_capacity = first_capacity;
	}

}
