package com.rabobank.assignment.controller;

import com.rabobank.assignment.crosscutting.*;
import com.rabobank.assignment.domain.dto.*;
import com.rabobank.assignment.domain.service.*;
import com.rabobank.assignment.repository.entity.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.server.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> importFile(@RequestParam("userIssues") MultipartFile userIssues) {
        log.info("Importing CSV file");
        if (Util.hasCSVFormat(userIssues)) {
            try {
                userService.save(userIssues);
                return ResponseEntity.status(HttpStatus.OK).body(("Uploaded the file successfully: " + userIssues.getOriginalFilename()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Couldn't upload file");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a csv file");
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserDto getById(@RequestParam long id) {
        log.info("Getting user by Id");
        try {
            return userService.getById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllFiltered(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "issueCount") String sortBy) {
        log.info("Getting users ascending ordered by issueCount");
        return userService.getFilteredUsers(pageNo, pageSize, sortBy);
    }

}
