package com.amsd.controller;

import com.amsd.model.User;
import com.amsd.service.JsonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private JsonDataService jsonDataService; // Service pour gérer les données JSON

    @PostMapping
    public User addUser(@RequestBody User user) throws Exception {
        jsonDataService.saveUser(user);  // Sauvegarde l'utilisateur dans le fichier JSON
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() throws Exception {
        return jsonDataService.getAllUsers();  // Récupère tous les utilisateurs depuis le fichier JSON
    }
}
