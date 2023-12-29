package com.kidzoo.toydetails.controller;

import com.kidzoo.toydetails.client.entity.Basket;
import com.kidzoo.toydetails.dao.CheckoutResponse;
import com.kidzoo.toydetails.dao.GeneralResponse;
import com.kidzoo.toydetails.service.BasketServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Api(tags = "Basket")
@RestController
@RequestMapping(value = "/v1")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class BasketController {

    @Autowired
    BasketServiceImpl basketService;

    @PostMapping(value = "/basket/add", produces = "application/hal+json")
    public GeneralResponse add(@RequestBody Basket basket) {
        return basketService.add(basket);
    }


    @GetMapping(value = "/view-basket-by-ID/{id}", produces = "application/hal+json")
    public Basket getBasketById(@PathVariable int id) {
        return basketService.getBasketsById(id);
    }

    @GetMapping(value = "/view-basket-query-by-ID/{id}", produces = "application/hal+json")
    public Basket getBasketByQueryId(@PathVariable int id) {
        return basketService.getBasketByQueryId(id);
    }

    @GetMapping(value = "/view-all-basket", produces = "application/hal+json")
    public List<Basket> getAllBasket(){
        return basketService.getAllBasket();
    }

    @GetMapping(value = "/checkout", produces = "application/hal+json")
    public CheckoutResponse checkoutResponse(){
        return basketService.checkoutResponse();
    }

}

