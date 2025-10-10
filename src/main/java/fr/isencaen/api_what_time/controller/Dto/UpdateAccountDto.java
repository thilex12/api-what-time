package fr.isencaen.api_what_time.controller.Dto;

import java.util.List;

public record UpdateAccountDto(
        String name,
        String surname,
        String mail,
        String pwd,
        List<Integer> tags
        ){
}
