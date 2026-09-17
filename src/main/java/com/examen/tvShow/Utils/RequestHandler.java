package com.examen.tvShow.Utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RequestHandler {
	public static ResponseEntity<Object> generarRespuesta(String mensaje, HttpStatus estatus, Object obj){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Mensaje", mensaje);
		map.put("Estatus",estatus.value());
		map.put("Datos", obj);
		return new ResponseEntity<Object>(map, estatus);
	}
}