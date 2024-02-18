package fr.dorian.authorhub.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.model.Model;
import fr.dorian.authorhub.controller.dto.GetUserDto;
import fr.dorian.authorhub.controller.dto.PostUserDto;
import fr.dorian.authorhub.model.User;
import fr.dorian.authorhub.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

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
        //update user, can only be done by the user itself
        

        service.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
