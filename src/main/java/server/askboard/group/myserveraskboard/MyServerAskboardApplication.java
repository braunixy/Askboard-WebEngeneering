package server.askboard.group.myserveraskboard;

import org.apache.catalina.Context;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class MyServerAskboardApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MyServerAskboardApplication.class, args);
	}
	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
		factory.setTomcatContextCustomizers(Arrays.asList(new CustomCustomizer()));
		return factory;
	}
	
	static class CustomCustomizer implements TomcatContextCustomizer {
		@Override
		public void customize(Context context) {
			context.setUseHttpOnly(false);
		}
	}
}
