package hu.sonrisa.vatcalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.sonrisa.vatcalculator.model.VatCalculationRequestDTO;
import hu.sonrisa.vatcalculator.model.VatCalculationResponseDTO;
import hu.sonrisa.vatcalculator.service.VatCalculationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VatCalculationController.class)
class VatCalculationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private VatCalculationServiceImpl vatCalculationServiceImpl;

  @Nested
  @DisplayName("Valid scenarios")
  class ValidCases {

    @Test
    @DisplayName("Should return 200 OK and calculated VAT response")
    void shouldReturnCalculatedVatResponse() throws Exception {
      // Arrange
      final VatCalculationRequestDTO request = new VatCalculationRequestDTO(
          "100.00",
          null,
          null,
          "0.20"
      );

      final VatCalculationResponseDTO expectedResponse = new VatCalculationResponseDTO(
          new BigDecimal("100.00"),
          new BigDecimal("120.00"),
          new BigDecimal("20.00")
      );

      Mockito.when(vatCalculationServiceImpl.calculate(request)).thenReturn(expectedResponse);

      // Act & Assert
      mockMvc.perform(post("/api/vat/calculate")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.netAmount").value("100.00"))
          .andExpect(jsonPath("$.grossAmount").value("120.00"))
          .andExpect(jsonPath("$.vatAmount").value("20.00"));

      verify(vatCalculationServiceImpl).calculate(Mockito.any());
    }
  }

  @Nested
  @DisplayName("Invalid request scenarios")
  class InvalidCases {

    @Test
    @DisplayName("Should return 400 Bad Request when all input fields are null")
    void returnsBadRequestWhenAllFieldsAreNull() throws Exception {
      final VatCalculationRequestDTO invalidRequest = new VatCalculationRequestDTO(
          null, null, null, null
      );

      mockMvc.perform(post("/api/vat/calculate")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(invalidRequest)))
          .andExpect(status().isBadRequest());

      verifyNoMoreInteractions(vatCalculationServiceImpl);
    }

    @Test
    @DisplayName("Should return 400 Bad Request when input amount is zero")
    void returnsBadRequestWhenAmountIsZero() throws Exception {
      final VatCalculationRequestDTO invalidRequest = new VatCalculationRequestDTO(
          "0.00",
          null,
          null,
          "0.20"
      );

      mockMvc.perform(post("/api/vat/calculate")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(invalidRequest)))
          .andExpect(status().isBadRequest());

      verifyNoMoreInteractions(vatCalculationServiceImpl);
    }

    @Test
    @DisplayName("Should return 400 Bad Request when more than one amount field is set")
    void returnsBadRequestWhenMultipleAmountFieldsAreSet() throws Exception {
      final VatCalculationRequestDTO invalidRequest = new VatCalculationRequestDTO(
          "12.00",
          "12.00",
          null,
          "0.10"
      );

      mockMvc.perform(post("/api/vat/calculate")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(invalidRequest)))
          .andExpect(status().isBadRequest());

      verifyNoMoreInteractions(vatCalculationServiceImpl);
    }
  }
}
