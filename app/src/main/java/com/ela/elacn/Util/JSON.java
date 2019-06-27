package com.ela.elacn.Util;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSON {


    private static final String LOG_TAG = "JSON";

    private static JsonMapper mapper = new JsonMapper();

    public static String toJson(Object object) {
        return mapper.toJson(object);
    }

    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        return mapper.fromJson(jsonString, clazz);
    }

    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        JavaType jt = mapper.createCollectionType(List.class, clazz);
        return mapper.fromJson(jsonString, jt);
    }

    public static <T> Set<T> parseSet(String jsonString, Class<T> clazz) {
        JavaType jt = mapper.createCollectionType(Set.class, clazz);
        return mapper.fromJson(jsonString, jt);
    }

    public static <K, V> Map<K, V> parseMap(String jsonString, Class<K> keyclz, Class<V> valclz) {
        JavaType jt = mapper.createCollectionType(Map.class, keyclz, valclz);
        return mapper.fromJson(jsonString, jt);
    }

    public static <T> T from (String json, JavaType jt) {
        return mapper.fromJson(json, jt);
    }

    public static JavaType type (Class<?> clazz1, Class<?> clazz2) {
        return mapper.getMapper().getTypeFactory().constructParametricType(clazz1, clazz2);
    }

    public static JavaType type (Class<?> clazz1, Class<?> clazz2, Class<?> clazz3) {
        return mapper.getMapper().getTypeFactory().constructParametricType(clazz1, clazz2, clazz3);
    }

    public static JavaType type (Class<?> clazz1, JavaType clazz2) {
        return mapper.getMapper().getTypeFactory().constructParametricType(clazz1, clazz2);
    }

    public static enum FilterBy{
        OUTEXCEPT,
        EXCEPT
    }

    public static ObjectWriter filterWriter(Class<?> target, Class<?> mixinSource, String filterName, FilterBy filterBy, String... properties){
        ObjectMapper mapper = new ObjectMapper().addMixIn(target, mixinSource);
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = filterBy == null ? null : (filterBy == FilterBy.OUTEXCEPT ? SimpleBeanPropertyFilter.filterOutAllExcept(properties) : SimpleBeanPropertyFilter.serializeAllExcept(properties));
        return mapper.writer(new SimpleFilterProvider().addFilter(filterName, simpleBeanPropertyFilter));
    }

    public static class JsonMapper {


        private ObjectMapper mapper;

        public JsonMapper() {
            this(null);
        }

        public JsonMapper(JsonInclude.Include include) {
            mapper = new ObjectMapper();
            if (include != null) {
                mapper.setSerializationInclusion(include);
            }

            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        }

        public static JsonMapper nonEmptyMapper() {
            return new JsonMapper(JsonInclude.Include.NON_EMPTY);
        }

        public static JsonMapper nonNullMapper() {
            return new JsonMapper(JsonInclude.Include.NON_NULL);
        }

        public static JsonMapper nonDefaultMapper() {
            return new JsonMapper(JsonInclude.Include.NON_DEFAULT);
        }

        public String toJson(Object object) {

            try {
                return mapper.writeValueAsString(object);
            } catch (IOException e) {
                Log.w(LOG_TAG, "write to json string error:" + object, e);
                return null;
            }
        }

        public <T> T fromJson(String jsonString, Class<T> clazz) {
            if (jsonString == null || jsonString.length() <= 0) {
                return null;
            }

            try {
                return mapper.readValue(jsonString, clazz);
            } catch (IOException e) {
                Log.w(LOG_TAG, "parse json string error:" + jsonString, e);
                return null;
            }
        }

        @SuppressWarnings("unchecked")
        public <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
            if (jsonString == null || jsonString.length() <= 0) {
                return null;
            }

            try {
                return (T) mapper.readValue(jsonString, typeReference);
            } catch (IOException e) {
                Log.w(LOG_TAG, "parse Collection<Bean> json string error:" + jsonString, e);
                return null;
            }
        }

        @SuppressWarnings("unchecked")
        public <T> T fromJson(String jsonString, JavaType javaType) {
            if (jsonString == null || jsonString.length() <= 0) {
                return null;
            }

            try {
                return (T) mapper.readValue(jsonString, javaType);
            } catch (IOException e) {
                Log.w(LOG_TAG, "parse javaType json string error:" + jsonString, e);
                return null;
            }
        }

        public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
            return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        }

        @SuppressWarnings("unchecked")
        public <T> T update(String jsonString, T object) {
            try {
                return (T) mapper.readerForUpdating(object).readValue(jsonString);
            } catch (JsonProcessingException e) {
                Log.w(LOG_TAG, "update json string:" + jsonString + " to object:" + object + " error.", e);
            } catch (IOException e) {
                Log.w(LOG_TAG, "update json string:" + jsonString + " to object:" + object + " error.", e);
            }
            return null;
        }

        public String toJsonP(String functionName, Object object) {
            return toJson(new JSONPObject(functionName, object));
        }

        public void enableEnumUseToString() {
            mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
            mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
        }

        public ObjectMapper getMapper() {
            return mapper;
        }
    }

}
