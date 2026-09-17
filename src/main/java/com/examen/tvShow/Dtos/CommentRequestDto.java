package com.examen.tvShow.Dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
	private Long show_id;
    private String comment;
    private String rating;


    public Long getShow_id() {
        return show_id;
    }
    public void setShow_id(Long show_id) {
        this.show_id = show_id;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
}
