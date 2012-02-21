/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.pentaho.plugin.reporting.rest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This is the Map returned for all objects. The properties are just key/value pairs. If an object
 * has subobjects those are just lists of SimpleObjects
 */
public class SimpleObject extends LinkedHashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	public SimpleObject() {
		super();
	}
	
	/**
	 * Puts a property in this map, and returns the map itself (for chained method calls)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public SimpleObject add(String key, Object value) {
		put(key, value);
		return this;
	}
	
	public static Object path(Object object, String path) {
		String[] components = path.split("\\.");
		for (String s : components) {
			try {
	            object = PropertyUtils.getProperty(object, s);
            }
            catch (Exception ex) {
	            return null;
            }
		}
		return object;
	}
	
	public Object path(String path) {
		return path(this, path);
	}
	
	/**
	 * Creates an instance from the given json string.
	 * 
	 * @param json
	 * @return the simpleObject
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static SimpleObject parseJson(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleObject object = objectMapper.readValue(json, SimpleObject.class);
		return object;
	}
}
