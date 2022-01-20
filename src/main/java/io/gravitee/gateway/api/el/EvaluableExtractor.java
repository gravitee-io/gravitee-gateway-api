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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONStyle;

/**
 * @author Guillaume CUSNIEUX (guillaume.cusnieux at graviteesource.com)
 * @author GraviteeSource Team
 */
public class EvaluableExtractor {

    public static void main(String[] args) throws IOException {
        new EvaluableExtractor().run();
    }

    public void run() throws IOException {
        Map<String, Object> all = new TreeMap<>();

        Map<String, TreeMap<String, Object>> request = new TreeMap<>();
        for (Method declaredMethod : EvaluableRequest.class.getDeclaredMethods()) {
            if (declaredMethod.getName().startsWith("get")) {
                String name = Introspector.decapitalize(declaredMethod.getName().replace("get", ""));
                if (!name.equals("ssl")) {
                    request.put(name, getChild(declaredMethod));
                } else {
                    TreeMap<String, Object> ssl = new TreeMap<>();
                    for (Method declaredSSLMethod : EvaluableSSLSession.class.getDeclaredMethods()) {
                        if (declaredSSLMethod.getName().startsWith("get")) {
                            String sslAttributeName = Introspector.decapitalize(declaredSSLMethod.getName().replace("get", ""));
                            if (!sslAttributeName.equals("client") && !sslAttributeName.equals("server")) {
                                ssl.put(sslAttributeName, getChild(declaredSSLMethod));
                            } else {
                                TreeMap<String, Object> principal = new TreeMap<>();
                                for (Method declaredSSLPrincipalMethod : EvaluableSSLPrincipal.class.getDeclaredMethods()) {
                                    if (declaredSSLPrincipalMethod.getName().startsWith("get")) {
                                        String principalAttributeName = Introspector.decapitalize(
                                            declaredSSLPrincipalMethod.getName().replace("get", "")
                                        );
                                        principal.put(principalAttributeName, getChild(declaredSSLPrincipalMethod));
                                    } else if (declaredSSLPrincipalMethod.getName().startsWith("is")) {
                                        String principalAttributeName = Introspector.decapitalize(
                                            declaredSSLPrincipalMethod.getName().replace("is", "")
                                        );
                                        principal.put(principalAttributeName, getChild(declaredSSLPrincipalMethod));
                                    }
                                }
                                ssl.put(sslAttributeName, principal);
                            }
                        }
                    }

                    request.put(name, ssl);
                }
            }
        }
        all.put("request", request);

        Map<String, TreeMap<String, Object>> response = new HashMap<>();
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

    private TreeMap<String, Object> getChild(Method declaredMethod) {
        Class<?> returnType = declaredMethod.getReturnType();
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("_type", returnType.getSimpleName());
        return map;
    }
}
