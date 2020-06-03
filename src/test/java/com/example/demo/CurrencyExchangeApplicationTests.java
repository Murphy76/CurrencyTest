package com.example.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.mycompany.currency.CurrencyExchangeApplication;
import com.mycompany.currency.dto.Commission;
import com.mycompany.currency.dto.ExchangeRate;
import com.mycompany.currency.model.Currency;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CurrencyExchangeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CurrencyExchangeApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testUnauthorized() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/commissions", HttpMethod.GET,
				entity, String.class);
		assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void testWrongAuthorized() {
		String username = "user";
		String password = "0";
		String url = "http://localhost:" + port + "/api/commissions";
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
		TestRestTemplate testRestTemplate1 = new TestRestTemplate(username, password);
		ResponseEntity<String> responseEntity = testRestTemplate1.exchange(url, HttpMethod.GET, requestEntity,
				String.class);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}

	@Test
	public void testAuthorized() {
		String username = "admin";
		String password = "1";
		String url = "http://localhost:" + port + "/api/commissions";
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
		TestRestTemplate testRestTemplate1 = new TestRestTemplate(username, password);
		ResponseEntity<String> responseEntity = testRestTemplate1.exchange(url, HttpMethod.GET, requestEntity,
				String.class);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List <Commission> example = new ArrayList<Commission>();
		example.add(new Commission("15", Currency.USD, Currency.UAH));
		example.add(new Commission(new BigDecimal(3), Currency.USD, Currency.EUR));
		example.add(new Commission("0", Currency.EUR, Currency.USD));
		example.add(new Commission("0", Currency.UAH, Currency.USD));
		List <Commission> result = null;
		try {
			 result = getCommissions(responseEntity.getBody());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		assertEquals(4, result.size());
		result.removeAll(example);
		assertEquals(0, result.size());
		
	}

	@Test
	public void creatRate() {
		String url = "http://localhost:" + port + "/api/exchange-rates";
		String username = "admin";
		String password = "1";

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		ExchangeRate rate = new ExchangeRate(new BigDecimal(1.0), Currency.USD, Currency.EUR);
		HttpEntity<ExchangeRate> requestEntity = new HttpEntity<>(rate, requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(username, password).exchange(url,
				HttpMethod.POST, requestEntity, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void setCommission() {
		String url = "http://localhost:" + port + "/api/commissions";
		String username = "admin";
		String password = "1";
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Commission commission = new Commission(new BigDecimal(3.0), Currency.USD, Currency.EUR);
		HttpEntity<Commission> requestEntity = new HttpEntity<>(commission, requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(username, password).exchange(url,
				HttpMethod.POST, requestEntity, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void creatSecondRate() {
		String url = "http://localhost:" + port + "/api/exchange-rates";
		String username = "admin";
		String password = "1";

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		ExchangeRate rate = new ExchangeRate(new BigDecimal(0.5), Currency.USD, Currency.UAH);
		HttpEntity<ExchangeRate> requestEntity = new HttpEntity<>(rate, requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(username, password).exchange(url,
				HttpMethod.POST, requestEntity, String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void setSecondCommission() {
		String url = "http://localhost:" + port + "/api/commissions";
		String username = "admin";
		String password = "1";
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Commission commission = new Commission(new BigDecimal(15.0), Currency.USD, Currency.UAH);
		HttpEntity<Commission> requestEntity = new HttpEntity<>(commission, requestHeaders);
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(username, password).exchange(url,
				HttpMethod.POST, requestEntity, String.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	public void getRates() {
		String username = "user";
		String password = "1";
		String url = "http://localhost:" + port + "/api/exchange-rates";
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
		TestRestTemplate testRestTemplate1 = new TestRestTemplate(username, password);
		ResponseEntity<String> responseEntity = testRestTemplate1.exchange(url, HttpMethod.GET, requestEntity,
				String.class);
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<ExchangeRate> example = new ArrayList<ExchangeRate>();
		example.add(new ExchangeRate(new BigDecimal(0.5), Currency.USD, Currency.UAH));
		example.add(new ExchangeRate(new BigDecimal(1.0), Currency.USD, Currency.EUR));
		example.add(new ExchangeRate(new BigDecimal(1.0), Currency.EUR, Currency.USD));
		example.add(new ExchangeRate(new BigDecimal(2.0), Currency.UAH, Currency.USD));
		try {
			List<ExchangeRate> result = getRates(responseEntity.getBody());
			assertEquals(4, result.size());
			result.removeAll(example);
			assertEquals(0, result.size());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public List<ExchangeRate> getRates(String body) throws JSONException {
		List<ExchangeRate> rateList = null;
		JSONArray array = new JSONArray(body);
		rateList = IntStream.range(0, array.length()).mapToObj(i -> {
			try {
				return parseExchangeRate((JSONObject) array.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
		return rateList;
	}

	public ExchangeRate parseExchangeRate(JSONObject obj) {
		ObjectMapper mapper = new ObjectMapper().registerModule(new JsonOrgModule());
		ExchangeRate value = mapper.convertValue(obj, ExchangeRate.class);
		return value;
	}
	public List<Commission> getCommissions(String body) throws JSONException {
		List<Commission> commissionList = null;
		JSONArray array = new JSONArray(body);
		commissionList = IntStream.range(0, array.length()).mapToObj(i -> {
			try {
				return parseCommission((JSONObject) array.getJSONObject(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
		return commissionList;
	}

	public Commission parseCommission(JSONObject obj) {
		ObjectMapper mapper = new ObjectMapper().registerModule(new JsonOrgModule());
		Commission value = mapper.convertValue(obj, Commission.class);
		return value;
	}

}
