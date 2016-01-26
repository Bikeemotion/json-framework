/**
 * Copyright (C) Bikeemotion
 * 2014
 *
 * The reproduction, transmission or use of this document or its contents is not
 * permitted without express written authorization. All rights, including rights
 * created by patent grant or registration of a utility model or design, are
 * reserved. Modifications made to this document are restricted to authorized
 * personnel only. Technical specifications and features are binding only when
 * specifically and expressly agreed upon in a written contract.
 */
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bikeemotion.json;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JSONObjectSerializer extends JsonSerializer<JSONObject> {

  @Override
  public void serialize(JSONObject value, JsonGenerator jsonGenerator,
      SerializerProvider provider) throws IOException {
    jsonGenerator.writeStartObject();
    constructObject(value, jsonGenerator);
    jsonGenerator.writeEndObject();
  }

  private void constructObject(JSONObject object, JsonGenerator jsonGenerator)
      throws IOException {
    for (String key : JSONObject.getNames(object)) {
      Object value = object.get(key);

      if (value instanceof JSONObject) {
        jsonGenerator.writeFieldName(key);
        jsonGenerator.writeStartObject();
        constructObject(object.getJSONObject(key), jsonGenerator);
        jsonGenerator.writeEndObject();
      } else if (value instanceof JSONArray) {
        JSONArray array = object.getJSONArray(key);

        jsonGenerator.writeFieldName(key);
        jsonGenerator.writeStartArray();
        for (int i = 0; i < array.length(); i++) {
          jsonGenerator.writeStartObject();
          constructObject(array.getJSONObject(i), jsonGenerator);
          jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
      } else {
        if (value instanceof Integer) {
          jsonGenerator.writeNumberField(key, (Integer) value);
        } else if (value instanceof Double) {
          jsonGenerator.writeNumberField(key, (Double) value);
        } else if (value instanceof Long) {
          jsonGenerator.writeNumberField(key, (Long) value);
        } else {
          jsonGenerator.writeStringField(key, value.toString());
        }
      }
    }
  }

}
