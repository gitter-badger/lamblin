package it.kotlin.controller

import com.lamblin.core.model.HttpMethod
import com.lamblin.core.model.HttpResponse
import com.lamblin.core.model.annotation.Endpoint
import com.lamblin.core.model.annotation.PathParam
import com.lamblin.core.model.annotation.QueryParam
import com.lamblin.core.model.annotation.RequestBody
import it.ExampleRequestBody
import it.MULTIPLE_PATH_PARAM_PATH_PUT_ENDPOINT
import it.PATH_PARAM_1
import it.PATH_PARAM_2
import it.QUERY_PARAM_1
import it.QUERY_PARAM_2
import it.QUERY_PARAM_PARAM_PUT_ENDPOINT
import it.ResponseEntity
import it.SIMPLE_PUT_ENDPOINT
import it.SIMPLE_REQUEST_BODY_PUT_ENDPOINT
import it.SINGLE_PATH_PARAM_PATH_PUT_ENDPOINT

class PutController {

    @Endpoint(SIMPLE_PUT_ENDPOINT, method = HttpMethod.PUT)
    fun simplePostNoParams(): HttpResponse<ResponseEntity> {
        return HttpResponse.ok(ResponseEntity(SIMPLE_PUT_ENDPOINT))
    }

    @Endpoint(QUERY_PARAM_PARAM_PUT_ENDPOINT, method = HttpMethod.PUT)
    fun singleQueryParamTest(@QueryParam(QUERY_PARAM_1) queryParam: String): HttpResponse<ResponseEntity> {
        return HttpResponse.ok(ResponseEntity("$QUERY_PARAM_PARAM_PUT_ENDPOINT-$queryParam"))
    }

    @Endpoint(QUERY_PARAM_PARAM_PUT_ENDPOINT, method = HttpMethod.PUT)
    fun multipleQueryParamTest(
            @QueryParam(QUERY_PARAM_1) queryParam1: String,
            @QueryParam(QUERY_PARAM_2) queryParam2: String): HttpResponse<ResponseEntity> {

        return HttpResponse.ok(
                ResponseEntity("$QUERY_PARAM_PARAM_PUT_ENDPOINT-$queryParam1,$queryParam2"))
    }

    @Endpoint(SINGLE_PATH_PARAM_PATH_PUT_ENDPOINT, method = HttpMethod.PUT)
    fun singlePathParamPath(
            @PathParam(PATH_PARAM_1) pathParam: String): HttpResponse<ResponseEntity> {

        return HttpResponse.ok(ResponseEntity("$SINGLE_PATH_PARAM_PATH_PUT_ENDPOINT-$pathParam"))
    }

    @Endpoint(SIMPLE_REQUEST_BODY_PUT_ENDPOINT, method = HttpMethod.PUT)
    fun requestBody(
            @RequestBody exampleRequestBody: ExampleRequestBody): HttpResponse<ResponseEntity> {

        return HttpResponse.ok(
                ResponseEntity("$SIMPLE_REQUEST_BODY_PUT_ENDPOINT-${exampleRequestBody.body}"))
    }

    @Endpoint(MULTIPLE_PATH_PARAM_PATH_PUT_ENDPOINT, method = HttpMethod.PUT)
    fun multiplePathParamPath(
            @PathParam(PATH_PARAM_1) pathParamOne: String,
            @PathParam(PATH_PARAM_2) pathParamTwo: String): HttpResponse<ResponseEntity> {

        return HttpResponse.ok(
                ResponseEntity("$MULTIPLE_PATH_PARAM_PATH_PUT_ENDPOINT-$pathParamOne,$pathParamTwo"))
    }

    @Endpoint(MULTIPLE_PATH_PARAM_PATH_PUT_ENDPOINT, method = HttpMethod.PUT)
    fun multiplePathParamWithQueryParamsPath(
            @QueryParam(QUERY_PARAM_1) queryParam: String,
            @PathParam(PATH_PARAM_1) pathParamOne: String,
            @PathParam(PATH_PARAM_2) pathParamTwo: String): HttpResponse<ResponseEntity> {

        return HttpResponse.ok(ResponseEntity(
                "$MULTIPLE_PATH_PARAM_PATH_PUT_ENDPOINT-$queryParam,$pathParamOne,$pathParamTwo"))
    }

}