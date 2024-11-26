package com.example.electronic.store.controllers;

import com.example.electronic.store.dtos.ApiResponseMessage;
import com.example.electronic.store.dtos.ImageResponse;
import com.example.electronic.store.dtos.UserDto;
import com.example.electronic.store.services.FileService;
import com.example.electronic.store.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    // create user
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") String userId)
    {
        UserDto userDto1 = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userID}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userID)
    {
        userService.deleteUser(userID);
        ApiResponseMessage message= ApiResponseMessage.builder().message("User is deleted successfully!!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //getall
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir)
    {
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{emailID}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String emailID)
    {
        return new ResponseEntity<>(userService.getUserByEmail(emailID), HttpStatus.OK);
    }

    //get by id
    @GetMapping("/{userID}")
    public ResponseEntity<UserDto> getUserByID(@PathVariable String userID)
    {
        return new ResponseEntity<>(userService.getUserById(userID), HttpStatus.OK);
    }

    //search user
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> getUserbykeyword(@PathVariable String keyword)
    {
        return new ResponseEntity<>(userService.getUser(keyword), HttpStatus.OK);
    }

    // upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@PathVariable("userId") String userId, @RequestParam("userImage")MultipartFile image) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto userDto = userService.updateUser(user, userId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    // server user image
    @GetMapping("/image/{userId}")
    public void serverUserImage(@PathVariable("userId") String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);

        logger.info("User image name : {} ", user.getImageName());

        InputStream resouce = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resouce, response.getOutputStream());
    }

}
