package ru.vtb.javaproexcercises.ex05.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.vtb.javaproexcercises.ex05.dto.UserDto;
import ru.vtb.javaproexcercises.ex05.domain.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    UserDto toDto(User user);
}
