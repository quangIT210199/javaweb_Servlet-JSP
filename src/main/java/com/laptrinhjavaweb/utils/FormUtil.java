package com.laptrinhjavaweb.utils;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
//Tạo class FromUtil ( mapping các tham số name trong view tới controller) trong package Util
public class FormUtil {
	//Map key và value các cái fiell trong request nhận về với field của các model
	//Hàm toModel dựa vào model truyền vào và mapping với cái thẻ name
	public static <T> T toModel (Class <T> tClass, HttpServletRequest request) {
		T object = null;
		try {
			object = tClass.newInstance();
			//Sử dụng BeanUtil pass cái Map trong request ra
			BeanUtils.populate(object, request.getParameterMap());
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println(e.getMessage());
		} catch (InvocationTargetException e) {
			System.out.println(e.getMessage());
		}
		
		return object;
	}
}
