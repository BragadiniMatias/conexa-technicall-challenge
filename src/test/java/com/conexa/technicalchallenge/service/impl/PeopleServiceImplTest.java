package com.conexa.technicalchallenge.service.impl;

import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.repository.PeopleRepository;
import com.conexa.technicalchallenge.service.IPeopleService;
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
public class PeopleServiceImplTest {
    private final PeopleRepository repository = mock(PeopleRepository.class);

    @InjectMocks
    private final IPeopleService sut = new PeopleServiceImpl();

    private final People domain = mock(People.class);
    private final List<People> domainList =  Collections.singletonList(domain);

    @Test
    void whenGetAll_thenCallServiceAndConverterAndReturnResultList(){
        when(repository.getAll(any(Pageable.class))).thenReturn(domainList);

        final List<People> result = sut.getAll(Pageable.unpaged());

        assertEquals(domainList, result);
        verify(repository, times(1)).getAll(any(Pageable.class));
    }

    @Test
    void givenId_whenGetBy_thenCallServiceAndConverterAndReturnResult(){
        when(repository.getById(VALID_ID)).thenReturn(domain);

        final People result = sut.getById(VALID_ID);

        assertEquals(domain, result);
        verify(repository, times(1)).getById(VALID_ID);
    }
}
