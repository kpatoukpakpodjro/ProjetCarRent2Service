package com.amsd.frontend;

import com.amsd.frontend.model.Car;
import com.amsd.frontend.model.Rent;
import com.amsd.frontend.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MyWebService {

    // Simuler des données (remplacez par votre logique de service réelle)
    private List<Car> cars = new ArrayList<>();
    private List<Rent> rents = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private User currentUser = null;

    // Initialisation des données (à remplacer par une base de données)
    public MyWebService() {
        // Initialisation des utilisateurs
        users.add(new User("john_doe", "john.doe@example.com", "USER"));
        users.add(new User("admin", "admin@example.com", "ADMIN"));
        users.add(new User("kkpodjro", "alice@example.com", "USER"));
        users.add(new User("wpaty", "bob@example.com", "USER"));

        // Initialisation des voitures
        cars.add(new Car("ABC123", 50, false));
        cars.add(new Car("XYZ456", 60, false));
        cars.add(new Car("ABC1234", 50, false));
        cars.add(new Car("XYZ5678", 60, false));
        cars.add(new Car("DEF2468", 70, false));
        cars.add(new Car("GHI1357", 55, false));
        cars.add(new Car("JKL456", 60, false));
        cars.add(new Car("MNO789", 80, false));
        cars.add(new Car("PQR123", 100, false));
        cars.add(new Car("STU987", 75, false));
        cars.add(new Car("VWX654", 90, false));
        cars.add(new Car("YZA321", 110, false));
        cars.add(new Car("BCD852", 95, false));
        cars.add(new Car("EFG741", 70, false));
        cars.add(new Car("HIJ258", 85, false));
        cars.add(new Car("KLM369", 95, false));
        cars.add(new Car("NOP852", 65, false));
        cars.add(new Car("QRS987", 120, false));
        cars.add(new Car("TUV654", 130, false));
        cars.add(new Car("WXY321", 140, false));
        cars.add(new Car("ZAB159", 100, false));
        cars.add(new Car("CDE357", 110, false));
    }
    @PostMapping("/login")
    @ResponseBody
    public User loginOrRegister(@RequestBody User user) {
        User existingUser = users.stream()
                .filter(u -> u.getUsername().equals(user.getUsername()) && u.getEmail().equals(user.getEmail()))
                .findFirst()
                .orElse(null);

        if (existingUser != null) {
            currentUser = existingUser;
            return currentUser;
        } else {
            user.setRole("USER");
            users.add(user);
            currentUser = user;
            return currentUser;
        }
    }
    @GetMapping("/cars/available")
    public String getAvailableCars(Model model, @RequestParam(required = false) String sort) {
        List<Car> availableCars = cars.stream().filter(car -> !car.isRented()).collect(Collectors.toList());
        if (sort != null && sort.equalsIgnoreCase("price")) {
            availableCars.sort((c1, c2) -> Double.compare(c1.getPrice(), c2.getPrice()));
        }
        model.addAttribute("cars", availableCars);
        return "availableCars"; // Nom du fichier HTML
    }

    @PostMapping("/rents/rent")
    public String rentCar(@RequestParam String carId) {
        Long carId2 = Long.parseLong(carId);
        Car car = cars.stream().filter(c -> c.getId().equals(carId2)).findFirst().orElse(null);
        User user = currentUser; //users.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(null);

        if (car != null && user != null) {
            car.setRented(true);
            Rent rent = new Rent();
            rent.setId((long) (rents.size() + 1));
            rent.setCar(car);
            rent.setUser(user);
            rents.add(rent);

            //return "redirect:/myrents?userId=" + user.getId();
            String redirectUrl = UriComponentsBuilder.fromPath("/myrents")
                    .queryParam("userId", user.getId())
                    .toUriString();
            return "redirect:" + redirectUrl;
        }
        if (user != null) {
            System.out.println(user.getUsername());
            System.out.println(carId);
            return "redirect:/cars/available";
    }
        return "redirect:/";
    }

    @GetMapping("/myrents")
    public String getMyRents(Model model, @RequestParam String userId, @RequestParam(required = false) Boolean returned) {
        Long userId2 = Long.parseLong(userId);

        List<Rent> userRents = rents.stream().filter(r -> r.getUser().getId().equals(userId2)).collect(Collectors.toList());
        if (returned != null) {
            userRents = userRents.stream().filter(r -> r.getCar().isRented() == returned).collect(Collectors.toList());
        }
        model.addAttribute("rents", userRents);
        return "usersRents"; // Nom du fichier HTML
    }

    @PostMapping("/rents/return")
    public String returnCar(@RequestBody Rent rent) {
        System.out.println("Rent ID reçu : " + rent.getId()); // Ajout d'un log
        System.out.println("User ID reçu : " + rent.getUser().getId()); // Ajout d'un log

        Rent existingRent = rents.stream().filter(r -> r.getId().equals(rent.getId())).findFirst().orElse(null);
        if (existingRent != null) {
            existingRent.getCar().setRented(false);
            Car car = cars.stream().filter(c -> c.getId().equals(existingRent.getCar().getId())).findFirst().orElse(null);
            if (car != null) {
                car.setRented(false);
            }
            return "redirect:/myrents?userId=" + rent.getUser().getId();
        }
        return "redirect:/myrents?userId=" + rent.getUser().getId();
    }

    public String index(Model model) {
        model.addAttribute("currentUserId", currentUser != null ? currentUser.getId() : null);
        model.addAttribute("currentUser", currentUser);
        return "index";
    }
}