package p1.ejercicio1;

public class Satelite extends CuerpoPlanetario {
	double presion, distanciaPlanetaOrbitado;	// distancia en UAs
	CuerpoPlanetario planetaOrbitado;
	
	public Satelite(double diametro, double superficie, double volumen, double masa, double densidad, double gravedad,
			double semiejeMayor, double perihelio, double temperaturaSuperficie, double albedo, double inclinacion,
			double excentricidad, Estrella estrellaOrbitada, double presion, double distanciaPlanetaOrbitado, 
			CuerpoPlanetario planetaOrbitado) {
		super(diametro, superficie, volumen, masa, densidad, gravedad, semiejeMayor, perihelio, temperaturaSuperficie, albedo,
				inclinacion, excentricidad, estrellaOrbitada);
		this.presion = presion;
		this.distanciaPlanetaOrbitado = distanciaPlanetaOrbitado;
		this.planetaOrbitado = planetaOrbitado;
		
	}



	public double getPresion() {
		return presion;
	}

	public void setPresion(double presion) {
		this.presion = presion;
	}

	public CuerpoPlanetario getPlanetaOrbitado() {
		return planetaOrbitado;
	}

	public void setPlanetaOrbitado(CuerpoPlanetario planetaOrbitado) {
		this.planetaOrbitado = planetaOrbitado;
	}



	@Override
	public String toString() {
		return "Satelite [presion=" + presion + ", distanciaPlanetaOrbitado=" + distanciaPlanetaOrbitado
				+ ", planetaOrbitado=" + planetaOrbitado + ", albedo=" + albedo + ", inclinacion=" + inclinacion
				+ ", excentricidad=" + excentricidad + ", semiejeMayor=" + semiejeMayor + ", perihelio=" + perihelio
				+ ", estrellaOrbitada=" + estrellaOrbitada + ", diametro=" + diametro + ", superficie=" + superficie
				+ ", volumen=" + volumen + ", masa=" + masa + ", densidad=" + densidad + ", gravedad=" + gravedad
				+ ", temperaturaSuperficie=" + temperaturaSuperficie + "]";
	}
	
	
	
}
