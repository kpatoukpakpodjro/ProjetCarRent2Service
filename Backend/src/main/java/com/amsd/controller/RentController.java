package com.amsd.controller;

import com.amsd.model.Car;
import com.amsd.model.Rent;
import com.amsd.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import com.amsd.service.JsonDataService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rents")
class RentController {

    @Autowired
    private JsonDataService jsonDataService; // Service pour gérer les données JSON

    @PostMapping("/rent")
    public Rent rentCar(@RequestParam Long carId, @RequestParam Long userId) throws Exception {
        List<Car> cars = jsonDataService.getAllCars();
        List<User> users = jsonDataService.getAllUsers();
        
        // Trouver la voiture et l'utilisateur par ID
        Optional<Car> carOpt = cars.stream().filter(car -> car.getId().equals(carId)).findFirst();
        Optional<User> userOpt = users.stream().filter(user -> user.getId().equals(userId)).findFirst();

        if (carOpt.isPresent() && userOpt.isPresent() && !carOpt.get().isRented()) {
            Car car = carOpt.get();
            User user = userOpt.get();
            car.setRented(true);
            jsonDataService.saveCar(car);  // Sauvegarder la voiture après modification

            LocalDate beginDate = LocalDate.now();
            Rent rent = new Rent(car, user, beginDate, null);
            jsonDataService.saveRent(rent);  // Sauvegarder la location dans le fichier JSON
            return rent;
        }

        throw new RuntimeException("Car not available for rent");
    }

    @PostMapping("/return")
    public void returnCar(@RequestParam Long carId) throws Exception {
        List<Car> cars = jsonDataService.getAllCars();
        List<Rent> rents = jsonDataService.getAllRents();

        Optional<Car> carOpt = cars.stream().filter(car -> car.getId().equals(carId)).findFirst();
        if (carOpt.isPresent() && carOpt.get().isRented()) {
            Car car = carOpt.get();
            car.setRented(false);
            jsonDataService.saveCar(car);  // Sauvegarder la voiture après modification

            Optional<Rent> rentOpt = rents.stream().filter(rent -> rent.getCar().getId().equals(carId) && rent.getEndDate() == null).findFirst();
            if (rentOpt.isPresent()) {
                Rent rent = rentOpt.get();
                rent.setEndDate(LocalDate.now());
                rent.setCar(car);
                jsonDataService.saveRent(rent);  // Sauvegarder la location après modification
            }
        } else {
            throw new RuntimeException("Car is not rented");
        }
    }

    @GetMapping("/myrents")
    public List<Rent> getMyRentedCars(@RequestParam Long userId, @RequestParam(required = false) Boolean returned) {
        try {
            Optional<User> userOpt = jsonDataService.getUserById(userId);
            if (userOpt.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            List<Rent> userRents = jsonDataService.getAllRents()
                    .stream()
                    .filter(rent -> rent.getUser().getId().equals(userId))
                    .collect(Collectors.toList());

            if (returned != null) {
                userRents = userRents.stream()
                        .filter(rent -> (returned ? rent.getEndDate() != null : rent.getEndDate() == null))
                        .collect(Collectors.toList());
            }

            return userRents;
        } catch (IOException e) {
            throw new RuntimeException("Error reading rent data", e);
        }
    }


    @GetMapping
    public List<Rent> getAllRents() throws Exception {
        return jsonDataService.getAllRents();
    }
}