package com.examen.tvShow.Controller;

import com.examen.tvShow.Dtos.CommentRequestDto;
import com.examen.tvShow.Models.CommentsModel;
import com.examen.tvShow.Services.CommentsService;
import com.examen.tvShow.Utils.RequestHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentsController {

	@Autowired
    private CommentsService _commentsService;

    // Agrega un nuevo comentario a un show
    @PostMapping
    public ResponseEntity<Object> addComment(@RequestBody CommentRequestDto request) {
        try {
            CommentsModel savedComment = _commentsService.saveComment(request);
            return RequestHandler.generarRespuesta(
                    "Comentario registrado correctamente",
                    HttpStatus.CREATED,
                    savedComment
            );
        } catch (IllegalArgumentException ex) {
            return RequestHandler.generarRespuesta(
                    ex.getMessage(),
                    HttpStatus.BAD_REQUEST,
                    null
            );
        } catch (Exception ex) {
            return RequestHandler.generarRespuesta(
                    "Error al registrar el comentario: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }

    // Obtiene los comentarios de un show
    @GetMapping("/show/{showId}")
    public ResponseEntity<Object> getCommentsByShow(@PathVariable Long showId) {
        try {
            List<CommentsModel> comments = _commentsService.getCommentsByShowId(showId);
            return RequestHandler.generarRespuesta(
                    "Comentarios recuperados exitosamente",
                    HttpStatus.OK,
                    comments
            );
        } catch (Exception ex) {
            return RequestHandler.generarRespuesta(
                    "Error al obtener los comentarios: " + ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null
            );
        }
    }
}