package com.kidzoo.toydetails.controller;

import com.kidzoo.toydetails.entity.UserEntity;
import com.kidzoo.toydetails.dao.GeneralResponse;
import com.kidzoo.toydetails.exception.UserAlreadyExistException;
import com.kidzoo.toydetails.exception.UserNotFoundException;
import com.kidzoo.toydetails.service.UserServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;

@Api(tags = "user")
@RestController
@RequestMapping(value = "/v1")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(value = "/user/add", produces = "application/hal+json")
    public GeneralResponse add(@RequestBody UserEntity userEntity) throws UserAlreadyExistException {
        return userService.addUser(userEntity);
    }

    @DeleteMapping(value = "/user/delete/{id}", produces = "application/hal+json")
    public GeneralResponse delete(@PathVariable int id) throws UserNotFoundException {
        return userService.delete(id);
    }
}
