package com.blankzhou.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

@Configuration
public class Env implements PropertyResolver, EnvironmentAware, InitializingBean {

	private static final String DEFAULT_PID = "00";

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Environment env;

	private EnvType envType = EnvType.DEV; // 默认dev环境

	/** 项目名称 */
	private String projectName;
	/** 服务ip地址。换成下划线形式 */
	private String serverName;
	/** 服务ip */
	private String serverIp;

	public String getClientId() {
		return clientId;
	}

	public String getPid() {
		return pid;
	}

	public String getProductLine() {
		return productLine;
	}

	/** 客户端标识 */
	private String clientId;
	/** 平台id */
	private String pid;
	/** 产品线编码 */
	private String productLine;

	@Override
	public void afterPropertiesSet() throws Exception {

		setEnv();

		checkAndSetProjectName();

		checkAndSetServerName();

		checkAndSetHeaderParam();
	}

	/**
	 * 检查并设置通用请求头所需参数
	 * 
	 * spring.application.id : 必填 4位数字，首位非0
	 */
	private void checkAndSetHeaderParam() {
		clientId = env.getProperty("app.client.id");

		pid = env.getProperty("app.client.pid", DEFAULT_PID);

		productLine = env.getProperty("app.client.productline");
	}

	/**
	 * 设置项目名称
	 */
	private void checkAndSetProjectName() {
		projectName = env.getProperty("spring.application.name");
		if (projectName == null) {
			throw new IllegalStateException("[ spring.application.name]  can't been null ");
		}
	}

	/**
	 * 设置环境 默认dev环境
	 */
	private void setEnv() {
		String[] profiles = env.getActiveProfiles();
		if (profiles != null && profiles.length > 0) {
			if ("prod".equals(profiles[0])) {
				envType = EnvType.PROD;
			} else if ("stage".equals(profiles[0])) {
				envType = EnvType.STAGE;
			}
		}
	}

	/**
	 * 检查并设置服务器ip信息
	 */
	private void checkAndSetServerName() {
		String ip = null;
		if (isProd()) {
			ip = getLocalAddress(env.getProperty("__prod.ip.prefix__", "10.1"), null);
		} else {
			ip = getLocalAddress("192.168.", null);
			if (null == ip) {
				ip = getLocalAddress("172.", null);
				if (null == ip) {
					ip = getLocalAddress(env.getProperty("__dev.ip.prefix__", "10."), null);
				}
			}
		}
		if (ip == null) {
			throw new IllegalStateException("can't gen serverName, ip not right, " + env);
		}

		serverIp = ip;

		serverName = "s" + ip.replace(".", "_");
	}

	/**
	 * 获取指定开头的IP
	 */
	private String getLocalAddress(String prefix, String defaultAddress) {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				Enumeration<InetAddress> addresses = interfaces.nextElement().getInetAddresses();
				while (addresses.hasMoreElements()) {
					String address = addresses.nextElement().getHostAddress();
					if (address.startsWith(prefix)) {
						return address;
					}
				}
			}
		} catch (Exception e) {
			logger.error("get ip error ", e);
		}
		return defaultAddress;
	}

	public boolean isProd() {
		return envType == EnvType.PROD;
	}

	public boolean isDev() {
		return envType == EnvType.DEV;
	}

	public boolean isStage() {
		return envType == EnvType.STAGE;
	}

	/**
	 * 根据环境,获取ip地址
	 * 
	 * @return
	 */
	public String getServerName() {
		return serverName;
	}

	public String getProjectName() {
		return env.getProperty("spring.application.name", "unknown-server-name");
	}

	public String getServerIp() {
		return serverIp;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}

	@Override
	public boolean containsProperty(String key) {
		return env.containsProperty(key);
	}

	@Override
	public String getProperty(String key) {
		return env.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return env.getProperty(key, defaultValue);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetType) {
		return env.getProperty(key, targetType);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		return env.getProperty(key, targetType, defaultValue);
	}

	@Override
	public <T> Class<T> getPropertyAsClass(String key, Class<T> targetType) {
		return env.getPropertyAsClass(key, targetType);
	}

	@Override
	public String getRequiredProperty(String key) throws IllegalStateException {
		return env.getRequiredProperty(key);
	}

	@Override
	public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
		return env.getRequiredProperty(key, targetType);
	}

	@Override
	public String resolvePlaceholders(String text) {
		return env.resolvePlaceholders(text);
	}

	@Override
	public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
		return env.resolveRequiredPlaceholders(text);
	}

	enum EnvType {
		PROD, DEV, STAGE;
	}
}
