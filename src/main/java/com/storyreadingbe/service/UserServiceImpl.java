package com.storyreadingbe.service;

import com.storyreadingbe.dto.request.UserDetailDTO;
import com.storyreadingbe.dto.response.UserResponseDTO;
import com.storyreadingbe.entity.User;
import com.storyreadingbe.repository.BookRepository;
import com.storyreadingbe.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

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
    AmazonS3ServiceImpl amazonS3Service;
    @Autowired
    ModelMapper modelMapper;

    public void register(User user) {
        Optional optional = userRepository.findById(user.getUsername());
        if (optional.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Can't register");
        }
    }

    public UserResponseDTO login(String username, String password) {
        try {
            User user = userRepository.findById(username).get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
                return dto;
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

    public UserResponseDTO changeAvatar(String username, MultipartFile multipartFile) {
        String urlImg = amazonS3Service.uploadFileUrl(multipartFile);
        User user = userRepository.findById(username).get();
        if (urlImg.equals("")) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Something went wrong");
        }
        user.setUrlImg(urlImg);
        userRepository.save(user);
        UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
        return dto;
    }
}
