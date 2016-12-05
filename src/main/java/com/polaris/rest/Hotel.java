package com.polaris.rest;

import java.io.Serializable;

public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String city;
	private String hotelName;
	private String hotelURL;
	private String cityID;
	private String hotelID;
	private String bubbles;
	private double numBubbles;
	private String numberOfReviews;
	private int numReviews;
	private String hotelReviewURl;
	private String hotelRanking;
	private int numHotelRanking;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelURL() {
		return hotelURL;
	}
	public void setHotelURL(String hotelURL) {
		this.hotelURL = hotelURL;
	}
	public String getCityID() {
		return cityID;
	}
	public void setCityID(String cityID) {
		this.cityID = cityID;
	}
	public String getHotelID() {
		return hotelID;
	}
	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}
	public String getBubbles() {
		return bubbles;
	}
	public void setBubbles(String bubbles) {
		this.bubbles = bubbles;
	}
	public double getNumBubbles() {
		return numBubbles;
	}
	public void setNumBubbles(double numBubbles) {
		this.numBubbles = numBubbles;
	}
	public String getNumberOfReviews() {
		return numberOfReviews;
	}
	public void setNumberOfReviews(String numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}
	public int getNumReviews() {
		return numReviews;
	}
	public void setNumReviews(int numReviews) {
		this.numReviews = numReviews;
	}
	public String getHotelReviewURl() {
		return hotelReviewURl;
	}
	public void setHotelReviewURl(String hotelReviewURl) {
		this.hotelReviewURl = hotelReviewURl;
	}
	public String getHotelRanking() {
		return hotelRanking;
	}
	public void setHotelRanking(String hotelRanking) {
		this.hotelRanking = hotelRanking;
	}
	public int getNumHotelRanking() {
		return numHotelRanking;
	}
	public void setNumHotelRanking(int numHotelRanking) {
		this.numHotelRanking = numHotelRanking;
	}
	
}
