package objects;

public class Airplane {

	private Integer id;
	private Integer type_id;

	public Airplane() {
	};

	public Airplane(Integer id, Integer type_id) {
		this.id = id;
		this.type_id = type_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType_id() {
		return type_id;
	}

	public void setType_id(Integer type_id) {
		this.type_id = type_id;
	}

}
