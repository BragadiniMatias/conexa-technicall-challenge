package com.conexa.technicalchallenge.repository.converter;

import com.conexa.technicalchallenge.domain.People;
import com.conexa.technicalchallenge.repository.dao.people.PeopleListItem;
import com.conexa.technicalchallenge.repository.dao.people.PersonProperties;
import com.conexa.technicalchallenge.repository.dao.people.PersonResult;
import com.conexa.technicalchallenge.utils.constants.FormatConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Mapper
public interface PeopleDAOConverter {
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

    @Mapping(source = "created", target = "created", qualifiedByName = "stringToDate")
    @Mapping(source = "edited", target = "edited", qualifiedByName = "stringToDate")
    List<People> peopleListItemToPeople(List<PeopleListItem> daoList);

    @Mapping(source = "created", target = "created", qualifiedByName = "stringToDate")
    @Mapping(source = "edited", target = "edited", qualifiedByName = "stringToDate")
    People personPropertiesToPeople(PersonProperties properties);

}
