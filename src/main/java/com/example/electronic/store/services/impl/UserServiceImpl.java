package com.example.electronic.store.services.impl;

import com.example.electronic.store.dtos.UserDto;
import com.example.electronic.store.entities.Role;
import com.example.electronic.store.entities.User;
import com.example.electronic.store.exceptions.ResourceNotFoundException;
import com.example.electronic.store.repositories.RoleRepository;
import com.example.electronic.store.repositories.UserRepository;
import com.example.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${user.profile.image.path}")
    private String imagePath;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto createUser(UserDto dto) {
        String uid = UUID.randomUUID().toString();
        dto.setUserid(uid);
        User user = dtoToentity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = new Role();
        role.setRoleId(UUID.randomUUID().toString());
        role.setName("ROLE_NORMAL");

        Role roleNormal = roleRepository.findByName("ROLE_NORMAL").orElse(role);

        user.setRoles(List.of(roleNormal));
        User savedUser =   userRepository.save(user);
        UserDto savedDtoUser = entityToDto(savedUser);
        return  savedDtoUser;
    }



    @Override
    public UserDto updateUser(UserDto userdto, String userId) {
//        User user = dtoToentity(userdto);
//        user.setUserId(userId);

        User updatedUser = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));
        updatedUser.setName(userdto.getName());
        updatedUser.setAbout(userdto.getAbout());
        updatedUser.setGender(userdto.getGender());
        updatedUser.setEmail(userdto.getEmail());
        updatedUser.setImageName(userdto.getImageName());
        updatedUser.setPassword(userdto.getPassword());
        updatedUser.setUserId(userId);

        User newUser = userRepository.save(updatedUser);

        return entityToDto(newUser);

    }

    @Override
    public void deleteUser(String userID) {
        User updatedUser = userRepository.findById(userID).orElseThrow(()-> new ResourceNotFoundException("User does not exist"));

        String fullpath = imagePath + updatedUser.getImageName();
        try
        {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        }
        catch(NoSuchFileException e)
        {
            logger.info("User image not found in folder");
            e.printStackTrace();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }

        userRepository.delete(updatedUser);
    }

    @Override
    public List<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Sort sort = (sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortOrder).descending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = userRepository.findAll(pageable);

        List<User> users = page.getContent();

        List<UserDto> userDtos = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User ID does not exits"));
        UserDto dto = entityToDto(user);
        return dto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User does not exits with these email"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> getUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> userDtos = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .name(savedUser.getName())
//                .password(savedUser.getPassword())
//                .email(savedUser.getEmail())
//                .about(savedUser.getAbout())
//                .userid(savedUser.getUserId())
//                .imageName(savedUser.getImageName())
//                .gender(savedUser.getGender())
//                .build();
        return  mapper.map(savedUser, UserDto.class);
    }

    private User dtoToentity(UserDto dto) {
//        User user1 = User.builder()
//                .userId(dto.getUserid())
//                .name(dto.getName())
//                .email(dto.getEmail())
//                .password(dto.getPassword())
//                .about(dto.getAbout())
//                .gender(dto.getAbout())
//                .imageName(dto.getImageName())
//                .build();
        return mapper.map(dto, User.class);
    }
}
