package p1.ejercicio1;

public class CuerpoAstrofisico {
	double diametro, superficie, volumen, masa, 
			densidad, gravedad, semiejeMayor, 
			perihelio, temperaturaSuperficie;

	public CuerpoAstrofisico(double diametro, double superficie, double volumen, double masa, double densidad,
			double gravedad, double semiejeMayor, double perihelio, double temperaturaSuperficie) {
		super();
		this.diametro = diametro;
		this.superficie = superficie;
		this.volumen = volumen;
		this.masa = masa;
		this.densidad = densidad;
		this.gravedad = gravedad;
		this.semiejeMayor = semiejeMayor;
		this.perihelio = perihelio;
		this.temperaturaSuperficie = temperaturaSuperficie;
	}

	public double getDiametro() {
		return diametro;
	}

	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}

	public double getSuperficie() {
		return superficie;
	}

	public void setSuperficie(double superficie) {
		this.superficie = superficie;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	public double getMasa() {
		return masa;
	}

	public void setMasa(double masa) {
		this.masa = masa;
	}

	public double getDensidad() {
		return densidad;
	}

	public void setDensidad(double densidad) {
		this.densidad = densidad;
	}

	public double getGravedad() {
		return gravedad;
	}

	public void setGravedad(double gravedad) {
		this.gravedad = gravedad;
	}

	public double getSemiejeMayor() {
		return semiejeMayor;
	}

	public void setSemiejeMayor(double semiejeMayor) {
		this.semiejeMayor = semiejeMayor;
	}

	public double getPerihelio() {
		return perihelio;
	}

	public void setPerihelio(double perihelio) {
		this.perihelio = perihelio;
	}

	public double getTemperaturaSuperficie() {
		return temperaturaSuperficie;
	}

	public void setTemperaturaSuperficie(double temperaturaSuperficie) {
		this.temperaturaSuperficie = temperaturaSuperficie;
	}
	
	
}
