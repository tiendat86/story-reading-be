package com.storyreadingbe.service;

import com.storyreadingbe.dto.request.UserDetailDTO;
import com.storyreadingbe.dto.response.BookResponseDTO;
import com.storyreadingbe.entity.User;
import com.storyreadingbe.repository.BookRepository;
import com.storyreadingbe.repository.UserRepository;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ModelMapper modelMapper;
    
    public void register(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't register");
        }
    }
    
    public User login(String username, String password) {
        try {
            User user = userRepository.findById(username).get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Error username");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error password");
    }

    public void createUser(UserDetailDTO dto) {
        try {
            User user = modelMapper.map(dto, User.class);
            userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Error save");
        }
    }
}
