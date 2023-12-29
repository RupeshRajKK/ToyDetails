package com.kidzoo.toydetails.service.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidzoo.toydetails.client.InventoryClient;
import com.kidzoo.toydetails.client.ToyDetailsClient;
import com.kidzoo.toydetails.client.entity.ToyDetailsEntity;
import com.kidzoo.toydetails.common.CommonMethods;
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
import java.util.Arrays;
import java.util.List;
import static com.kidzoo.toydetails.common.test.CommonMethods.readFile;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ToydetailsServiceImplTest {

	@Autowired
	ToyDetailsServiceImpl toyDetailsServiceimpl;

	@MockBean
	InventoryClient inventoryClient;

	@MockBean
	ToyDetailsClient toyDetailsClient;

	private MockMvc mockMvc;
	ObjectMapper mapper = new ObjectMapper();

	@Test
	void getToyDetailsTest() {
		try {
			String toyDetails = new String(readFile("\\rest-mock\\getAllToyList.json"));
			List<ToyDetailsEntity> toyDetailsEntities = Arrays.asList(mapper.readValue(toyDetails, ToyDetailsEntity[].class));
			given(toyDetailsClient.findAll()).willReturn(toyDetailsEntities);
			ToyDetailsResponse toyDetailsResponse = toyDetailsServiceimpl.getToyDetails();
			Assert.assertEquals(1000,toyDetailsResponse.getToyDetailsList().get(0).getId());
			Assert.assertEquals("10",toyDetailsResponse.getToyDetailsList().get(0).getPrice());
			Assert.assertEquals("3",toyDetailsResponse.getToyDetailsList().get(0).getAge());
			Assert.assertEquals("car",toyDetailsResponse.getToyDetailsList().get(0).getName());
			Assert.assertEquals("src/media/Car.jpg",toyDetailsResponse.getToyDetailsList().get(0).getImageURL());
		} catch (Exception e) {
		}
	}

	@Test
	void getToyDetailsBasedOnPriceRangeTest(){
		try{
			String toyDetails = new String(readFile("\\rest-mock\\getAllToyList.json"));
			List<ToyDetailsEntity> toyDetailsEntities = Arrays.asList(mapper.readValue(toyDetails, ToyDetailsEntity[].class));
			given(toyDetailsClient.findAll()).willReturn(toyDetailsEntities);
			ToyDetailsResponse toyDetailsResponse = toyDetailsServiceimpl.getToyDetailsBasedOnPriceRange("5","15");
			Assert.assertEquals(1000,toyDetailsResponse.getToyDetailsList().get(0).getId());
			Assert.assertEquals("10",toyDetailsResponse.getToyDetailsList().get(0).getPrice());
			Assert.assertEquals("3",toyDetailsResponse.getToyDetailsList().get(0).getAge());
			Assert.assertEquals("car",toyDetailsResponse.getToyDetailsList().get(0).getName());
			Assert.assertEquals("src/media/Car.jpg",toyDetailsResponse.getToyDetailsList().get(0).getImageURL());
		}catch(Exception e){}
	}

	@Test
	void getToyStatusByIdTest() {
		try {
			ToyStatusById toyStatusById = new ToyStatusById();
			toyStatusById.setId(1000);
			toyStatusById.setStatus("available");
			given(inventoryClient.findToyFromInventoryById(ArgumentMatchers.anyInt())).willReturn(toyStatusById);
			ToyStatusById toyStatusByIdResp = toyDetailsServiceimpl.getToyStatusById(1000);
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
				given(inventoryClient.getToyListFromInventoryByStatus(ArgumentMatchers.any())).willReturn(toyDetailsResponse);
				List<ToyStatusById> toyStatusById = toyDetailsServiceimpl.getListOfToysByStatus("available");
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
			List<ToyStatusById> toyStatusById = toyDetailsServiceimpl.getListOfToysByStatus("backorder");
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
			List<ToyStatusById> toyStatusById = toyDetailsServiceimpl.getListOfToysByStatus("outofstock");
			Assert.assertEquals("outofstock",toyStatusById.get(0).getStatus());
			Assert.assertEquals(1010,toyStatusById.get(0).getId());
		} catch (Exception e) {
		}
	}
}
