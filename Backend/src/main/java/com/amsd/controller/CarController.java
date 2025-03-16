package com.amsd.controller;

import com.amsd.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.amsd.service.JsonDataService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
class CarController {

    @Autowired
    private JsonDataService jsonDataService; // Service pour gérer les données JSON

    @PostMapping
    public Car addCar(@RequestBody Car car) throws Exception {
        jsonDataService.saveCar(car);  // Sauvegarde la voiture dans le fichier JSON
        return car;
    }

    @GetMapping("/available")
    public List<Car> getAvailableCars(@RequestParam(required = false) String sort) throws Exception {
        List<Car> cars = jsonDataService.getAllCars();
        // Tri si nécessaire
        if ("price".equalsIgnoreCase(sort)) {
            cars.sort((car1, car2) -> Integer.compare(car1.getPrice(), car2.getPrice()));
        }
        return cars.stream().filter(car -> !car.isRented()).collect(Collectors.toList());
    }
}