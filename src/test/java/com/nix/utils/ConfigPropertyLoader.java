package com.nix.utils;

import java.util.Properties;

import org.hibernate.cfg.Configuration;

public class ConfigPropertyLoader {

	private static Properties properties = new Configuration().configure().getProperties();

	public static String getPropertyByName(String name) {
		return properties.getProperty(name);
	}

}
