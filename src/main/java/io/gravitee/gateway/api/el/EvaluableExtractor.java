/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.api.el;

import io.gravitee.common.http.HttpHeaders;
import java.beans.Introspector;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;
import org.springframework.util.MultiValueMap;

/**
 * @author Guillaume CUSNIEUX (guillaume.cusnieux at graviteesource.com)
 * @author GraviteeSource Team
 */
public class EvaluableExtractor {

    public static void main(String[] args) throws IOException {
        new EvaluableExtractor().run();
    }

    public void run() throws IOException {
        Map<String, Object> all = new HashMap<>();

        Map<String, HashMap<String, Object>> request = new HashMap<>();
        for (Method declaredMethod : EvaluableRequest.class.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("get")) {
                String name = Introspector.decapitalize(declaredMethod.getName().replace("get", ""));
                request.put(name, getChild(declaredMethod));
            }
        }
        all.put("request", request);

        Map<String, HashMap<String, Object>> response = new HashMap<>();
        for (Method declaredMethod : EvaluableResponse.class.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("get")) {
                String name = Introspector.decapitalize(declaredMethod.getName().replace("get", ""));
                response.put(name, getChild(declaredMethod));
            }
        }
        all.put("response", response);

        Map<String, Object> arrayOfStringType = new HashMap<>();
        arrayOfStringType.put("_type", String[].class.getSimpleName());
        all.put("properties", arrayOfStringType);
        all.put("dictionaries", arrayOfStringType);
        all.put("endpoints", arrayOfStringType);

        Map<String, Object> context = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();
        String[] attrs = new String[] { "context-path", "resolved-path", "application", "api", "user-id", "plan", "api-key" };
        for (String attr : attrs) {
            Map m = new HashMap();
            m.put("_type", String.class.getSimpleName());
            attributes.put(attr, m);
        }
        context.put("attributes", attributes);
        all.put("context", context);
        all.put("_enums", buildEnums());

        JSONObject o = new JSONObject(all);
        BufferedWriter writer = new BufferedWriter(new FileWriter("/tmp/grammar.json"));
        writer.write(o.toJSONString(JSONStyle.NO_COMPRESS));
        writer.close();
    }

    private Object buildEnums() {
        Map<String, Object> _enums = new HashMap<>();

        Field[] declaredFields = HttpHeaders.class.getDeclaredFields();
        _enums.put(
            HttpHeaders.class.getSimpleName(),
            Arrays
                .stream(declaredFields)
                .filter(f -> Modifier.isPublic(f.getModifiers()))
                .map(field -> {
                    try {
                        return field.get(null);
                    } catch (IllegalAccessException e) {
                        return field.getName();
                    }
                })
                .collect(Collectors.toList())
        );

        return _enums;
    }

    private HashMap<String, Object> getChild(Method declaredMethod) {
        Class<?> returnType = declaredMethod.getReturnType();
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", returnType.getSimpleName());
        return map;
    }
}
