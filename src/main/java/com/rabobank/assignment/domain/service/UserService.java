package com.rabobank.assignment.domain.service;

import com.rabobank.assignment.crosscutting.*;
import com.rabobank.assignment.domain.dto.*;
import com.rabobank.assignment.repository.entity.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.time.*;
import java.util.*;

import static com.rabobank.assignment.crosscutting.Util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(MultipartFile file) {
        try {
            var users = csvToUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store csv data: " + e.getMessage());
        }
    }

    public UserDto getById(long id) throws Exception {
        var byId = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
        var age = Util.calculateAge(LocalDate.parse(byId.getDateOfBirth()), LocalDate.now());
        return new UserDto(byId, age);
    }

    public List<User> getFilteredUsers(int pageNo, int pageSize, String sortBy) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        var pagedResult = userRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }

    }

}
