package com.polaris.rest;

import java.util.List;

public class Report {
	private List<ReviewScore> topTrue;
	private List<ReviewScore> topFake;
	private List<String> topics;
	public List<ReviewScore> getTopTrue() {
		return topTrue;
	}
	public void setTopTrue(List<ReviewScore> topTrue) {
		this.topTrue = topTrue;
	}
	public List<ReviewScore> getTopFake() {
		return topFake;
	}
	public void setTopFake(List<ReviewScore> topFake) {
		this.topFake = topFake;
	}
	public List<String> getTopics() {
		return topics;
	}
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	
}
