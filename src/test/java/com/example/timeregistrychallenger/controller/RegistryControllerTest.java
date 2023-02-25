package com.example.timeregistrychallenger.controller;

import com.example.timeregistrychallenger.controller.dtos.requests.BeatRequestDTO;
import com.example.timeregistrychallenger.controller.dtos.responses.BeatResponseDTO;
import com.example.timeregistrychallenger.models.Beat;
import com.example.timeregistrychallenger.usecases.BeatRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BeatRegistry beatRegistry;

    @Test
    void shouldReturnBeatResponseDTO() {
        final LocalDateTime now = LocalDateTime.now();
        BeatRequestDTO requestDTO = new BeatRequestDTO(now);

        given(beatRegistry.execute(any(LocalDateTime.class))).willReturn(new Beat());

        final var response = restTemplate.postForEntity("/v1/batidas", requestDTO, BeatResponseDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}