package com.kidzoo.toydetails.service;

import com.kidzoo.toydetails.client.BasketDetailsClient;
import com.kidzoo.toydetails.client.ToyDetailsClient;
import com.kidzoo.toydetails.client.entity.Basket;
import com.kidzoo.toydetails.client.entity.ToyDetailsEntity;
import com.kidzoo.toydetails.dao.CheckoutItem;
import com.kidzoo.toydetails.dao.CheckoutResponse;
import com.kidzoo.toydetails.dao.GeneralResponse;
import com.kidzoo.toydetails.exception.ToyDetailsCustomException;
import com.kidzoo.toydetails.model.response.BasketDetailsResponse;
import com.kidzoo.toydetails.model.response.ToyDetails;
import com.kidzoo.toydetails.repository.BasketRepository;
import com.kidzoo.toydetails.repository.ToyDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
//        System.out.println("basket= " + basket);
//        System.out.println("Toy= " + product.toString());
        if (product != null) {
            repository.save(basket);
            return new GeneralResponse(true, "Succcessfully added to basket");
        }
        return new GeneralResponse(false, "Invalid toy id.");
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


    public List<Basket> getAllBasket() {
        return repository.findAll();
    }


    public CheckoutResponse checkoutResponse() {
       CheckoutResponse checkoutResponse = new CheckoutResponse();
       List<CheckoutItem> items = repository.checkout();
       double total = 0;
       for (int i=0; i<items.size(); i++){
       total = total + items.get(i).getTotal();
       }
       checkoutResponse.items=items;
       checkoutResponse.total=total;
        return checkoutResponse;
    }
}

