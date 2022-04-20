package com.xti.jenkins.plugin.awslambda.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.GetFunctionConfigurationRequest;
import com.amazonaws.services.lambda.model.GetFunctionRequest;
import com.amazonaws.waiters.Waiter;
import com.amazonaws.waiters.WaiterParameters;

import java.util.Date;

public class WaitersHelper {
    private final JenkinsLogger logger;
    private final AWSLambdaClient client;

    private static final String FUNCTION_EXISTS = "Function Exists";
    private static final String FUNCTION_ACTIVE = "Function Active";
    private static final String FUNCTION_UPDATED = "Function Updated";

    public WaitersHelper(AWSLambdaClient client, JenkinsLogger logger) {
        this.client = client;
        this.logger = logger;
    }

    public void waitForFunctionToBecomeUsable(String functionName) {
        waitForFunctionToExist(functionName);
        waitForFunctionToBeActive(functionName);
        waitForFunctionUpdateToBeSuccessful(functionName);
    }

    private void waitForFunctionToExist(String functionName) {
        GetFunctionRequest getFunctionRequest = new GetFunctionRequest();
        getFunctionRequest.withFunctionName(functionName);
        WaiterParameters<GetFunctionRequest> waiterParameters = new WaiterParameters<GetFunctionRequest>(getFunctionRequest);
        Waiter<GetFunctionRequest> waiter = client.waiters().functionExists();
        runWaiter(waiter, FUNCTION_EXISTS, waiterParameters);
    }

    private void waitForFunctionToBeActive(String functionName) {
        GetFunctionConfigurationRequest getFunctionConfigurationRequest = new GetFunctionConfigurationRequest();
        getFunctionConfigurationRequest.withFunctionName(functionName);
        WaiterParameters<GetFunctionConfigurationRequest> waiterParameters = new WaiterParameters<GetFunctionConfigurationRequest>(getFunctionConfigurationRequest);
        Waiter<GetFunctionConfigurationRequest> waiter = client.waiters().functionActive();
        runWaiter(waiter, FUNCTION_ACTIVE, waiterParameters);
    }

    private void waitForFunctionUpdateToBeSuccessful(String functionName) {
        GetFunctionConfigurationRequest getFunctionConfigurationRequest = new GetFunctionConfigurationRequest();
        getFunctionConfigurationRequest.withFunctionName(functionName);
        WaiterParameters<GetFunctionConfigurationRequest> waiterParameters = new WaiterParameters<GetFunctionConfigurationRequest>(getFunctionConfigurationRequest);
        Waiter<GetFunctionConfigurationRequest> waiter = client.waiters().functionUpdated();
        runWaiter(waiter, FUNCTION_UPDATED, waiterParameters);
    }

    private void runWaiter(Waiter waiter, String waiterDescription, WaiterParameters waiterParameters) {
        logger.log(String.format("%s waiter started %s", waiterDescription, new Date()));
        try {
            waiter.run(waiterParameters);
            logger.log(String.format("%s waiter done %s", waiterDescription, new Date()));
        } catch (SdkClientException e) {
            throw new RuntimeException(String.format("%s waiter failed %s", waiterDescription, new Date()), e);
        }
    }
}
