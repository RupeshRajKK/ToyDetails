package com.kidzoo.toydetails.service;

import com.kidzoo.toydetails.client.BasketDetailsClient;
import com.kidzoo.toydetails.entity.Basket;
import com.kidzoo.toydetails.entity.ToyDetailsEntity;
import com.kidzoo.toydetails.entity.UserEntity;
import com.kidzoo.toydetails.dao.CheckoutItem;
import com.kidzoo.toydetails.dao.CheckoutResponse;
import com.kidzoo.toydetails.dao.GeneralResponse;
import com.kidzoo.toydetails.exception.ToyDetailsCustomException;
import com.kidzoo.toydetails.model.response.BasketDetailsResponse;
import com.kidzoo.toydetails.repository.BasketRepository;
import com.kidzoo.toydetails.repository.ToyDetailsRepository;
import com.kidzoo.toydetails.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl {

    @Autowired
    private BasketDetailsClient basketDetailsClient;

    @Autowired
    private BasketRepository repository;

    @Autowired
    private ToyDetailsRepository toyDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    String referenceId = UUID.randomUUID().toString();

    public BasketDetailsResponse getBasketDetails() {
        BasketDetailsResponse basketDetails = new BasketDetailsResponse();
        try {
            basketDetails.setBasketDetailsList(basketDetailsClient.findAll());
        } catch (Exception exception) {
            throw new ToyDetailsCustomException(referenceId, exception.getMessage(), "400");
        }
        return basketDetails;
    }

    public GeneralResponse add(Basket basket) {
        ToyDetailsEntity product = toyDetailsRepository.findById(basket.getToyId()).orElse(null);
        if (product == null){
            return new GeneralResponse(false,"Product not found");
        }
        UserEntity users = userRepository.findById(basket.getUserId()).orElse(null);
        if (users == null){
            return new GeneralResponse(false,"User not found");
        }
        Basket dbobj= repository.findByToyIdAndUserId(basket.getToyId(), basket.getUserId());
        if (dbobj == null) {
            repository.save(basket);
            return new GeneralResponse(true, "Succcessfully added to basket");
        }
        dbobj.setQuantity(basket.getQuantity());
        update(dbobj);
        return new GeneralResponse(true, "Succcessfully updated to basket");
    }

    public Basket update(Basket basket) {
        Basket product = repository.findById(basket.getBasketId()).orElse(null);

        product.setBasketId(basket.getBasketId());
        product.setQuantity(basket.getQuantity());
        return repository.save(basket);
    }

    public String delete(int basketId) {
        repository.deleteById(basketId);
        return basketId + "is removed";
    }

    public Basket getBasketsById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Basket getBasketByQueryId(int id){
        return repository.findByQueryId(id);
    }


    public List<Basket> getAllBasket() {
        return repository.findAll();
    }


    public CheckoutResponse checkoutResponse() {
       List<CheckoutItem> items = repository.checkout();
        return new CheckoutResponse(items);
    }

    public CheckoutResponse checkoutByUserId(int id){
        List<CheckoutItem> items = repository.checkoutByUserId(id);
        return new CheckoutResponse(items);
    }
}

