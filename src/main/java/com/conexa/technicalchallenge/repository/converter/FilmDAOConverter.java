package com.conexa.technicalchallenge.repository.converter;

import com.conexa.technicalchallenge.domain.Film;
import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.repository.dao.film.FilmProperties;
import com.conexa.technicalchallenge.repository.dao.film.FilmResult;
import com.conexa.technicalchallenge.repository.dao.people.PeopleListItem;
import com.conexa.technicalchallenge.repository.dao.people.PersonProperties;
import com.conexa.technicalchallenge.utils.constants.FormatConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper
public interface FilmDAOConverter {

    @Named("stringToDate")
    default Date stringToDate(String dateTime) {
        if (dateTime != null && !dateTime.isEmpty()) {
            try {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat(FormatConstants.dateTimeFormat);
                SimpleDateFormat dateFormat = new SimpleDateFormat(FormatConstants.dateFormat);
                Date date = dateTimeFormat.parse(dateTime);
                return dateFormat.parse(dateFormat.format(date));
            } catch (ParseException e) {
                throw new RuntimeException("Error parsing date", e);
            }
        }
        return null;
    }
    @Mapping(source = "releaseDate", target = "releaseDate", dateFormat = FormatConstants.dateFormat)
    @Mapping(source = "created", target = "created", qualifiedByName = "stringToDate")
    @Mapping(source = "edited", target = "edited", qualifiedByName = "stringToDate")
    Film propertiesDaoToDomain(FilmProperties properties);

    List<Film> filmPropertiesToDomainList(List<FilmProperties> properties);


}
