package com.polaris.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
		String line;
	    while ((line = br.readLine()) != null) {
	    	String[] parts = line.split("\t");
	    	if(parts.length == 12) {
		    	cities.add(parts[0]);
	    	}
	    	
	    }
	    return cities;
	}

	@RequestMapping(method=RequestMethod.GET, value="/search", 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public List<Hotel> getHotelByCity(@RequestParam(value = "city", required=false) String city, @RequestParam(value = "hotel", required=false) String hotelId) throws Exception {
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
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}", 
    		produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public Hotel getHotelById(@PathVariable int id) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("trip_hotels_for_ui.txt").getInputStream()));
		List<Hotel> hotels = new ArrayList<>();
		String line;
	    while ((line = br.readLine()) != null) {
	    	String[] parts = line.split("\t");
	    }
	    throw new Exception("Hotel not found");
	}

}
