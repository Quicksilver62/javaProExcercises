package ru.vtb.javaproexcercises.ex05.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.vtb.javaproexcercises.ex05.domain.Product;
import ru.vtb.javaproexcercises.ex05.dto.ProductDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);
}
