package com.kidzoo.toydetails.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidzoo.toydetails.exception.ToyDetailsCustomException;
import com.kidzoo.toydetails.model.response.ToyStatusById;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.kidzoo.toydetails.common.CommonMethods.readFile;
import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@Slf4j
@Component
public class    InventoryClient {

    final private String getServiceRoot = "http://localhost:8080/inventory/";
    final private String getFindStatus = "findByStatus";
    final private String getToyId = "{id}";
    final private String getStatus = "?status=";
    String referenceId = UUID.randomUUID().toString();
    ObjectMapper objectMapper = new ObjectMapper();

    public ToyStatusById findToyFromInventoryById(int toyId){
        RestTemplate restTemplate = new RestTemplate();
        final String url = getServiceRoot+toyId;
        final String getURLForToyId = getServiceRoot+getToyId;
        ToyStatusById toyStatusById = new ToyStatusById();
        toyStatusById.setId(toyId);
        toyStatusById.setStatus("available");
        try {
            MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
            String response = objectMapper.writeValueAsString(toyStatusById);
            server.expect(manyTimes(), requestTo(url)).andExpect(method(HttpMethod.GET))
                    .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));
            toyStatusById = restTemplate.getForObject(getURLForToyId, ToyStatusById.class, toyId);
        } catch (Exception exception) {
            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
        }
         return toyStatusById;
    }

    public List<ToyStatusById> getToyListFromInventoryByStatus(String status){
        RestTemplate restTemplate = new RestTemplate();
        final String url = getServiceRoot+getFindStatus+getStatus+status;
        final String getURLForToyDetailsByStatus = getServiceRoot+getFindStatus+getStatus+"{status}";
        List<ToyStatusById> toyDetailsByStatus = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);
        try {
            MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
            String available = new String(readFile("\\rest-mock\\getAllToyListByStatusAvailable.json"));
            String backOrder = new String(readFile("\\rest-mock\\getAllToyListByStatusBackOrder.json"));
            String outOfStock = new String(readFile("\\rest-mock\\getAllToyListByStatusOutOfStock.json"));
            if(status.equalsIgnoreCase("available")) {
                server.expect(manyTimes(), requestTo(url)).andExpect(method(HttpMethod.GET))
                        .andRespond(withSuccess(available, MediaType.APPLICATION_JSON));
                toyDetailsByStatus = Arrays.asList(restTemplate.getForObject(getURLForToyDetailsByStatus, ToyStatusById[].class,status));
            }else if(status.equalsIgnoreCase("backorder")) {
                server.expect(manyTimes(), requestTo(url)).andExpect(method(HttpMethod.GET))
                        .andRespond(withSuccess(backOrder, MediaType.APPLICATION_JSON));
                toyDetailsByStatus = Arrays.asList(restTemplate.getForObject(getURLForToyDetailsByStatus, ToyStatusById[].class,status));
            }else if(status.equalsIgnoreCase("outOfStock")) {
                    server.expect(manyTimes(), requestTo(url)).andExpect(method(HttpMethod.GET))
                            .andRespond(withSuccess(outOfStock, MediaType.APPLICATION_JSON));
                toyDetailsByStatus = Arrays.asList(restTemplate.getForObject(getURLForToyDetailsByStatus, ToyStatusById[].class,status));
                }
            } catch (Exception exception) {
                throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
            }
            return toyDetailsByStatus;
    }
}
