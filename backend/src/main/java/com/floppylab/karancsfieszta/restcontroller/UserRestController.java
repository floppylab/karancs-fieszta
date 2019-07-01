package com.floppylab.karancsfieszta.restcontroller;

import com.floppylab.karancsfieszta.domain.Animation;
import com.floppylab.karancsfieszta.helper.AnimationInput;
import com.floppylab.karancsfieszta.helper.UserInput;
import com.floppylab.karancsfieszta.service.AnimationService;
import com.floppylab.karancsfieszta.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRestController {

    private final ApplicationUserService applicationUserService;

    /* ------------------------ GET ------------------------ */

    @GetMapping(path = "/users")
    public boolean existsUser(@RequestParam String username) {
        return applicationUserService.existsUser(username);
    }

    /* ------------------------ POST ------------------------ */

    @PostMapping(path = "/users")
    public void registerUser(@RequestBody UserInput userInput) {
        applicationUserService.saveUser(userInput);
    }

}
