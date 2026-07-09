package ru.vtb.java.pro.productservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.vtb.java.pro.productservice.domain.Product;
import ru.vtb.java.pro.productservice.dto.ProductDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);
}
