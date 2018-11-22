package telran.cars.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.cars.dto.*;
import static telran.cars.dto.RentCompanyConstants.*;
import telran.cars.model.IRentCompany;

@ManagedResource
@RestController
public class CarsController {

	@Autowired
	IRentCompany rentCompany;
	
	@Value("${gasPrice:10}")
	int gasPrice;
	
	@Value("${finePercent:15}")
	int finePercent;
	
	@PostConstruct
	public void set() {
		rentCompany.setGasPrice(gasPrice);
		rentCompany.setFinePercent(finePercent);
		System.out.println(rentCompany.getAllModelNames());
		
	}
	
	
	@ManagedOperation
	public void setGasPrice(int price) {
		rentCompany.setGasPrice(price);
	}
	
	@ManagedOperation
	public void setFinePercent(int percent) {
		rentCompany.setFinePercent(percent);
	}
	
	@ManagedAttribute
	public int getGasPrice() {
		return rentCompany.getGasPrice();
	}
	
	@ManagedAttribute
	public int getFinePercent() {
		return rentCompany.getFinePercent();
	}
	
	@PostMapping(value = ADD_DRIVER)
	CarsReturnCode addDriver(@RequestBody Driver driver) {
		return rentCompany.addDriver(driver);
	}
	
	@PostMapping(value = ADD_MODEL)
	CarsReturnCode addModel(@RequestBody Model model) {
		return rentCompany.addModel(model);
	}
	
	@PostMapping(value = ADD_CAR)
	CarsReturnCode addCar(@RequestBody Car car) {
		return rentCompany.addCar(car);
	}
	
	@GetMapping(value = GET_MODEL + "/{modelName}")
	Model getModel(@PathVariable("modelName") String modelName) {
		return rentCompany.getModel(modelName);
	}
	
	@GetMapping(value = GET_CAR + "/{carNumber}")
	Car getCar(@PathVariable("carNumber") String carNumber) {
		return rentCompany.getCar(carNumber);
	}
	
	@GetMapping(value = GET_DRIVER + "/{licenseId}")
	Driver getDriver(@PathVariable("licenseId") long licenseId) {
		return rentCompany.getDriver(licenseId);
	}
	
	@PostMapping(value = RENT_CAR)
	CarsReturnCode rentCar(@RequestBody DatesForRent data) {
		return rentCompany.rentCar(data.getCarNumber(), data.getDriverId(), data.getRentDate(), data.getDays());
	}
	
	@PostMapping(value = RETURN_CAR)
	CarsReturnCode returnCar(@RequestBody DatesForReturn returnCar) {
		return rentCompany.returnCar(returnCar.getCarNumber(), returnCar.getReturnDate(), returnCar.getGasTankPercent(),
				returnCar.getDamages());
	}
	
	@DeleteMapping(value = REMOVE_CAR + "/{carNumber}")
	CarsReturnCode returnCar(@PathVariable("carNumber") String carNumber) {
		return rentCompany.removeCar(carNumber);
	}
	
	@GetMapping(value = GET_ALL_DRIVERS)
	List<Driver> getAllDrivers(){
		return rentCompany.getAllDrivers().collect(Collectors.toList());
	}
	
	@GetMapping(value = GET_ALL_MODELS)
	List<String> getAllModels() {
		return rentCompany.getAllModelNames();
	}
	
	@GetMapping(value = GET_ALL_CARS)
	List<Car> getAllCars() {
		return rentCompany.getAllCars().collect(Collectors.toList());
	}
	
	@GetMapping(value = GET_CAR_DRIVERS + "/{carNumber}")
	List<Driver> getCarDrivers(@PathVariable("carNumber") String carNumber) {
		return rentCompany.getCarDrivers(carNumber);
	}
	
	@GetMapping(value = GET_DRIVER_CARS + "/{licenseId}")
	List<Car> getDriverCars(@PathVariable("licenseId") long licenseId) {
		return rentCompany.getDriverCars(licenseId);
	}
	
	@GetMapping(value = GET_ALL_RECORDS)
	List<RentRecord> getAllRecords() {
		return rentCompany.getAllRecords().collect(Collectors.toList());
	}
	
	@GetMapping(value = GET_MODEL_PROFIT + "/{modelName}")
	double getModelProfit(@PathVariable("modelName") String modelName) {
		return rentCompany.getModelProfit(modelName);
	}
	
	@GetMapping(value = GET_MOST_PROFITABLE_MODEL_NAMES)
	List<String> getMostProfitableModelNames() {
		return rentCompany.getMostProfitModelNames();
	}

	@GetMapping(value = GET_MOST_POPULAR_MODEL_NAMES)
	List<String> getMostPopularModelName() {
		return rentCompany.getMostPopularModelNames();
	}

	@PostMapping(value = CLEAR)
	void clear(@RequestBody ClearFactor clear) {
		rentCompany.clear(clear.getCurrentDate(), clear.getDays());
	}

	@GetMapping(value = GET_RETURNED_RECORDS_BY_DATES)
	List<RentRecord> getReturnRecordsByDates(@RequestBody DatesForReturnByDates days) {
		return rentCompany.getReturnedRecordsByDates(days.getFrom(), days.getTo()).collect(Collectors.toList());
	}
}
