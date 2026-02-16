package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import java.util.Map;

public class ApiClient {

    protected APIRequestContext context;

    public ApiClient(APIRequestContext context) {
        this.context = context;
    }

    public APIResponse get(String endpoint) {
        return context.get(endpoint);
    }
    public APIResponse get(String endpoint, Map<String, String> params) {
        RequestOptions options = RequestOptions.create();
        params.forEach(options::setQueryParam);
        return context.get(endpoint, options);
    }

    public APIResponse delete(String endpoint) {
        return context.delete(endpoint);
    }

    public APIResponse post(String endpoint, Map body) {
        return context.post(endpoint,
                RequestOptions.create().setData(body));
    }
    public APIResponse post(String endpoint, Object body) {
        return context.post(endpoint,
                RequestOptions.create().setData(body));
    }
    public APIResponse post(String endpoint, String body) {
        return context.post(endpoint,
                RequestOptions.create().setData(body));
    }

    public APIResponse put(String endpoint, Map body) {
        return context.put(endpoint,
                RequestOptions.create().setData(body));
    }
    public APIResponse put(String endpoint, Object body) {
        return context.put(endpoint,
                RequestOptions.create().setData(body));
    }
}

