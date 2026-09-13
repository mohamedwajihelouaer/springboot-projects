package com.jyx.library.mapper;

import com.jyx.library.domain.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.jyx.library.generated.model.AuthorDTO;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper( AuthorMapper.class );

    AuthorDTO authorToAuthorDTO(Author author);

}
