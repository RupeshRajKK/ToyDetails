package com.kidzoo.toydetails.service;

import com.kidzoo.toydetails.client.InventoryClient;
import com.kidzoo.toydetails.client.ToyDetailsClient;

import com.kidzoo.toydetails.client.entity.ToyDetailsEntity;
import com.kidzoo.toydetails.exception.ToyDetailsCustomException;
import com.kidzoo.toydetails.model.response.ToyDetailsResponse;
import com.kidzoo.toydetails.model.response.ToyStatusById;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToyDetailsServiceImpl {
    @Autowired
    private ToyDetailsClient toyDetailsClient;
    @Autowired
    private InventoryClient inventoryClient;
    String referenceId = UUID.randomUUID().toString();

    public ToyDetailsResponse getToyDetails() {
        ToyDetailsResponse toyDetailsResponse = new ToyDetailsResponse();
        try {
             toyDetailsResponse.setToyDetailsList(toyDetailsClient.findAll());
         }catch(Exception exception) {
             throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
         }
        return toyDetailsResponse;
    }

//    public ToyDetailsResponse getToyDetailsBasedOnPriceRange(String priceRange_1, String priceRange_2){
//        ToyDetailsResponse toyDetailsResponse = new ToyDetailsResponse();
//        try {
//            List<ToyDetailsEntity> getToyDetails = toyDetailsClient.findAll();
//            toyDetailsResponse.setToyDetailsList(getToyDetails
//                    .stream().filter(s -> s.getPrice().compareTo(new BigDecimal(priceRange_1)) >= 0
//                            && s.getPrice().compareTo(new BigDecimal(priceRange_2)) <= 0)
//                    .collect(Collectors.toList()));
//        }catch(Exception exception) {
//            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
//        }
//        return toyDetailsResponse;
//    }

    public ToyDetailsResponse getToyDetailsBasedOnPriceRange(String priceRange_1, String priceRange_2){
        ToyDetailsResponse toyDetailsResponse = new ToyDetailsResponse();
        try {
            List<ToyDetailsEntity> array = toyDetailsClient.findAll();
            List<ToyDetailsEntity> outArray = new ArrayList<ToyDetailsEntity>();

            for (int i=0; i<array.size(); i++){
                ToyDetailsEntity toy = array.get(i); //getting an object from index "i"
                int price = toy.getPrice().intValue(); //converting bigdecimal to int
                int min = Integer.parseInt(priceRange_1); //converting string to int
                int max = Integer.parseInt(priceRange_2); //converting string to int
                if (price >= min && price <= max ){
                    outArray.add(toy);

                }
            }
            toyDetailsResponse.setToyDetailsList(outArray);
        }catch(Exception exception) {
            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
        }
        return toyDetailsResponse;
    }

    public ToyDetailsResponse getToyDetailsBasedOnSearchString(String searchString){
        ToyDetailsResponse toyDetailsResponse = new ToyDetailsResponse();
        try {
            List<ToyDetailsEntity> array = toyDetailsClient.findAll();
            List<ToyDetailsEntity> outArray = new ArrayList<ToyDetailsEntity>();

            for (int i=0; i<array.size(); i++){
                ToyDetailsEntity toy = array.get(i); //getting an object from index "i"
                String name = toy.getName();

                if (name.toLowerCase().contains(searchString.toLowerCase())){
                    outArray.add(toy);
                }
            }
            toyDetailsResponse.setToyDetailsList(outArray);
        }catch(Exception exception) {
            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
        }
        return toyDetailsResponse;
    }

//    public ToyDetailsResponse getToyDetailsBasedOnSort(String sort){
//        ToyDetailsResponse toyDetailsResponse = new ToyDetailsResponse();
//        try {
//            List<ToyDetailsEntity> array = toyDetailsClient.findAll();
//            List<ToyDetailsEntity> outArray = new ArrayList<ToyDetailsEntity>();
//
//            for (int i=0; i<array.size(); i++){
//                ToyDetailsEntity toy = array.get(i); //getting an object from index "i"
//                //String name = toy.getName();
//                BigDecimal price = toy.getPrice();
//
//                if (array.sort(price)){
//                    outArray.add(toy);
//                }
//            }
//            toyDetailsResponse.setToyDetailsList(outArray);
//        }catch(Exception exception) {
//            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
//        }
//        return toyDetailsResponse;
//    }
    public ToyStatusById getToyStatusById(int toyId) {
        ToyStatusById toyStatusById ;
        try {
            toyStatusById = inventoryClient.findToyFromInventoryById(toyId);
        } catch (Exception exception) {
            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
        }
        return toyStatusById;
    }

    public List<ToyStatusById> getListOfToysByStatus(String status) {
        try{
            return inventoryClient.getToyListFromInventoryByStatus(status);
        }catch (Exception exception) {
            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
        }
    }

//    public List<ToyStatusById> getToyListByAscendingDescendingOrder(String status) {

//        try{
//            CompareHelper com = new CompareHelper();
//            List<ToyDetailsEntity> array = toyDetailsClient.findAll();
//            array.sort(new CompareHelper());
//
//
//        }catch (Exception exception) {
//            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
//        }
//        return
//    }
}
