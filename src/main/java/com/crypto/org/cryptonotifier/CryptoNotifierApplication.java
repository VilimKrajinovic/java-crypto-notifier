package com.crypto.org.cryptonotifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import reactor.tools.agent.ReactorDebugAgent;

@SpringBootApplication
@EnableConfigurationProperties
public class CryptoNotifierApplication {

	public static final String REACTOR_DEBUG_AGENT_ENABLED_PROPERTY = "reactor.debug.agent.enabled";

	public static void main(String[] args) {
		if(isDebugAgentEnabled()) ReactorDebugAgent.init();
		SpringApplication.run(CryptoNotifierApplication.class, args);
	}

	public static boolean isDebugAgentEnabled(){
		return "true".equals(System.getProperty(REACTOR_DEBUG_AGENT_ENABLED_PROPERTY, "false"));
	}

}
