package car_rental;

public class Main {
	public static void main(String[] args) {

		CarRental rentalSystem = new CarRental();

		Car car1 = new Car("101", "Toyota", "Fortuner", 140.0);
		Car car2 = new Car("202", "Kia", "Sonet", 90.0);
		Car car3 = new Car("303", "Suzuki", "Swift", 40.0);
		rentalSystem.addCar(car1);
		rentalSystem.addCar(car2);
		rentalSystem.addCar(car3);

		rentalSystem.menu();
	}
}
