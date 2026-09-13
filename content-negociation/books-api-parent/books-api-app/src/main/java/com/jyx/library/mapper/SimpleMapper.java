package com.jyx.library.mapper;

import com.jyx.library.domain.Author;
import com.jyx.library.generated.model.AuthorDTO;

public class SimpleMapper {

    public static AuthorDTO authorToAuthorDTO(Author author) {

        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setFirstName(author.getFirstName());
        authorDTO.setLastName(author.getLastName());
        authorDTO.setEmail(author.getEmail());
        authorDTO.setAddress(author.getAddress());
        authorDTO.setPhone(author.getPhone());
        authorDTO.setBirthDate(author.getBirthDate().toString());
        return authorDTO;
    }
}
