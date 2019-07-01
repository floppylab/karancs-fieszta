package com.floppylab.karancsfieszta.service;

import com.floppylab.karancsfieszta.domain.Animation;
import com.floppylab.karancsfieszta.domain.ApplicationUser;
import com.floppylab.karancsfieszta.exception.AccessDeniedException;
import com.floppylab.karancsfieszta.exception.AnimationNotFoundException;
import com.floppylab.karancsfieszta.helper.AnimationInput;
import com.floppylab.karancsfieszta.helper.UserInput;
import com.floppylab.karancsfieszta.repository.AnimationRepository;
import com.floppylab.karancsfieszta.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {

    private final ApplicationUserRepository userRepository;

    private final ModelMapper modelMapper;

    public void saveUser(UserInput userInput) {
        ApplicationUser toSave = modelMapper.map(userInput, ApplicationUser.class);
        toSave.setPassword(new BCryptPasswordEncoder().encode(toSave.getPassword()));
        userRepository.save(toSave);
    }

    public boolean existsUser(String username) {
        return userRepository.existsByUsername(username);
    }

}
