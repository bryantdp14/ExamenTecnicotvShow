package com.examen.tvShow.Controller;

import com.examen.tvShow.Services.ShowsService;
import com.examen.tvShow.Utils.RequestHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

	@Autowired
    private ShowsService _showsService;

    // Realiza la búsqueda de un show por query
    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(value = "search_query", required = false) String searchQuery) {
        try {
            if (searchQuery == null || searchQuery.trim().isEmpty()) {
                return RequestHandler.generarRespuesta(
                        "El parámetro search_query es obligatorio",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }

            List<Map<String, Object>> shows = _showsService.searchShows(searchQuery);
            return RequestHandler.generarRespuesta(
                    "Búsqueda completada exitosamente",
                    HttpStatus.OK,
                    shows
            );
        } catch (Exception ex) {
            return RequestHandler.generarRespuesta(
                    "Error al realizar la búsqueda de shows: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // Obtiene un show por su ID
    @GetMapping("/{show_id}")
    public ResponseEntity<Object> getShowById(@PathVariable("show_id") Long showId) {
        try {
            if (showId == null || showId <= 0) {
                return RequestHandler.generarRespuesta(
                        "El identificador del show es inválido",
                        HttpStatus.BAD_REQUEST,
                        null
                );
            }

            Map<String, Object> show = _showsService.getShowById(showId);
            if (show == null || show.isEmpty()) {
                return RequestHandler.generarRespuesta(
                        "No se encontró información para el show solicitado",
                        HttpStatus.NOT_FOUND,
                        null
                );
            }

            return RequestHandler.generarRespuesta(
                    "Información del show obtenida exitosamente",
                    HttpStatus.OK,
                    show
            );
        } catch (Exception ex) {
            return RequestHandler.generarRespuesta(
                    "Error al consultar el show: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }
}