package ru.vtb.javaproexcercises.ex05.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.vtb.javaproexcercises.ex05.dto.UserDto;
import ru.vtb.javaproexcercises.ex05.domain.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductMapper.class})
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", source = "products")
    User toEntity(UserDto userDto);

    @Mapping(target = "products", source = "products")
    UserDto toDto(User user);
}
