package com.gantrex.config;

import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ConfigurationConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gantrex.exceptions.ConfigNotInitializedException;
import com.gantrex.filter.EmpowerFilterConfig;
import com.gantrex.filter.FilterConfig;

import streamserve.context.Context;
import streamserve.context.ContextFactory;

/*
 * The Singleton Configuration class.This interface allows accessing properties from configuration file.
 * There is a generic getProperty()method, which returns the value of the queried property in its raw datatype. 
 * Other getter methods are for returning specific properties applicable for project. For most of the property getter methods an 
 * overloaded version exists that allows to specify a default value, which will be returned if the queried property 
 * cannot be found in the configuration. The behavior of the methods that do not take a default value in case of a 
 * missing property is not defined returns null 
 * 
 */

public class ConfigurationManager {

	private Configuration config;

	private static ConfigurationManager instance;

	private static final Logger log = LoggerFactory.getLogger(ConfigurationManager.class);

	private static final String empowerBaseURLKey = "EmpowerBaseURL";
	private static final String otdsBaseURLKey = "OtdsBaseURL";
	private static final String restLogLevel = "RestLogLevel";
	private static final String clientIDKey = "ClientID";
	private static final String clientSecretKey = "ClientSecret";
	private static final String grantTypeKey = "GrantType";
	private static final String empowerUserKey = "EmpowerUser";
	private static final String empowerUserPasswordKey = "EmpowerUserPassword";
	private static final String appIDKey = "ApplicationID";
	private static final String threadsKey = "Threads";
	private static final String watchDirectoryKey = "WatchDirectory";
	private static final String filesExtensionFilterKey = "FilesToWatch";
	private static final String archiveDirectoryKey = "ArchiveDirectory";
	private static final String errorDirectoryKey = "ErrorDirectory";
	private static final String runTypeKey = "RunType";
	private static final String secretLogKey = "SecretLogType";
	private static final String exportDirectoryKey = "ExportDirectory";

	public static ConfigurationManager getInstance() {
		// double checked threadsafe Singleton implementation

		if (instance == null) {
			synchronized (ConfigurationManager.class) {
				if (instance == null) {
					log.info("Configuration Instance is null, Initializing new one");

					instance = new ConfigurationManager();
				}
			}
		}

		return instance;

	}

	public ConfigurationManager() {

		Context context = ContextFactory.acquireContext(ContextFactory.ServiceContextType);

		streamserve.context.Configuration ssConfig = context.getInterface(streamserve.context.Configuration.class);
		FilterConfig filterConfig = ssConfig.getInterface(EmpowerFilterConfig.class);
		Properties props = filterConfig.getProperties();
		if (props == null) {
			throw new NullPointerException("Properties From Filter Config is null");
		}

		config = ConfigurationConverter.getConfiguration(props);
	}

	public Boolean isInitialized() {
		return config != null;
	}

	public Object getProperty(String key) throws ConfigNotInitializedException {
		log.info("Get Property :  {} ", key);
		if (config != null) {
			return config.getProperty(key);
		} else {
			log.debug("Configuration Object is null while getting property {}", key);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getEmpowerBaseURL() throws ConfigNotInitializedException {
		log.info("Get Empower BASE_URL using key :  {} ", empowerBaseURLKey);
		if (config != null) {
			return config.getString(empowerBaseURLKey);
		} else {
			log.debug("Configuration Object is null while getting Empower BASE_URL using {}", empowerBaseURLKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getOTDSBaseURL() throws ConfigNotInitializedException {
		log.info("Get OTDS BASE_URL using key :  {} ", otdsBaseURLKey);
		if (config != null) {
			return config.getString(otdsBaseURLKey);
		} else {
			log.debug("Configuration Object is null while getting OTDS BASE_URL using {}", otdsBaseURLKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getClientID() throws ConfigNotInitializedException {
		log.info("Get Client ID using key :  {} ", clientIDKey);
		if (config != null) {
			return config.getString(clientIDKey);
		} else {
			log.debug("Configuration Object is null while getting Client ID using {}", clientIDKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getClientSecret() throws ConfigNotInitializedException {
		log.info("Get Client Secret using key :  {} ", clientSecretKey);
		if (config != null) {
			return config.getString(clientSecretKey);
		} else {
			log.debug("Configuration Object is null while getting Client Secret using {}", clientSecretKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getEmpowerUser() throws ConfigNotInitializedException {
		log.info("Get Empower User using key :  {} ", empowerUserKey);
		if (config != null) {
			return config.getString(empowerUserKey);
		} else {
			log.debug("Configuration Object is null while getting Empower User using {}", empowerUserKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getGrantType() throws ConfigNotInitializedException {
		log.info("Get Grant Type using key :  {} ", grantTypeKey);
		if (config != null) {
			return config.getString(grantTypeKey);
		} else {
			log.debug("Configuration Object is null while getting Grant Type using {}", grantTypeKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getEmpowerUserPassword() throws ConfigNotInitializedException {
		log.info("Get Empower User's Password using key :  {} ", empowerUserPasswordKey);
		if (config != null) {
			return config.getString(empowerUserPasswordKey);
		} else {
			log.debug("Configuration Object is null while getting Empower User's Password using {}",
					empowerUserPasswordKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getApplicationID() throws ConfigNotInitializedException {
		log.info("Get Application ID using key :  {} ", appIDKey);
		if (config != null) {
			return config.getString(appIDKey);
		} else {
			log.debug("Configuration Object is null while getting Application ID using {}", appIDKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public int getRunType() throws ConfigNotInitializedException {
		log.info("Get Run type using key :  {} ", runTypeKey);
		if (config != null) {
			return config.getInt(runTypeKey);
		} else {
			log.debug("Configuration Object is null while getting run type using {}", runTypeKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public int getThreads() throws ConfigNotInitializedException {
		log.info("Get Threads using key :  {} ", threadsKey);
		if (config != null) {
			return config.getInt(threadsKey);
		} else {
			log.debug("Configuration Object is null while getting Threads using {}", threadsKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getWatchDirectory() throws ConfigNotInitializedException {
		log.info("Get Watch Directory using key :  {} ", watchDirectoryKey);
		if (config != null) {
			return config.getString(watchDirectoryKey);
		} else {
			log.debug("Configuration Object is null while getting Watch Directory using {}", watchDirectoryKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getErrorDirectory() throws ConfigNotInitializedException {
		log.info("Get Error Directory using key :  {} ", errorDirectoryKey);
		if (config != null) {
			return config.getString(errorDirectoryKey);
		} else {
			log.debug("Configuration Object is null while getting Error Directory using {}", errorDirectoryKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getFileExtensionFilter() throws ConfigNotInitializedException {
		log.info("Get File Extension Filter using key :  {} ", filesExtensionFilterKey);
		if (config != null) {
			return config.getString(filesExtensionFilterKey);
		} else {
			log.debug("Configuration Object is null while getting File Extension Filter using {}",
					filesExtensionFilterKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getArchiveDirectory() throws ConfigNotInitializedException {
		log.info("Get Archive Directory using key :  {} ", archiveDirectoryKey);
		if (config != null) {
			return config.getString(archiveDirectoryKey);
		} else {
			log.debug("Configuration Object is null while getting Archive Directory using {}", archiveDirectoryKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	public String getExportDirectory() throws ConfigNotInitializedException {
		log.info("Get Export Directory using key :  {} ", exportDirectoryKey);
		if (config != null) {
			return config.getString(exportDirectoryKey);
		} else {
			log.debug("Configuration Object is null while getting Export Directory using {}", exportDirectoryKey);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

	// undoumented
	public boolean getSecretLog() {
		if (config != null) {
			try {
				int secretLog = config.getInt(secretLogKey);
				return secretLog == 0 ? false : true;
			} catch (Exception e) {
				return false;
			}

		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return the log level for ohttp log interceptor 0 -> NONE, 1 -> BASIC, 2 ->
	 *         HEADERS, 3 -> BODY
	 * @throws ConfigNotInitializedException
	 */
	public int getInterceptorLogLevel() throws ConfigNotInitializedException {
		log.info("Get Interceptor Log Level using key :  {} ", restLogLevel);
		if (config != null) {
			return config.getInt(restLogLevel);
		} else {
			log.debug("Configuration Object is null while getting Interceptor Log Level using {}", restLogLevel);
			throw new ConfigNotInitializedException("Configuration Object is null");
		}
	}

}
