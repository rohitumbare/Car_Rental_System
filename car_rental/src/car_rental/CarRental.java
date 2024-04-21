package car_rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarRental {

	public static final String RED = "\u001B[31m";
	public static final String RESET = "\u001B[0m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String Magenta = "\u001B[35m";
	public static final String Cyan = "\u001B[36m";

	private List<Car> cars;
	private List<Customer> customers;
	private List<Rental> rentals;

	public CarRental() {
		cars = new ArrayList<>();
		customers = new ArrayList<>();
		rentals = new ArrayList<>();
	}

	public void addCar(Car car) {
		cars.add(car);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void rentCar(Car car, Customer customer, int days) {
		if (car.isAvailable()) {
			car.rent();
			rentals.add(new Rental(car, customer, days));

		} else {
			System.out.println(RED + "Car is not available for rent." + RESET);
		}
	}

	public void returnCar(Car car) {
		car.returnCar();
		Rental rentalToRemove = null;
		for (Rental rental : rentals) {
			if (rental.getCar() == car) {
				rentalToRemove = rental;
				break;
			}
		}
		if (rentalToRemove != null) {
			rentals.remove(rentalToRemove);

		} else {
			System.out.println(RED + "Car was not rented." + RESET);
		}
	}

	public void menu() {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println(Magenta + "*** Car Rental System ***" + RESET);
			System.out.println(Cyan + "1. Rent a Car");
			System.out.println("2. Return a Car");
			System.out.println("3. Exit" + RESET);
			System.out.print("Enter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			if (choice == 1) {
				System.out.println(Cyan + "\n* Rent a Car *\n" + RESET);
				System.out.print("Enter your name: ");
				String customerName = scanner.nextLine();

				System.out.println(GREEN + "\nAvailable Cars:" + RESET);
				for (Car car : cars) {
					if (car.isAvailable()) {
						System.out.println(
								YELLOW + car.getCarId() + " - " + car.getBrand() + " " + car.getModel() + RESET);
					}
				}

				System.out.print("\nEnter the car ID you want to rent: ");
				String carId = scanner.nextLine();

				System.out.print("Enter the number of days for rental: ");
				int rentalDays = scanner.nextInt();
				scanner.nextLine();

				Customer newCustomer = new Customer("C" + (customers.size() + 1), customerName);
				addCustomer(newCustomer);

				Car selectedCar = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && car.isAvailable()) {
						selectedCar = car;
						break;
					}
				}

				if (selectedCar != null) {
					double totalPrice = selectedCar.calculatePrice(rentalDays);
					System.out.println(Cyan + "\n== Rental Information ==\n" + RESET);
					System.out.println(Cyan + "Customer ID: " + YELLOW + newCustomer.getCustomerId() + RESET);
					System.out.println(Cyan + "Customer Name: " + YELLOW + newCustomer.getName() + RESET);
					System.out.println(
							Cyan + "Car: " + YELLOW + selectedCar.getBrand() + " " + selectedCar.getModel() + RESET);
					System.out.println(Cyan + "Rental Days: " + YELLOW + rentalDays + RESET);
					System.out.println(Cyan + "Total Price: " + YELLOW + totalPrice + " $" + RESET);

					System.out.print("\nConfirm rental (Y/N): ");
					String confirm = scanner.nextLine();

					if (confirm.equalsIgnoreCase("Y")) {
						rentCar(selectedCar, newCustomer, rentalDays);
						System.out.println(GREEN + "\nCar rented successfully." + RESET);
					} else {
						System.out.println(RED + "\nRental canceled." + RESET);
					}
				} else {
					System.out.println(RED + "\nInvalid car selection or car not available for rent." + RESET);
				}
			} else if (choice == 2) {
				System.out.println(Cyan + "\n* Return a Car *\n" + RESET);
				System.out.print("Enter the car ID you want to return: ");
				String carId = scanner.nextLine();

				Car carToReturn = null;
				for (Car car : cars) {
					if (car.getCarId().equals(carId) && !car.isAvailable()) {
						carToReturn = car;
						break;
					}
				}

				if (carToReturn != null) {
					Customer customer = null;
					for (Rental rental : rentals) {
						if (rental.getCar() == carToReturn) {
							customer = rental.getCustomer();
							break;
						}
					}

					if (customer != null) {
						returnCar(carToReturn);
						System.out
								.println(GREEN + "Car returned successfully by " + YELLOW + customer.getName() + RESET);
					} else {
						System.out.println(RED + "Car was not rented or rental information is missing." + RESET);
					}
				} else {
					System.out.println(RED + "Invalid car ID or car is not rented." + RESET);
				}
			} else if (choice == 3) {
				break;
			} else {
				System.out.println(RED + "Invalid choice. Please enter a valid option." + RESET);
			}
		}

		System.out.println(Magenta + "\nThank you for using the Car Rental System!" + RESET);
	}
}
