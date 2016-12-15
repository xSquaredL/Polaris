package com.polaris.rest;

public class ReviewScore {
	private long id;
	private double ranking_score;
	private String rating;
	private String review;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getRanking_score() {
		return ranking_score;
	}
	public void setRanking_score(double ranking_score) {
		this.ranking_score = ranking_score;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}

}
