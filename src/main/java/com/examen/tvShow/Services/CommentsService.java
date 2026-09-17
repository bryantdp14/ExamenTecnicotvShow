package com.examen.tvShow.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.examen.tvShow.Dtos.CommentRequestDto;
import com.examen.tvShow.Models.CommentsModel;
import com.examen.tvShow.Repository.CommentsRepository;
import java.util.Collections;
import java.util.List;

@Service
public class CommentsService {

	@Autowired
    private CommentsRepository commentsRepository;


    // Guarda un comentario validando la petición
    public CommentsModel saveComment(CommentRequestDto dto) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("El cuerpo de la petición no puede ser nulo.");
            }
            if (dto.getShow_id() == null) {
                throw new IllegalArgumentException("El campo 'show_id' es obligatorio.");
            }
            if (dto.getComment() == null || dto.getComment().trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'comment' no puede estar vacío.");
            }
            if (dto.getRating() == null || dto.getRating().trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'rating' es obligatorio.");
            }

            int numericRating;
            try {
                numericRating = Integer.parseInt(dto.getRating().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El rating debe ser un valor numérico entero (0-5).");
            }

            if (numericRating < 0 || numericRating > 5) {
                throw new IllegalArgumentException("El rating debe estar en el rango de 0 a 5.");
            }

            CommentsModel comment = new CommentsModel();
            comment.setShowId(dto.getShow_id());
            comment.setComment(dto.getComment().trim());
            comment.setRating(String.valueOf(numericRating));

            return commentsRepository.save(comment);

        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Error al guardar en MongoDB: " + ex.getMessage(), ex);
        }
    }

    // Obtiene los comentarios por ID de show
    public List<CommentsModel> getCommentsByShowId(Long showId) {
        try {
            if (showId == null) {
                return Collections.emptyList();
            }
            return commentsRepository.findByShowId(showId);
        } catch (Exception ex) {
            throw new RuntimeException("Error al consultar comentarios por showId: " + ex.getMessage(), ex);
        }
    }

    // Obtiene los comentarios para varios IDs de show
    public List<CommentsModel> getCommentsByShowIds(List<Long> showIds) {
        try {
            if (showIds == null || showIds.isEmpty()) {
                return Collections.emptyList();
            }
            return commentsRepository.findByShowIdIn(showIds);
        } catch (Exception ex) {
            throw new RuntimeException("Error al consultar comentarios por lote: " + ex.getMessage(), ex);
        }
    }
}