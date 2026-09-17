package com.examen.tvShow.Models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "shows")
public class ShowModel {
	   @Id
	    private Long id;
	    private String name;
	    private String network_name;
	    private List<CommentsModel> comments;
	    private String webchannel_name;
	    private String summary;
	    private String genres;
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNetwork_name() {
			return network_name;
		}
		public void setNetwork_name(String network_name) {
			this.network_name = network_name;
		}
		public List<CommentsModel> getComments() {
			return comments;
		}
		public void setComments(List<CommentsModel> comments) {
			this.comments = comments;
		}
		public String getWebchannel_name() {
			return webchannel_name;
		}
		public void setWebchannel_name(String webchannel_name) {
			this.webchannel_name = webchannel_name;
		}
		public String getSummary() {
			return summary;
		}
		public void setSummary(String summary) {
			this.summary = summary;
		}
		public String getGenres() {
			return genres;
		}
		public void setGenres(String genres) {
			this.genres = genres;
		}
	    
	    
}
