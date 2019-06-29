package com.floppylab.karancsfieszta.restcontroller;

import com.floppylab.karancsfieszta.domain.Animation;
import com.floppylab.karancsfieszta.helper.AnimationInput;
import com.floppylab.karancsfieszta.service.AnimationService;
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
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AnimationRestController {

    private final AnimationService animationService;

    /* ------------------------ GET ------------------------ */

    @GetMapping(path = "/animations")
    public Page<Animation> findAnimations(Pageable pageable) {
        return animationService.findAnimations(pageable);
    }

    @GetMapping(path = "/animations/{id}")
    public Animation findAnimation(@PathVariable("id") long id) {
        return animationService.findAnimation(id);
    }

    /* ------------------------ POST ------------------------ */

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/animations")
    public void saveAnimation(@RequestBody AnimationInput animationInput) {
        animationService.saveAnimation(animationInput);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(path = "/animations/{id}/hearts")
    public void heartAnimation(@PathVariable("id") long id) {
        animationService.heartAnimation(id);
    }

    /* ------------------------ PUT ------------------------ */

    @PreAuthorize("isAuthenticated()")
    @PutMapping(path = "/animations/{id}")
    public void modifyAnimation(@PathVariable("id") long id, @RequestBody AnimationInput animationInput) {
        animationService.modifyAnimation(id, animationInput);
    }

    /* ------------------------ DELETE ------------------------ */

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/animations/{id}")
    public void deleteAnimation(@PathVariable("id") long id) {
        animationService.deleteAnimation(id);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping(path = "/animations/{id}/hearts")
    public void unheartAnimation(@PathVariable("id") long id) {
        animationService.unheartAnimation(id);
    }

}
