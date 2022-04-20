package com.xti.jenkins.plugin.awslambda.util;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.GetFunctionConfigurationRequest;
import com.amazonaws.waiters.Waiter;
import com.amazonaws.waiters.WaiterParameters;
import com.xti.jenkins.plugin.awslambda.service.JenkinsLogger;

import java.util.Date;

public class WaitersHelper {
    private final JenkinsLogger logger;
    private final AWSLambdaClient client;

    private static final String FUNCTION_ACTIVE = "Function Active";
    private static final String FUNCTION_UPDATED = "Function Updated";

    public WaitersHelper(AWSLambdaClient client, JenkinsLogger logger) {
        this.client = client;
        this.logger = logger;
    }

    public void waitForFunctionToBecomeUsable(String functionName) {
        WaiterParameters<GetFunctionConfigurationRequest> waiterParameters = new WaiterParameters<>(
                new GetFunctionConfigurationRequest()
                        .withFunctionName(functionName)
        );

        runWaiter(client.waiters().functionActive(), FUNCTION_ACTIVE, waiterParameters);
        runWaiter(client.waiters().functionUpdated(), FUNCTION_UPDATED, waiterParameters);
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
