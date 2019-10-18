package com.inti.formation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inti.formation.entities.User;
import com.inti.formation.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8034") //Autorise un serveur externe (entité externe) à communiquer avec notre application
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User addNewUser(@RequestBody User user) {
    	 System.out.println(user.getUserName());
    	return this.userService.addUser(user);
       
    }
    
    @PutMapping("/{id}")
    public User updateUser (@RequestBody User user, @PathVariable long id){
    	if(userService.getUserById(id)== null) {
    		return null;
    	}else {
    		return userService.addUser(user);
    	}
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
    	if(userService.getUserById(id)!= null) {
    		 userService.deleteUser(userService.getUserById(id));
    	}
    }
    
}