package com.example.tiaaccidentes;

import java.net.URI;
import java.net.URISyntaxException;

public class Reporte {
	private int id;
	private String adress;
	private double latitude, longitude;
	private String time;
	private Incide incidencia;
	public enum Incide{NULA, BAJA, MEDIA, ALTA};
	private URI uri;
	public Reporte(int id, String adress, double latitude, double longitude,
			String time, int incidencia, String uri) {
		this.id = id;
		this.adress = adress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.time = time;
		switch (incidencia){
			case 1: this.incidencia = Incide.BAJA; break;
			case 2: this.incidencia = Incide.MEDIA; break;
			case 3: this.incidencia = Incide.ALTA; break;
			default: this.incidencia = Incide.NULA; break;
		}
		try {
			this.uri = new URI(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getId() {
		return id;
	}
	public String getAdress() {
		return adress;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getTime() {
		return time;
	}
	public Incide getIncidencia() {
		return incidencia;
	}
	public URI getUri() {
		return uri;
	}
	
}
