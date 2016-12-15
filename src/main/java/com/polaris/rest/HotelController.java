package com.polaris.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

	@RequestMapping(method=RequestMethod.GET, 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Hotel> getHotels() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("trip_hotels_for_ui.txt").getInputStream()));
		List<Hotel> hotels = new ArrayList<>();
		String line;
	    while ((line = br.readLine()) != null) {
	    	String[] parts = line.split("\t");
	    	if(parts.length == 12) {
	    		Hotel hotel = new Hotel();
		    	hotel.setCity(parts[0]);
		    	hotel.setCity(parts[1]);
		    	hotel.setHotelURL(parts[2]);
		    	hotel.setCityID(parts[3]);
		    	hotel.setHotelID(parts[4]);
		    	hotel.setBubbles(parts[5]);
		    	hotel.setNumBubbles(Double.parseDouble(parts[6]));
		    	hotel.setNumberOfReviews(parts[7]);
		    	hotel.setNumReviews(Integer.parseInt(parts[8]));
		    	hotel.setHotelReviewURl(parts[9]);
		    	hotel.setHotelRanking(parts[10]);
		    	hotel.setNumHotelRanking(Integer.parseInt(parts[11]));
		    	hotels.add(hotel);
	    	}
	    	
	    }
	    return hotels;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/cities", 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Set<String> getCities() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("trip_hotels_for_ui.txt").getInputStream()));
		Set<String> cities = new HashSet<>();
//		cities.add("Recent");
		String line;
	    while ((line = br.readLine()) != null) {
	    	String[] parts = line.split("\t");
	    	if(parts.length == 12) {
		    	cities.add(parts[0]);
	    	}
	    	
	    }
	    return cities;
	}
	
	public List<Hotel> getLastAnalysis() throws Exception {
		Path path = Paths.get(reportPath, "recent_analysis.txt");
	    File file = path.toFile();
		BufferedReader br = new BufferedReader(new FileReader(file));
//		BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("last_analysis.txt").getInputStream(),StandardCharsets.UTF_8));
		List<Hotel> hotels = new ArrayList<>();
		String line;
	    while ((line = br.readLine()) != null) {
	    	String[] parts = line.split("\t");
	    	if(parts.length == 12) {
	    		Hotel hotel = new Hotel();
		    	hotel.setCity(parts[0]);
		    	hotel.setHotelName(parts[1]);
		    	hotel.setHotelURL(parts[2]);
		    	hotel.setCityID(parts[3]);
		    	hotel.setHotelID(parts[4]);
		    	hotel.setBubbles(parts[5]);
		    	hotel.setNumBubbles(Double.parseDouble(parts[6]));
		    	hotel.setNumberOfReviews(parts[7]);
		    	hotel.setNumReviews(Integer.parseInt(parts[8]));
		    	hotel.setHotelReviewURl(parts[9]);
		    	hotel.setHotelRanking(parts[10]);
		    	hotel.setNumHotelRanking(Integer.parseInt(parts[11]));
		    	hotels.add(hotel);
	    	}
	    }
	    return hotels;
	}

	@RequestMapping(method=RequestMethod.GET, value="/search", 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Hotel> getHotelByCity(@RequestParam(value = "city", required=false) String city, @RequestParam(value = "hotel", required=false) String hotelId) throws Exception {
		if(city.equals("Recent")) {
			return getLastAnalysis();
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("trip_hotels_for_ui.txt").getInputStream(),StandardCharsets.UTF_8));
		List<Hotel> hotels = new ArrayList<>();
		String line;
	    while ((line = br.readLine()) != null) {
	    	String[] parts = line.split("\t");
	    	if(parts.length == 12 && ((hotelId==null && parts[0].equals(city)||(hotelId!=null && parts[3].equals(city) && parts[4].equals(hotelId))))) {
	    		Hotel hotel = new Hotel();
		    	hotel.setCity(parts[0]);
		    	hotel.setHotelName(parts[1]);
		    	hotel.setHotelURL(parts[2]);
		    	hotel.setCityID(parts[3]);
		    	hotel.setHotelID(parts[4]);
		    	hotel.setBubbles(parts[5]);
		    	hotel.setNumBubbles(Double.parseDouble(parts[6]));
		    	hotel.setNumberOfReviews(parts[7]);
		    	hotel.setNumReviews(Integer.parseInt(parts[8]));
		    	hotel.setHotelReviewURl(parts[9]);
		    	hotel.setHotelRanking(parts[10]);
		    	hotel.setNumHotelRanking(Integer.parseInt(parts[11]));
		    	hotels.add(hotel);
	    	}
	    }
	    return hotels;
	}
	
	private final String reportPath = "/home/PolarisCS598/CS598/";
//	private final String reportPath = "/Users/appd110/Documents/develop/CS598/ProjectFile/";

	@RequestMapping(method=RequestMethod.GET, value="report/review/{id}", 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Report getHotelReview(@PathVariable int id) throws Exception {
	    Path path = Paths.get(reportPath, String.valueOf(id), "reviews.txt");
	    File from = path.toFile();
	    if(!from.exists()) {
	    	throw new Exception("Report not found");
	    }
	    BufferedReader br = new BufferedReader(new FileReader(from));
	    Map<Long, String> map = new HashMap<>();
	    
		String line = null;
		while ((line = br.readLine()) != null) {
	    	String[] parts = line.split("\t");
	    	if(parts.length == 2) {
	    		map.put(Long.valueOf(parts[0]), parts[1]);
	    	}
		}
	 
		br.close();
		
		HashMap<String, ReviewScore> topFakeMap = getHotelReviewScore(id, true);
		HashMap<String, ReviewScore> topTrueMap = getHotelReviewScore(id, false);
	    List<ReviewScore> topFake = new ArrayList<ReviewScore>(topFakeMap.values());
	    List<ReviewScore> topTrue = new ArrayList<ReviewScore>(topTrueMap.values());
	    for(ReviewScore rs : topFake) {
	    	System.out.println(map.get(rs.getId()));
	    	rs.setReview(map.get(rs.getId()));
	    }
	    for(ReviewScore rs : topTrue) {
	    	rs.setReview(map.get(rs.getId()));
	    }
	    Report report = new Report();
	    report.setTopFake(topFake);
	    report.setTopTrue(topTrue);
	    
	    File topicFile = Paths.get(reportPath, String.valueOf(id), "topics.txt").toFile();
	    if(topicFile.exists()) {
	    	BufferedReader br1 = new BufferedReader(new FileReader(topicFile));
	    	Set<String> topics = new HashSet<>();
		    
			String line1 = null;
			while ((line1 = br1.readLine()) != null) {
		    	topics.add(line1);
			}
		 
			br.close();
			report.setTopics(new ArrayList<>(topics));
	    }
	    
	    return report;
	}
	
	public HashMap<String, ReviewScore> getHotelReviewScore(int id, boolean isFake) throws Exception {
		String fileName = "top_10_genuine_reviews.json";
		if(isFake) {
			fileName = "top_10_fake_reviews.json";
		}
	    Path path = Paths.get(reportPath, String.valueOf(id), fileName);
	    ObjectMapper mapper = new ObjectMapper(); 
	    File from = path.toFile();
	    if(!from.exists()) {
	    	throw new Exception("Report not found");
	    }
	    TypeReference<HashMap<String,ReviewScore>> typeRef 
	            = new TypeReference<HashMap<String,ReviewScore>>() {};

	    HashMap<String,ReviewScore> o = mapper.readValue(from, typeRef); 
	    
	    return o;
	}

}
