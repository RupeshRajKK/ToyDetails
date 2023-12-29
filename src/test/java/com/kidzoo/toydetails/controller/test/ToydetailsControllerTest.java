package com.kidzoo.toydetails.controller.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidzoo.toydetails.common.CommonMethods;
import com.kidzoo.toydetails.controller.ToyDetailsController;
import com.kidzoo.toydetails.model.response.ToyDetailsResponse;
import com.kidzoo.toydetails.model.response.ToyStatusById;
import com.kidzoo.toydetails.service.ToyDetailsServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static com.kidzoo.toydetails.common.test.CommonMethods.readFile;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ToydetailsControllerTest {

	@MockBean
	ToyDetailsServiceImpl toyDetailsServiceimpl;
	private MockMvc mockMvc;
	@Autowired
	ToyDetailsController toyDetailsController;
	ObjectMapper mapper = new ObjectMapper();

	@Test
	void getToyDetailsTest() {
		try {
			String toyDetails = new String(readFile("\\rest-mock\\getAllToyList.json"));
			ToyDetailsResponse toyDetailsResponseMapper = mapper.readValue(toyDetails, ToyDetailsResponse.class);
			given(toyDetailsServiceimpl.getToyDetails()).willReturn(toyDetailsResponseMapper);
			ToyDetailsResponse toyDetailsResponse = toyDetailsController.getToyDetails();
			Assert.assertEquals(1000,toyDetailsResponse.getToyDetailsList().get(0).getId());
			Assert.assertEquals(new BigDecimal(10),toyDetailsResponse.getToyDetailsList().get(0).getPrice());
			Assert.assertEquals("3",toyDetailsResponse.getToyDetailsList().get(0).getAge());
			Assert.assertEquals("Car",toyDetailsResponse.getToyDetailsList().get(0).getName());
			Assert.assertEquals("src/media/Car.jpg",toyDetailsResponse.getToyDetailsList().get(0).getImageURL());
		} catch (Exception e) {
		}
	}

	@Test
	void getToyDetailsBasedOnPriceRangeTest(){
		try{
			String toyDetails = new String(readFile("\\rest-mock\\getToyDetailsBasedOnPriceRange.json"));
			ToyDetailsResponse toyDetailsResponseMapper = mapper.readValue(toyDetails, ToyDetailsResponse.class);
			given(toyDetailsServiceimpl.getToyDetailsBasedOnPriceRange(ArgumentMatchers.any(),ArgumentMatchers.any())).willReturn(toyDetailsResponseMapper);
			ToyDetailsResponse toyDetailsResponse = toyDetailsController.getToyDetailsBasedOnPriceRange("5","15");
			Assert.assertEquals(1000,toyDetailsResponse.getToyDetailsList().get(0).getId());
			Assert.assertEquals(new BigDecimal(10),toyDetailsResponse.getToyDetailsList().get(0).getPrice());
			Assert.assertEquals("3",toyDetailsResponse.getToyDetailsList().get(0).getAge());
			Assert.assertEquals("Car",toyDetailsResponse.getToyDetailsList().get(0).getName());
			Assert.assertEquals("src/media/Car.jpg",toyDetailsResponse.getToyDetailsList().get(0).getImageURL());
		}catch(Exception e){}
	}

	@Test
	void getToyStatusByIdTest() {
		try {
			ToyStatusById toyStatusById = new ToyStatusById();
			toyStatusById.setId(1000);
			toyStatusById.setStatus("available");
			given(toyDetailsServiceimpl.getToyStatusById(ArgumentMatchers.anyInt())).willReturn(toyStatusById);
			ToyStatusById toyStatusByIdResp = toyDetailsController.getToyStatusById(1000);
			Assert.assertEquals(1000,toyStatusByIdResp.getId());
			Assert.assertEquals("available",toyStatusByIdResp.getStatus());
		} catch (Exception e) {
		}
	}
		@Test
		void getToyDetailsByStatusAvailableTest() {
			try {
				String available = new String(CommonMethods.readFile("\\rest-mock\\getAllToyListByStatusAvailable.json"));
				List<ToyStatusById> toyDetailsResponse = mapper.readValue(available, new TypeReference<List<ToyStatusById>>(){});
				given(toyDetailsServiceimpl.getListOfToysByStatus(ArgumentMatchers.any())).willReturn(toyDetailsResponse);
				List<ToyStatusById> toyStatusById = toyDetailsController.getToyListByStatus("available");
				Assert.assertEquals("available",toyStatusById.get(0).getStatus());
				Assert.assertEquals(1000,toyStatusById.get(0).getId());
			} catch (Exception e) {
			}
	}


	@Test
	void getToyDetailsByStatusBackOrderTest() {
		try {
			String backOrder = new String(CommonMethods.readFile("\\rest-mock\\getAllToyListByStatusBackOrder.json"));
			List<ToyStatusById> toyDetailsResponse = mapper.readValue(backOrder, new TypeReference<List<ToyStatusById>>(){});
			given(toyDetailsServiceimpl.getListOfToysByStatus(ArgumentMatchers.any())).willReturn(toyDetailsResponse);
			List<ToyStatusById> toyStatusById = toyDetailsController.getToyListByStatus("backorder");
			Assert.assertEquals("backorder",toyStatusById.get(0).getStatus());
			Assert.assertEquals(1017,toyStatusById.get(0).getId());
		} catch (Exception e) {
		}
	}


	@Test
	void getToyDetailsByStatusOutOfStockTest() {
		try {
			String outOfStock = new String(CommonMethods.readFile("\\rest-mock\\getAllToyListByStatusOutOfStock.json"));
			List<ToyStatusById> toyDetailsResponse = mapper.readValue(outOfStock, new TypeReference<List<ToyStatusById>>(){});
			given(toyDetailsServiceimpl.getListOfToysByStatus(ArgumentMatchers.any())).willReturn(toyDetailsResponse);
			List<ToyStatusById> toyStatusById = toyDetailsController.getToyListByStatus("outofstock");
			Assert.assertEquals("outofstock",toyStatusById.get(0).getStatus());
			Assert.assertEquals(1010,toyStatusById.get(0).getId());
		} catch (Exception e) {
		}
	}
}
