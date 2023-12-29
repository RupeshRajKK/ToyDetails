package com.kidzoo.toydetails.service;

import com.kidzoo.toydetails.client.entity.UserEntity;
import com.kidzoo.toydetails.dao.GeneralResponse;
import com.kidzoo.toydetails.exception.UserAlreadyExistException;
import com.kidzoo.toydetails.exception.UserNotFoundException;
import com.kidzoo.toydetails.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    @Autowired
    UserRepository userRepository;

    public GeneralResponse addUser(UserEntity userEntity) throws UserAlreadyExistException {
            UserEntity user = userRepository.findByEmail(userEntity.getEmail()).orElse(null);
            if (user != null) {
//                return new GeneralResponse(false, " email already exists");
                throw new UserAlreadyExistException();
            }
            userRepository.save(userEntity);
            return new GeneralResponse(true, "user successfully created ");
    }

    public GeneralResponse delete(int id) throws UserNotFoundException {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
        return new GeneralResponse(true,"user deleted successfully");
    }
}
