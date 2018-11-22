package telran.cars.configuration;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import telran.cars.model.IRentCompany;
import telran.cars.model.RentCompanyEmbedded;
import telran.utils.Persistable;

@ManagedResource
@Configuration
public class CarsServiceConfigurator {

	@Value("${companyName:company.data}")
	String filename;
	
	@Bean
	public IRentCompany getCarsService() {
		return new RentCompanyEmbedded().restoreFromFile(filename);
	}
		
	@Autowired
	IRentCompany rentCompany;
	
	@PreDestroy
	@ManagedOperation
	public void save() {
		((Persistable) rentCompany).saveToFile(filename);
	}

}
