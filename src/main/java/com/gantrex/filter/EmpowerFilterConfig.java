package com.gantrex.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import streamserve.service.Configuration;

public class EmpowerFilterConfig implements Configuration, FilterConfig {

	private Properties props;

	private static final Logger log = LoggerFactory.getLogger(EmpowerFilterConfig.class);

	@Override
	public void readConfiguration(InputStream is) {

		props = new Properties();
		try {
			props.load(is);
			log.info("Properties loaded {}", props.keySet().size());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public void updateConfiguration(InputStream arg0) {

	}

	@Override
	public void writeConfiguration(OutputStream arg0) {

	}

	public Properties getProperties() {
		return props;
	}

}
