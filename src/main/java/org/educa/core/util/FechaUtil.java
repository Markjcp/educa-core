package org.educa.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class FechaUtil {

	public static Date formateFechaDDMMYYYYUsa(Date fechaBase){
		SimpleDateFormat format = getSimpleDateFormatUsa();
		String cadenaFecha = cadenaFechaDDMMYYYYUsa(fechaBase);		
		Date fecha = null;
		
		try {
			fecha = format.parse(cadenaFecha);
		} catch (Exception ex) {
			//Nada		
		}

		return fecha;
	}
	
	public static String cadenaFechaDDMMYYYYUsa(Date fechaBase){
		String fechaDesdeFormateada = "";
		if(fechaBase != null){
			SimpleDateFormat format = getSimpleDateFormatUsa();
			fechaDesdeFormateada = format.format(fechaBase);
		}
		
		return fechaDesdeFormateada;
	}
	
	public static SimpleDateFormat getSimpleDateFormatUsa(){
		//En la base esta: 2016-10-10
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public static Date formateFechaDDMMYYYYEs(Date fechaBase){
		SimpleDateFormat format = getSimpleDateFormatEs();
		String cadenaFecha = cadenaFechaDDMMYYYYEs(fechaBase);		
		Date fecha = null;
		
		try {
			fecha = format.parse(cadenaFecha);
		} catch (Exception ex) {
			//Nada		
		}

		return fecha;
	}
	
	public static String cadenaFechaDDMMYYYYEs(Date fechaBase){
		String fechaDesdeFormateada = "";
		if(fechaBase != null){
			SimpleDateFormat format = getSimpleDateFormatEs();
			fechaDesdeFormateada = format.format(fechaBase);
		}
		
		return fechaDesdeFormateada;
	}
	
	public static SimpleDateFormat getSimpleDateFormatEs(){
		return new SimpleDateFormat("dd-MM-yyyy");
	}
}
