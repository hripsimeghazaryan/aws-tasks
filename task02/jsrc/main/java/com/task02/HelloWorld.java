package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
    lambdaName = "hello_world",
	roleName = "hello_world-role",
	isPublishVersion = true,
	aliasName = "${lambdas_alias_name}",
	logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@LambdaUrlConfig {
	authType = AuthType.NONE,
	invokeMode = InvokeMode.BUFFERED,
}
public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> request, Context context) {
        // Extract path and HTTP method from the request
        String path = (String) request.get("path");
        String method = (String) request.get("httpMethod");

        Map<String, Object> response = new HashMap<>();

        if ("/hello".equals(path)) {
            response.put("statusCode", 200);
            response.put("message", "Hello from Lambda");
        } else {
            response.put("statusCode", 400);
            response.put("message", "Bad request syntax or unsupported method. Request path: " + path + ". HTTP method: " + method);
        }

        return response;
    }
}
