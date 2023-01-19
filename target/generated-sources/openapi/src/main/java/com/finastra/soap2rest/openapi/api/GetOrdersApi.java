/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.finastra.soap2rest.openapi.api;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finastra.soap2rest.wsdl.GetOrders;
import com.finastra.soap2rest.wsdl.GetOrdersPortType;
import com.finastra.soap2rest.wsdl.GetOrdersResponse;
import com.finastra.soap2rest.wsdl.OrdersService;

import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-01-19T14:46:23.052+05:30[Asia/Calcutta]")
@Validated
@Api(value = "GetOrders", description = "the GetOrders API")
public interface GetOrdersApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /GetOrders : GetOrders
     * GetOrders
     *
     * @param getOrders  (optional)
     * @return GetOrdersResponse (status code 200)
     */
    @ApiOperation(value = "GetOrders", nickname = "getOrders", notes = "GetOrders", response = Object.class, tags={ "OrdersService", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "GetOrdersResponse", response = Object.class) })
    @PostMapping(
        value = "/GetOrders",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    default ResponseEntity<com.finastra.soap2rest.openapi.model.GetOrdersResponse> getOrders(@ApiParam(value = ""  )  @Valid @RequestBody(required = false) Object getOrders) {
    	
    	OrdersService os = new OrdersService();
    	GetOrdersPortType go = os.getGetOrdersPort();
    	
    	GetOrders gos = new GetOrders();
    	gos.setCriteria("xyz");
    	
    	GetOrdersResponse res = go.getOrders(gos);
    	ObjectMapper om = new ObjectMapper();
    	com.finastra.soap2rest.openapi.model.GetOrdersResponse response  = om.convertValue(om, com.finastra.soap2rest.openapi.model.GetOrdersResponse.class);
    	
        return new ResponseEntity<com.finastra.soap2rest.openapi.model.GetOrdersResponse>(response,HttpStatus.OK);

    }

}