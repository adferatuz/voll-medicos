package med.voll.api.controller;

import med.voll.api.domain.modelos.consulta.dto.DatosAgendarConsulta;
import med.voll.api.domain.modelos.consulta.dto.DatosDetalleConsulta;
import med.voll.api.domain.modelos.medico.Especialidad;
import med.voll.api.domain.servicios.AgendaDeConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @MockBean
    private AgendaDeConsultaService agendaDeConsultaService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DatosAgendarConsulta> agendarConsultaJacksonTester;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJacksonTester;

    @Test
    @DisplayName("Deberia retornar un estado http 400 cuando los datos ingresados sean invalidos.")
    @WithMockUser
    void agendarEscenario1() throws Exception {

        var response = mvc.perform(post("/consultas"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deberia retornar un estado http 200 cuando los datos ingresados sean validos.")
    @WithMockUser
    void agendarEscenario2() throws Exception {

        var fecha = LocalDateTime.now().plusHours(1);

        var especialidad = Especialidad.CARDIOLOGIA;

        var datos = new DatosDetalleConsulta(null, 2l, 5l, fecha);

        when(agendaDeConsultaService.agendar(any()))
                .thenReturn(datos);

        var response = mvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(agendarConsultaJacksonTester
                                .write(new DatosAgendarConsulta(null,2l, 5l, fecha, especialidad ))
                                .getJson())
                ).andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = datosDetalleConsultaJacksonTester
                .write(datos).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);


    }


}