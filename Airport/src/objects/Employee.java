package objects;

public class Employee {
	private String originID;
	private String originName;
	private String destID;
	private String destName;
	private Integer routeID;

	public Employee() {
	}

	public Employee(Integer routeID, String originID, String originName, String destID, String destName) {
		this.routeID = routeID;
		this.originID = originID;
		this.originName = originName;
		this.destID = destID;
		this.destName = destName;
	}

	public String empRoutePrint() {

		return getOriginID() + ", " + getOriginName() + " -> " + getDestID() + ", " + getDestName();
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public String getOriginID() {
		return originID;
	}

	public void setOriginID(String originID) {
		this.originID = originID;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getDestID() {
		return destID;
	}

	public void setDestID(String destID) {
		this.destID = destID;
	}

	public String getDestName() {
		return destName;
	}

	public void setDestName(String destName) {
		this.destName = destName;
	}

}
