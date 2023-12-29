package com.kidzoo.toydetails.service;

import com.kidzoo.toydetails.client.InventoryClient;
import com.kidzoo.toydetails.client.ToyDetailsClient;

import com.kidzoo.toydetails.client.entity.Basket;
import com.kidzoo.toydetails.client.entity.ToyDetailsEntity;
import com.kidzoo.toydetails.exception.ToyDetailsCustomException;
import com.kidzoo.toydetails.model.response.ToyDetailsResponse;
import com.kidzoo.toydetails.model.response.ToyStatusById;
import com.kidzoo.toydetails.repository.ToyDetailsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToyDetailsServiceImpl {
    @Autowired
    private ToyDetailsClient toyDetailsClient;
    @Autowired
    private InventoryClient inventoryClient;

    @Autowired
    private ToyDetailsRepository repository;

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

    public ToyDetailsResponse getToyDetailsBasedOnPriceRange(String priceRange_1, String priceRange_2){
        ToyDetailsResponse toyDetailsResponse = new ToyDetailsResponse();
        try {
            List<ToyDetailsEntity> getToyDetails = toyDetailsClient.findAll();
            toyDetailsResponse.setToyDetailsList(getToyDetails
                    .stream().filter(s -> s.getPrice().compareTo(new BigDecimal(priceRange_1)) >= 0
                            && s.getPrice().compareTo(new BigDecimal(priceRange_2)) <= 0)
                    .collect(Collectors.toList()));
        }catch(Exception exception) {
            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
        }
        return toyDetailsResponse;
    }

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

//###################################################################################################################



    public ToyDetailsEntity saveToy(ToyDetailsEntity toyDetailsEntity){
        return repository.save(toyDetailsEntity);
    }

    public List<ToyDetailsEntity> saveToys(List<ToyDetailsEntity> toyDetailsEntities){
        return repository.saveAll(toyDetailsEntities);
    }

    public List<ToyDetailsEntity> getToys(){
        return repository.findAll();
    }

    public ToyDetailsEntity getToysById(int id){
        return repository.findById(id).orElse(null);
    }

    public ToyDetailsEntity getToysByName(String name){
        return repository.findByName(name);
    }

    public String deleteToy(int id){
        repository.deleteById(id);
        return " Toy id " + id + " is removed";
    }

    public ToyDetailsEntity updateToy(ToyDetailsEntity toyDetailsEntity){
        ToyDetailsEntity existingProduct = repository.findById(toyDetailsEntity.getId()).orElse(null);

        existingProduct.setId(toyDetailsEntity.getId());
        existingProduct.setPrice(toyDetailsEntity.getPrice());
        existingProduct.setAge(toyDetailsEntity.getAge());
        existingProduct.setName(toyDetailsEntity.getName());
        return repository.save(existingProduct);
    }


}
