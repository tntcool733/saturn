package saturn.spring.cloud.serviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import saturn.spring.cloud.serviceprovider.controller.PerformanceInteceptor;

@SpringBootApplication
public class ServiceProviderApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PerformanceInteceptor())
				.addPathPatterns("/user/**");
	}

}
