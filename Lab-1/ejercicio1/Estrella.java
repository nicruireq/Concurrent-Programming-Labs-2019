package p1.ejercicio1;

public class Estrella extends CuerpoAstrofisico {
	double brillo, luminosidad, temperaturaNucleo, temperaturaCorona;

	public Estrella(double diametro, double superficie, double volumen, double masa, double densidad,
			double gravedad, double temperaturaSuperficie, double brillo, double luminosidad, double temperaturaNucleo, double temperaturaCorona) {
		super(temperaturaCorona, temperaturaCorona, temperaturaCorona, temperaturaCorona, temperaturaCorona, temperaturaCorona, temperaturaCorona, temperaturaCorona, temperaturaCorona);
		this.brillo = brillo;
		this.luminosidad = luminosidad;
		this.temperaturaNucleo = temperaturaNucleo;
		this.temperaturaCorona = temperaturaCorona;
	}

	
	
	public double getBrillo() {
		return brillo;
	}

	public void setBrillo(double brillo) {
		this.brillo = brillo;
	}

	public double getLuminosidad() {
		return luminosidad;
	}

	public void setLuminosidad(double luminosidad) {
		this.luminosidad = luminosidad;
	}

	public double getTemperaturaNucleo() {
		return temperaturaNucleo;
	}

	public void setTemperaturaNucleo(double temperaturaNucleo) {
		this.temperaturaNucleo = temperaturaNucleo;
	}

	public double getTemperaturaCorona() {
		return temperaturaCorona;
	}

	public void setTemperaturaCorona(double temperaturaCorona) {
		this.temperaturaCorona = temperaturaCorona;
	}
	
	
}
