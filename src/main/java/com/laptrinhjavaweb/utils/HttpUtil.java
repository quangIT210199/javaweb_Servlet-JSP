package com.laptrinhjavaweb.utils;

import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

//lấy data từ request và parser ra string json
public class HttpUtil {
	
	private String value;
	
	public HttpUtil (String value) {
		this.value = value;
	}
	//Dùng cho API
	//Map các cái field nhận về với field của các model
	public <T> T toModel (Class <T> tClass) {
		//tự động phân tách các field ra model cần: có sẵn trong thư viện jackson-binding ở pom.xml
		try {
			return new ObjectMapper().readValue(value, tClass);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	public static HttpUtil of(BufferedReader reader) {//Vì hàm request.getReader(BufferedReader) nên nhận vào là BufferedReader 
		//Convert data từ client json sang String json
		StringBuilder sb = new StringBuilder();
		
		try {
			String line;
		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return new HttpUtil(sb.toString());
	}
}
