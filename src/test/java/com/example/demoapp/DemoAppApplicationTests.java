package com.example.demoapp;

import com.example.demoapp.entities.Adn;
import com.example.demoapp.services.AdnServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoAppApplicationTests {



	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AdnServiceImpl servicio;
	@Test
	public void testSaveAdnAsMutant() throws Exception {// Crear el ADN de prueba
		List<String> adnList = Arrays.asList("ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");

		// Configurar la simulación del servicio
		Adn adn = new Adn();
		adn.setAdnList(adnList);
		adn.setMutant(true);

		when(servicio.findByAdnValue(anyString())).thenReturn(Optional.empty());
		when(servicio.saveAdn(anyList())).thenReturn(adn);

		// Realizar la solicitud y verificar la respuesta
		mockMvc.perform(post("/api/v1/adn/mutant/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(adnList))) // Enviamos la lista como JSON
				.andExpect(status().isOk());

	}

	@Test
	public void test1Mutante(){
		List<String> dna = Arrays.asList(
				"ATGCGA",
				"AAGTGC",
				"ATATGT",
				"AGAAGG",
				"TTTTTA",
				"TCACTG"
		);

		boolean resultado=Adn.esMutante(dna);
		Assertions.assertTrue(resultado);
	}

	@Test
	public void test2Mutante(){
		List<String> dna = Arrays.asList(
				"AAAA",
				"CCCC",
				"TCAG",
				"GGTC"
		);

		boolean resultado=Adn.esMutante(dna);
		Assertions.assertTrue(resultado);
	}

	@Test
	public void test3Mutante(){
		List<String> dna = Arrays.asList(
				"TGAC",
				"AGCC",
				"TGAC",
				"GGTC"
		);

		boolean resultado=Adn.esMutante(dna);
		Assertions.assertTrue(resultado);
	}

	@Test
	public void test1NoMutante(){
		List<String> dna = Arrays.asList(
				"TGAC",
				"ATCC",
				"TAAC",
				"GGTC"
		);

		boolean resultado=Adn.esMutante(dna);
		Assertions.assertFalse(resultado);
	}

	@Test
	public void test2NoMutante(){
		List<String> dna = Arrays.asList(
				"AAAA",
				"AAAA",
				"AAAA",
				"AAAA"
		);

		boolean resultado=Adn.esMutante(dna);
		Assertions.assertFalse(resultado);
	}

	@Test
	public void testMutanteColumnas(){
		List<String> dna = Arrays.asList(
				"ATGCGA",
				"AAGTGC",
				"ATGTGG",
				"AGAAGG",
				"CTCCTG",
				"TCACTG"
		);


		boolean resultado=Adn.esMutante(dna);
		Assertions.assertTrue(resultado);
	}
	@Test
	public void testMutanteDiagonalInvertida(){
		List<String> dna = Arrays.asList(
				"ATGCGA",
				"CTGGTC",
				"TTGTGT",
				"AGAGGA",
				"CCCCGA",
				"TCACTT"
		);

		boolean resultado=Adn.esMutante(dna);
		Assertions.assertTrue(resultado);
	}

	@Test
	public void testMatrizNxM() {
		List<String> dna = Arrays.asList("ATG", "CAG", "TA");


		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Adn.esMutante(dna);
		});
	}

	@Test
	public void testMatriznull() {
		List<String> dna = null;


		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Adn.esMutante(dna);
		});
	}

	@Test
	public void testCaracterNoValido() {
		List<String> dna = Arrays.asList(
				"ATGCGA",
				"CAGTGC",
				"TTATGT",
				"AGXAGG",
				"CCCCTA",
				"TCACTG"
		);


		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			Adn.esMutante(dna);
		});

	}

}
