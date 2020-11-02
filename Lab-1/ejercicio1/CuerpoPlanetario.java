package p1.ejercicio1;

public class CuerpoPlanetario extends CuerpoAstrofisico {
	double albedo, inclinacion, excentricidad, semiejeMayor, perihelio;
	Estrella estrellaOrbitada;

	public CuerpoPlanetario(double diametro, double superficie, double volumen, double masa, double densidad,
			double gravedad, double semiejeMayor, double perihelio, double temperaturaSuperficie,
			double albedo, double inclinacion, double excentricidad, Estrella estrellaOrbitada) {
		super(diametro, superficie, volumen, masa, densidad, gravedad, semiejeMayor, perihelio, temperaturaSuperficie);
		this.albedo = albedo;
		this.inclinacion = inclinacion;
		this.excentricidad = excentricidad;
		this.estrellaOrbitada = estrellaOrbitada;
	}



	public double getAlbedo() {
		return albedo;
	}

	public void setAlbedo(double albedo) {
		this.albedo = albedo;
	}

	public double getInclinacion() {
		return inclinacion;
	}

	public void setInclinacion(double inclinacion) {
		this.inclinacion = inclinacion;
	}

	public double getExcentricidad() {
		return excentricidad;
	}

	public void setExcentricidad(double excentricidad) {
		this.excentricidad = excentricidad;
	}
	
	
}
