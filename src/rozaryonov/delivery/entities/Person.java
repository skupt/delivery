package rozaryonov.delivery.entities;

public class Person {
	private long id;
	private String login;
	private String password;
	private String email;
	private String name;
	private String surname;
	private String patronomic;
	private String title;
	private Role role;
	
	public Person() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronomic() {
		return patronomic;
	}

	public void setPatronomic(String patronomic) {
		this.patronomic = patronomic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", login=" + login + ", password=" + password + ", email=" + email + ", name="
				+ name + ", surname=" + surname + ", patronomic=" + patronomic + ", title=" + title + ", role=" + role
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
