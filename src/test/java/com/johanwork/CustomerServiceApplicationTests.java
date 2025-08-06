package com.johanwork;

import com.johanwork.client.MovieClient;
import com.johanwork.domain.Genre;
import com.johanwork.dto.CustomerDto;
import com.johanwork.dto.GenreUpdateRequest;
import com.johanwork.dto.MovieDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.ProblemDetail;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.List;

@Slf4j
@Import(TestcontainersConfiguration.class)
@MockitoBean(types = {RestClient.class, MovieClient.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MovieClient movieClient;

	@Test
	void health() {
		ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}

	@Test
	public void customerWithMovies(){
		// Mock Recommended movies
		Mockito.when(movieClient.getMovies(Mockito.any(Genre.class)))
				.thenReturn(List.of(
						new MovieDto(1L,"movie1",2020, Genre.ACTION),
						new MovieDto(2L,"movie2",2021, Genre.ACTION)
				));
		ResponseEntity<CustomerDto> response = this.restTemplate.getForEntity("/api/customers/1", CustomerDto.class);
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		CustomerDto customer = response.getBody();
		Assertions.assertNotNull(customer);
		Assertions.assertEquals("sam", customer.name());
		Assertions.assertEquals(2, customer.recommendedMovies().size());
	}

	@Test
	public void customerNotFound(){
		var responseEntity = this.restTemplate.getForEntity("/api/customers/10", ProblemDetail.class);
		Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
		var problemDetail = responseEntity.getBody();
		log.info("Problem detail: {}", problemDetail);
		Assertions.assertNotNull(problemDetail);
		Assertions.assertEquals("Customer Not Found", problemDetail.getTitle());
	}

	@Test
	public void updateGenre(){
		GenreUpdateRequest genreUpdateRequest = new GenreUpdateRequest(Genre.DRAMA);
		RequestEntity<GenreUpdateRequest> request =
				new RequestEntity<>(genreUpdateRequest, HttpMethod.PATCH, URI.create("/api/customers/1/genre"));
		ResponseEntity<Void> responseEntity = this.restTemplate.exchange(request, Void.class);
		Assertions.assertEquals(204, responseEntity.getStatusCode().value());
	}


}
