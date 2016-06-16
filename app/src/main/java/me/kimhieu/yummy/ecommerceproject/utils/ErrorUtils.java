package me.kimhieu.yummy.ecommerceproject.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;

import me.kimhieu.yummy.ecommerceproject.model.APIErrorsResponse;
import me.kimhieu.yummy.ecommerceproject.service.ServiceGenerator;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;


public class ErrorUtils {
    public static APIErrorsResponse parseError(Response<?> response) {
        Converter<ResponseBody, APIErrorsResponse> converter =
                ServiceGenerator.retrofit()
                        .responseBodyConverter(APIErrorsResponse.class, new Annotation[0]);

        APIErrorsResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIErrorsResponse();
        }

        return error;
    }
}
