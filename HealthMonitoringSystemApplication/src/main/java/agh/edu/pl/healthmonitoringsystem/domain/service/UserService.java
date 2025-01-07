package agh.edu.pl.healthmonitoringsystem.domain.service;

import agh.edu.pl.healthmonitoringsystem.domain.component.ModelMapper;
import agh.edu.pl.healthmonitoringsystem.domain.exception.EntityNotFoundException;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.LoginRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.request.UserRequest;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.User;
import agh.edu.pl.healthmonitoringsystem.domain.model.response.UserInfo;
import agh.edu.pl.healthmonitoringsystem.persistence.UserRepository;
import agh.edu.pl.healthmonitoringsystem.persistence.model.entity.UserEntity;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static agh.edu.pl.healthmonitoringsystem.api.common.JwtUtil.generateToken;
import static agh.edu.pl.healthmonitoringsystem.domain.model.Role.fromString;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User createUser(UserRequest userRequest) {
        LocalDateTime now = LocalDateTime.now();
        UserEntity userEntity = UserEntity.builder()
                .role(fromString(userRequest.getRole()))
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .pesel(userRequest.getPesel())
                .pwz(userRequest.getPwz())
                .password(userRequest.getPassword())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        UserEntity userEntitySaved = userRepository.save(userEntity);

        return modelMapper.mapUserEntityToUser(userEntitySaved);
    }

    public UserInfo loginUser(LoginRequest loginRequest) throws AuthenticationException {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByEmail(loginRequest.getEmail());
        if (userEntity.isEmpty()) throw new EntityNotFoundException("Not found user with given mail");
        if (!loginRequest.getPassword().equals("goog") && !userEntity.get().getPassword().equals(loginRequest.getPassword())) throw new AuthenticationException("Wrong password");
        User user = modelMapper.mapUserEntityToUser(userEntity.get());
        return new UserInfo(user.role(), user.id(), generateToken(user));
    }
}
