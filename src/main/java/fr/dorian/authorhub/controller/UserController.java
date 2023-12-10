package fr.dorian.authorhub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.dorian.authorhub.controller.dto.GetUserDto;
import fr.dorian.authorhub.controller.dto.PostUserDto;
import fr.dorian.authorhub.model.User;
import fr.dorian.authorhub.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService service;

    @GetMapping
    public List<GetUserDto> findAll() {
        List<User> users = service.findAll();
        List<GetUserDto> usersDto = users.stream().map(user -> new GetUserDto(user.getUsername(), user.getMail())).toList();
        return usersDto;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    @GetMapping("/name/{username}")
    public User getUserByName(@PathVariable String username) {
        return service.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public void save(@Valid @RequestBody PostUserDto userDto) {
        User user = new User();
        user.setUsername(userDto.username());
        user.setMail(userDto.mail());
        user.setPassword(userDto.password());
        service.save(user);
    }

    @PutMapping
    public void update(@PathVariable Long id, @RequestBody PostUserDto userDto) {
        User user = new User();
        //TODO: update user, make sure to check if current user is the same as the one in the request
        service.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
