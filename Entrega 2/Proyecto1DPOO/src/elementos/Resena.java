package elementos;

public class Resena {

	private int idActividad;
	private String opinion;
	private int rating;
	private String loginAutor;
	private String rolAutor;
	
	//Constructor
	public Resena(int idActividad, String opinion, int rating, String loginAutor, String rolAutor) {
		this.idActividad = idActividad;
		this.opinion = opinion;
		this.rating = rating;
		this.loginAutor = loginAutor;
		this.rolAutor = rolAutor;
	}

	//Getters y Setters
	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getLoginAutor() {
		return loginAutor;
	}

	public void setLoginAutor(String loginAutor) {
		this.loginAutor = loginAutor;
	}

	public String getRolAutor() {
		return rolAutor;
	}

	public void setRolAutor(String rolAutor) {
		this.rolAutor = rolAutor;
	}
}
