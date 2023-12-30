package com.kidzoo.toydetails.controller;

import com.kidzoo.toydetails.entity.ToyDetailsEntity;
import com.kidzoo.toydetails.model.response.ToyDetailsResponse;
import com.kidzoo.toydetails.model.response.ToyStatusById;
import com.kidzoo.toydetails.service.ToyDetailsServiceImpl;
import com.kidzoo.toydetails.exception.Error;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "ToyDetails")
@RestController
@RequestMapping(value = "/v1")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class ToyDetailsController {
    @Autowired
    ToyDetailsServiceImpl toyDetailsService;

    @ApiOperation(value = "This API will retrieve list of all toys from database")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping(value = "/get-list-of-toys", produces = "application/hal+json")
    public ToyDetailsResponse getToyDetails(){
        return toyDetailsService.getToyDetails();
    }


    @ApiOperation(value = "This API will retrieve list of all toys with thr price range from database")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping(value = "/get-list-of-toys/byPrice", produces = "application/hal+json")
    public ToyDetailsResponse getToyDetailsBasedOnPriceRange(
            @NotNull @NotEmpty @ApiParam(value = "This is value of price range 1", required = true) @RequestParam(value = "price_range1") String price_range1,
            @NotNull @NotEmpty @ApiParam(value = "This is value of price range 2", required = true) @RequestParam(value = "price_range2") String price_range2){
        return toyDetailsService.getToyDetailsBasedOnPriceRange(price_range1,price_range2);
    }

    @ApiOperation(value = "This API will retrieve status of toy")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyStatusById.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping(value = "/get-status-by-toyId/{toyId}", produces = "application/hal+json")
    public ToyStatusById getToyStatusById(
            @NotNull @NotEmpty @ApiParam(value = "This is value of toy id", required = true) @PathVariable(value = "toyId") int toyId){
        return toyDetailsService.getToyStatusById(toyId);
    }

    @ApiOperation(value = "This API will retrieve list of toy when queried with status")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyStatusById.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping(value = "/find-toys-by-status", produces = "application/hal+json")
    public List<ToyStatusById> getToyListByStatus(
            @NotNull @NotEmpty @ApiParam(value = "This is status of toy", required = true
                    , allowableValues =  "available,backorder,outofstock") @RequestParam(value = "status") String status){
        return toyDetailsService.getListOfToysByStatus(status);
    }

    @ApiOperation(value = "This API will retrieve list of toy when queried with string")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping(value = "/find-toys-by-name", produces = "application/hal+json")
    public ToyDetailsResponse getToyListByName(
            @NotNull @NotEmpty @ApiParam(value = "This is search string", required = true) @RequestParam(value = "searchString") String searchString){
        return toyDetailsService.getToyDetailsBasedOnSearchString(searchString);
    }

    //###############################################################################################################

    @ApiOperation(value = "This API will add Toy in basket")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @PostMapping(value = "/add_Toy", produces = "application/hal+json")
    public ToyDetailsEntity addToy(@RequestBody ToyDetailsEntity toyDetailsEntity){
        return toyDetailsService.saveToy(toyDetailsEntity);
    }

    @ApiOperation(value = "This API will add list of Toys in basket")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @PostMapping("/addToys")
    public List<ToyDetailsEntity> saveToys(@RequestBody List<ToyDetailsEntity> toyDetailsEntities){
        return toyDetailsService.saveToys(toyDetailsEntities);
    }

    @ApiOperation(value = "This API will show the list the Toys in basket")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping("/o")
    public List<ToyDetailsEntity> getToys(){
        return toyDetailsService.getToys();
    }

    @ApiOperation(value = "This API will list Toys in basket by using ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping("/view-toy-by-ID/{id}")
    public ToyDetailsEntity getToysById(@PathVariable int id){
        return toyDetailsService.getToysById(id);
    }

    @ApiOperation(value = "This API will list Toys in basket by using Name")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @GetMapping("/view-toy-by-name/{name}")
    public ToyDetailsEntity getToysByName(@PathVariable String name){
        return toyDetailsService.getToysByName(name);
    }

    @ApiOperation(value = "This API will delete Toy in basket by using ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @DeleteMapping(value = "/delete-toy/{id}", produces = "application/hal+json")
    public String deleteToy(@PathVariable int id){
        return toyDetailsService.deleteToy(id);
    }

    @ApiOperation(value = "This API will update Toy in basket")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = ToyDetailsResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = Error.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
            @ApiResponse(code = 403, message = "Forbidden", response = Error.class),
            @ApiResponse(code = 405, message = "Method not allowed", response = Error.class),
            @ApiResponse(code = 404, message = "Not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class) })
    @PutMapping(value = "/update-toy", produces = "application/hal+json")
    public ToyDetailsEntity updateToy(@RequestBody ToyDetailsEntity toyDetailsEntity){
        return toyDetailsService.updateToy(toyDetailsEntity);
    }




}
