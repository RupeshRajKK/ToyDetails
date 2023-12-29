package com.kidzoo.toydetails.dao.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidzoo.toydetails.common.CommonMethods;
import com.kidzoo.toydetails.model.response.ToyDetailsResponse;
import com.kidzoo.toydetails.model.response.ToyStatusById;
import com.kidzoo.toydetails.service.ToyDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.kidzoo.toydetails.common.test.CommonMethods.readFile;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
class ToydetailsDaoTest {

	@MockBean
	ToyDetailsServiceImpl toyDetailsServiceimpl;
	private MockMvc mockMvc;
	ObjectMapper mapper = new ObjectMapper();

	@Test
	void getToyDetailsTest() {
		try {
			String toyDetails = new String(readFile("\\rest-mock\\getAllToyList.json"));
			ToyDetailsResponse toyDetailsResponse = mapper.readValue(toyDetails, ToyDetailsResponse.class);
			given(toyDetailsServiceimpl.getToyDetails()).willReturn(toyDetailsResponse);
			mockMvc.perform(get("/toy-details/v1/get-list-of-toys/")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		} catch (Exception e) {
		}
	}

	@Test
	void getToyDetailsBasedOnPriceRangeTest(){
		try{
			String toyDetails = new String(readFile("\\rest-mock\\getToyDetailsBasedOnPriceRange.json"));
			ToyDetailsResponse toyDetailsResponse = mapper.readValue(toyDetails, ToyDetailsResponse.class);
			given(toyDetailsServiceimpl.getToyDetailsBasedOnPriceRange(ArgumentMatchers.any(),ArgumentMatchers.any())).willReturn(toyDetailsResponse);
			mockMvc.perform(get("/toy-details/v1/get-list-of-toys/byPrice/")
					.queryParam("price_range1","5")
					.queryParam("price_range2","15")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn();
		}catch(Exception e){}
	}

	@Test
	void getToyStatusByIdTest() {
		try {
			ToyStatusById toyStatusById = new ToyStatusById();
			toyStatusById.setId(1000);
			toyStatusById.setStatus("available");
			given(toyDetailsServiceimpl.getToyStatusById(ArgumentMatchers.anyInt())).willReturn(toyStatusById);
			mockMvc.perform(get("/v1/find-Toys-By-Status").queryParam("status","available")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		} catch (Exception e) {
		}
	}
		@Test
		void getToyDetailsByStatusAvailableTest() {
			try {
				String toyDetailsByStatus = new String(readFile("\\rest-mock\\getToyDetailsBasedOnPriceRange.json"));
				List<ToyStatusById> toyDetailsResponse = mapper.readValue(toyDetailsByStatus, new TypeReference<List<ToyStatusById>>(){});
				String available = new String(CommonMethods.readFile("\\rest-mock\\getAllToyListByStatusAvailable.json"));
				given(toyDetailsServiceimpl.getListOfToysByStatus(ArgumentMatchers.any())).willReturn(toyDetailsResponse);
				mockMvc.perform(get("/v1/find-Toys-By-Status").queryParam("status","available")
						.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			} catch (Exception e) {
			}
	}


	@Test
	void getToyDetailsByStatusBackOrderTest() {
		try {
			String toyDetailsByStatus = new String(readFile("\\rest-mock\\getToyDetailsBasedOnPriceRange.json"));
			List<ToyStatusById> toyDetailsResponse = mapper.readValue(toyDetailsByStatus, new TypeReference<List<ToyStatusById>>(){});
			String backOrder = new String(CommonMethods.readFile("\\rest-mock\\getAllToyListByStatusBackOrder.json"));
			given(toyDetailsServiceimpl.getListOfToysByStatus(ArgumentMatchers.any())).willReturn(toyDetailsResponse);
			mockMvc.perform(get("/v1/find-Toys-By-Status").queryParam("status","backorder")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		} catch (Exception e) {
		}
	}


	@Test
	void getToyDetailsByStatusOutOfStockTest() {
		try {
			String toyDetailsByStatus = new String(readFile("\\rest-mock\\getToyDetailsBasedOnPriceRange.json"));
			List<ToyStatusById> toyDetailsResponse = mapper.readValue(toyDetailsByStatus, new TypeReference<List<ToyStatusById>>(){});
			String outOfStock = new String(CommonMethods.readFile("\\rest-mock\\getAllToyListByStatusOutOfStock.json"));
				given(toyDetailsServiceimpl.getListOfToysByStatus(ArgumentMatchers.any())).willReturn(toyDetailsResponse);
			mockMvc.perform(get("/v1/find-Toys-By-Status").queryParam("status","outofstatus")
					.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		} catch (Exception e) {
		}
	}
}
