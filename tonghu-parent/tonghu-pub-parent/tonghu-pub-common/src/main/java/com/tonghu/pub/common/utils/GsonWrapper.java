package com.tonghu.pub.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class GsonWrapper {

	private static final Logger LOGGER = LoggerFactory.getLogger(GsonWrapper.class);
	private static final Logger ERROR_LOGGER = LoggerFactory.getLogger("errorLog");

	public static <T> T fromJson(Gson gson, String json, Class<T> classOfT) {
		T result = null;
		Reader reader = null;
		JsonReader jsonReader = null;
		try {
			reader = new StringReader(json);
			jsonReader = new JsonReader(reader);
			jsonReader.setLenient(true);
			result = gson.fromJson(jsonReader, classOfT);
		} catch (JsonSyntaxException e) {

		} finally {
			try {
				if(reader != null){
					reader.close();
				}
				if(jsonReader != null){
					jsonReader.close();
				}
			} catch (IOException e) {

			}
		}
		return result;
	}

}
