package com.jyx.library.service;

import com.jyx.library.domain.Author;
import com.jyx.library.generated.model.AuthorDTO;
import com.jyx.library.mapper.AuthorMapper;
import com.jyx.library.mapper.SimpleMapper;
import com.jyx.library.repository.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    }

    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(SimpleMapper::authorToAuthorDTO).toList();
    }


}
