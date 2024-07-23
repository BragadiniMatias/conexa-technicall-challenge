package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.Starship;
import com.conexa.technicalchallenge.repository.StarshipRepository;
import com.conexa.technicalchallenge.service.IStarshipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static com.conexa.technicalchallenge.utils.constants.TestsConstants.VALID_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StarshipServiceImplTest {
    private final StarshipRepository repository = mock(StarshipRepository.class);

    @InjectMocks
    private final IStarshipService sut = new StarshipServiceImpl();

    private final Starship domain = mock(Starship.class);
    private final List<Starship> domainList =  Collections.singletonList(domain);

    @Test
    void whenGetAll_thenCallServiceAndConverterAndReturnResultList(){
        when(repository.getAll(any(Pageable.class))).thenReturn(domainList);

        final List<Starship> result = sut.getAll(Pageable.unpaged());

        assertEquals(domainList, result);
        verify(repository, times(1)).getAll(any(Pageable.class));
    }

    @Test
    void givenId_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(repository.getById(VALID_ID)).thenReturn(domain);

        final Starship result = sut.getById(VALID_ID);

        assertEquals(domain, result);
        verify(repository, times(1)).getById(VALID_ID);
    }
}
