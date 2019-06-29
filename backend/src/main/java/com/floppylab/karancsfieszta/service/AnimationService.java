package com.floppylab.karancsfieszta.service;

import com.floppylab.karancsfieszta.domain.Animation;
import com.floppylab.karancsfieszta.exception.AccessDeniedException;
import com.floppylab.karancsfieszta.exception.AnimationNotFoundException;
import com.floppylab.karancsfieszta.helper.AnimationInput;
import com.floppylab.karancsfieszta.repository.AnimationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimationService {

    private final AnimationRepository animationRepository;

    private final LoggedInUserService loggedInUserService;

    private final ModelMapper modelMapper;

    public Page<Animation> findAnimations(Pageable pageable) {
        return animationRepository.findAll(pageable);
    }

    public Animation findAnimation(long id) {
        return animationRepository.findById(id).orElse(null);
    }

    public void saveAnimation(AnimationInput animationInput) {
        Animation toSave = modelMapper.map(animationInput, Animation.class);
        animationRepository.save(toSave);
    }

    public void modifyAnimation(Long id, AnimationInput animationInput) {
        Optional<Animation> oldAnimation = animationRepository.findById(id);
        if (oldAnimation.isPresent()) {
            if (oldAnimation.get().getCreatedBy().equals(loggedInUserService.getLoggedInUserName())) {
                Animation toModify = oldAnimation.get();
                toModify.setCode(animationInput.getCode());
                toModify.setTitle(animationInput.getTitle());
                animationRepository.save(toModify);
            } else {
                throw new AccessDeniedException();
            }
        } else {
            throw new AnimationNotFoundException();
        }
    }

    public void heartAnimation(Long id) {
        Optional<Animation> oldAnimation = animationRepository.findById(id);
        if (oldAnimation.isPresent()) {
            Animation toHeart = oldAnimation.get();
            String loggedInUsername = loggedInUserService.getLoggedInUserName();
            toHeart.getHearts().add(loggedInUsername);
            animationRepository.save(toHeart);
        } else {
            throw new AnimationNotFoundException();
        }
    }

    public void unheartAnimation(Long id) {
        Optional<Animation> oldAnimation = animationRepository.findById(id);
        if (oldAnimation.isPresent()) {
            Animation toHeart = oldAnimation.get();
            String loggedInUsername = loggedInUserService.getLoggedInUserName();
            toHeart.getHearts().remove(loggedInUsername);
            animationRepository.save(toHeart);
        } else {
            throw new AnimationNotFoundException();
        }
    }

    public void deleteAnimation(Long id) {
        Optional<Animation> animation = animationRepository.findById(id);
        if (animation.isPresent()) {
            if (animation.get().getCreatedBy().equals(loggedInUserService.getLoggedInUserName())) {
                animationRepository.deleteById(id);
            } else {
                throw new AccessDeniedException();
            }
        } else {
            throw new AnimationNotFoundException();
        }
    }

}
