package ru.vtb.javaproexcercises.ex05.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.javaproexcercises.ex05.dto.UserDto;
import ru.vtb.javaproexcercises.ex05.exceptions.CustomException;
import ru.vtb.javaproexcercises.ex05.mappers.UserMapper;
import ru.vtb.javaproexcercises.ex05.domain.User;
import ru.vtb.javaproexcercises.ex05.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UserDto userDto) {
        User user = userRepository.findById(userDto.id())
                .orElseThrow(() -> new CustomException("User not found with id: " + userDto.id(), 404));
        userRepository.delete(user);
    }

    @Transactional
    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new CustomException("User not found with id: " + id, 404));
    }

    @Transactional
    public Page<UserDto> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }
}
