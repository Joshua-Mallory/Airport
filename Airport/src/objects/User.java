package objects;

public class User {

	private Integer id;
	private Integer role_id;
	private String given_name;
	private String family_name;
	private String username;
	private String email;
	private String password;
	private String phone;

	public User() {
	}

	public User(Integer id, Integer role_id, String given_name, String family_name, String username, String email,
			String password, String phone) {
		this.id = id;
		this.role_id = role_id;
		this.given_name = given_name;
		this.family_name = family_name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getGiven_name() {
		return given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
