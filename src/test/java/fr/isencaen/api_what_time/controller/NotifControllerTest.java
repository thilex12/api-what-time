package fr.isencaen.api_what_time.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.isencaen.api_what_time.config.SpringSecurityConfig;
import fr.isencaen.api_what_time.controller.Dto.AccountDto;
import fr.isencaen.api_what_time.controller.Dto.NotifDto;
import fr.isencaen.api_what_time.service.Model.AccountModel;
import fr.isencaen.api_what_time.service.Model.NotifModel;
import fr.isencaen.api_what_time.service.NotifService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@WebMvcTest(NotifController.class)
@Import(SpringSecurityConfig.class)
public class NotifControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private NotifService notifService;

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    public void testGetAllNotifs() throws Exception{
        NotifModel notifModel1 = new NotifModel(1, 1, 1, false, LocalDateTime.now());
        Page<NotifModel> pageResult = new PageImpl<>(List.of(notifModel1));
        Mockito.when(notifService.getAllNotifs(any())).thenReturn(pageResult);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/notifs")
                        .contentType("application/json")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getContentAsString());

        // Vérifie le nombre d'éléments dans le content
        Assertions.assertEquals(1, root.get("content").size());
        Assertions.assertEquals(1, root.get("totalElements").asInt());

        JsonNode notifJson = root.get("content").get(0);
        Assertions.assertEquals(notifModel1.idNotif(), notifJson.get("idNotif").asInt());
        Assertions.assertEquals(notifModel1.idEvent(), notifJson.get("idEvent").asInt());
        // Comment traiter la date ? ...
        Assertions.assertFalse(notifJson.get("read").asBoolean());
    }

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    public void testGetNotif() throws Exception{
        NotifModel notif = new NotifModel(1, 1, 1, false, null);
        Mockito.when(notifService.getNotif(anyInt())).thenReturn(notif);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/notifs/1")
                        .contentType("application/json")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
        NotifModel result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});

        Assertions.assertEquals(NotifDto.of(notif), NotifDto.of(result));
    }

    @Test
    @WithMockUser(username = "John Doe", roles = {"USER"})
    public void testDeleteNotif() throws Exception {
        NotifModel notif = new NotifModel(1, 1, 1, false, null);

        Mockito.when(notifService.deleteNotif(anyInt())).thenReturn(notif);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.delete("/v1/notifs/1")
                        .contentType("application/json")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
        NotifModel result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>(){});

        Assertions.assertEquals(NotifDto.of(notif), NotifDto.of(result));

    }
}
