package elementos;

public class Estudiante extends Usuario {
	public Estudiante(String login, String password) {
		super(login, password);
	}

	//getters y setters
	public String getLogin() {
		return super.getLogin();
	}

	public void setLogin(String login) {
		super.setLogin(login);
	}

	public String getPassword() {
		return super.getPassword();
	}

	public void setPassword(String password) {
		super.setPassword(password);
	}

	@Override
	public String toString() {
		return "Estudiante [login=" + super.getLogin() + ", password=" + super.getPassword() + "]";
	}
	
	
}
