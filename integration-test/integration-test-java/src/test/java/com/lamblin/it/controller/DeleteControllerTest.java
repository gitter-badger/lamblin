package com.lamblin.it.controller;

import com.google.common.collect.ImmutableSet;

import com.lamblin.it.controller.client.DeleteControllerClient;
import com.lamblin.test.config.LamblinTestConfig;
import com.lamblin.test.config.annotation.LamblinTestRunnerConfig;
import com.lamblin.test.junit4.JUnit4LamblinTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import static com.lamblin.it.model.EndpointsKt.MULTIPLE_PATH_PARAM_DELETE_ENDPOINT;
import static com.lamblin.it.model.EndpointsKt.QUERY_PARAM_DELETE_ENDPOINT;
import static com.lamblin.it.model.EndpointsKt.SIMPLE_DELETE_ENDPOINT;
import static com.lamblin.it.model.EndpointsKt.SINGLE_PATH_PARAM_DELETE_ENDPOINT;
import static com.lamblin.it.model.TestUtilsKt.runRequestAndVerifyResponse;
import static java.text.MessageFormat.format;

@RunWith(JUnit4LamblinTestRunner.class)
@LamblinTestRunnerConfig(testConfigClass = DeleteControllerTest.TestConfiguration.class)
public class DeleteControllerTest {

    private static final DeleteControllerClient client = DeleteControllerClient.INSTANCE;

    @Test
    public void shouldHandleDeleteRequestsWithNoParams() {
        runRequestAndVerifyResponse(
                client::callSimpleDeleteNoParamsEndpoint,
                SIMPLE_DELETE_ENDPOINT);
    }

    @Test
    public void shouldHandleDeleteRequestsWithSingleQueryParam() {
        String queryParamValue = "value";

        runRequestAndVerifyResponse(
                () -> client.callSingleQueryParamEndpoint(queryParamValue),
                format(
                        "{0}-{1}",
                        QUERY_PARAM_DELETE_ENDPOINT,
                        queryParamValue));
    }

    @Test
    public void shouldHandleDeleteRequestsWithMultipleQueryParams() {
        String queryParam1Value = "value1";
        String queryParam2Value = "value2";

        runRequestAndVerifyResponse(
                () -> client.callMultiQueryParamEndpoint(queryParam1Value, queryParam2Value),
                format(
                        "{0}-{1},{2}",
                        QUERY_PARAM_DELETE_ENDPOINT,
                        queryParam1Value,
                        queryParam2Value));
    }

    @Test
    public void shouldHandleDeleteRequestsWithSinglePathParam() {
        String pathParamValue = "value";

        runRequestAndVerifyResponse(
                () -> client.callSinglePathParamEndpoint(pathParamValue),
                format(
                        "{0}-{1}",
                        SINGLE_PATH_PARAM_DELETE_ENDPOINT,
                        pathParamValue));
    }

    @Test
    public void shouldHandleDeleteRequestsWithMultiplePathParams() {
        String pathParamValue1 = "value1";
        String pathParamValue2 = "value2";

        runRequestAndVerifyResponse(
                () -> client.callMultiPathParamEndpoint(pathParamValue1, pathParamValue2),
                format(
                        "{0}-{1},{2}",
                        MULTIPLE_PATH_PARAM_DELETE_ENDPOINT,
                        pathParamValue1,
                        pathParamValue2));
    }

    @Test
    public void shouldHandleDeleteRequestsWithMultiplePathParamsAndQueryParams() {
        String queryParamValue = "queryParamValue";
        String pathParamValue1 = "value1";
        String pathParamValue2 = "value2";

        runRequestAndVerifyResponse(
                () -> client.callMultiPathParamWithQueryParamEndpoint(queryParamValue, pathParamValue1, pathParamValue2),
                format(
                        "{0}-{1},{2},{3}",
                        MULTIPLE_PATH_PARAM_DELETE_ENDPOINT,
                        queryParamValue,
                        pathParamValue1,
                        pathParamValue2));
    }

    public static class TestConfiguration implements LamblinTestConfig {

        @Override
        public Set<Object> controllers() {
            return ImmutableSet.of(new DeleteController());
        }
    }

}
