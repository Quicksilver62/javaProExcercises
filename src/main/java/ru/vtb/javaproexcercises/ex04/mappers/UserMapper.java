package ru.vtb.javaproexcercises.ex04.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.vtb.javaproexcercises.ex04.dao.UserDao;
import ru.vtb.javaproexcercises.ex04.domain.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDao userDao);

    UserDao toDao(User user);
}
