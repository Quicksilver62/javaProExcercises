package ru.vtb.java.pro.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.vtb.java.pro.productservice.dto.UserDto;
import ru.vtb.java.pro.productservice.services.UserService;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/")
    public Page<UserDto> findAllUsers(Pageable pageable) {
        return userService.findAllUsers(pageable);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(UserDto userDto) {
        userService.createUser(userDto);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(UserDto userDto) {
        userService.deleteUser(userDto);
    }
}
