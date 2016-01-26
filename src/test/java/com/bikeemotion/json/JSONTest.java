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
package com.bikeemotion.json;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import com.bikeemotion.common.exception.BusinessException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;

/**
 * Tests for {@link JSON}.
 */
public class JSONTest {

  @Test
  public void test_drop_values_whose_fields_are_not_in_schema()
      throws BusinessException {
    final String expectedString = ""//
        + "{  \n"//
        + "   \"id\":1,"//
        + "   \"num1\":{  \n"//
        + "      \"type\":\"number\",\n"//
        + "      \"value\":111,\n"//
        + "      \"mandatory\":false\n"//
        + "   },\n"//
        + "   \"string1\":{  \n"//
        + "      \"type\":\"sstring\",\n"//
        + "      \"value\":\"\",\n"//
        + "      \"mandatory\":false\n"//
        + "   }\n"//
        + "}";

    final JSON expectedObject = new JSON(expectedString);

    final String schema = ""//
        + "{  \n"//
        + "   \"id\":0,"//
        + "   \"num1\":{  \n"//
        + "      \"type\":\"number\",\n"//
        + "      \"value\":0,\n"//
        + "      \"mandatory\":false\n"//
        + "   },\n"//
        + "   \"string1\":{  \n"//
        + "      \"type\":\"sstring\",\n"//
        + "      \"value\":\"\",\n"//
        + "      \"mandatory\":false\n"//
        + "   }\n"//
        + "}";

    final String values = ""//
        + "{  \n"//
        + "   \"id\":1,"//
        + "   \"num1\":{  \n"//
        + "      \"type\":\"number\",\n"//
        + "      \"value\":111,\n"//
        + "      \"foo\":111,\n"//
        + "      \"mandatory\":false\n"//
        + "   },\n"//
        + "   \"num2\":{  \n"//
        + "      \"type\":\"number\",\n"//
        + "      \"value\":222,\n"//
        + "      \"mandatory\":false\n"//
        + "   }\n"//
        + "}";

    final JSON object = new JSON(schema, values);

    assertEquals(object.getValue().toString(), expectedObject.getValue()
        .toString());
  }

  @Test
  public void test_add_schema_field_with_non_existing_value()
      throws BusinessException {
    final String expectedString = ""//
        + "{  \n"//
        + "   \"id\":1,"//
        + "   \"num1\":{  \n"//
        + "      \"type\":\"number\",\n"//
        + "      \"value\":111,\n"//
        + "      \"mandatory\":false\n"//
        + "   },\n"//
        + "   \"string1\":{  \n"//
        + "      \"type\":\"sstring\",\n"//
        + "      \"value\":\"\",\n"//
        + "      \"mandatory\":false\n"//
        + "   }\n"//
        + "}";

    final JSON expectedObject = new JSON(expectedString);

    final String schema = ""//
        + "{  \n"//
        + "   \"id\":0,"//
        + "   \"num1\":{  \n"//
        + "      \"type\":\"number\",\n"//
        + "      \"value\":0,\n"//
        + "      \"mandatory\":false\n"//
        + "   },\n"//
        + "   \"string1\":{  \n"//
        + "      \"type\":\"sstring\",\n"//
        + "      \"value\":\"\",\n"//
        + "      \"mandatory\":false\n"//
        + "   }\n"//
        + "}";

    final String values = "" + //
        "{  \n"//
        + "   \"id\":1,"//
        + "   \"num1\":{  \n"//
        + "      \"value\":111,\n"//
        + "      \"foo\":111,\n"//
        + "      \"mandatory\":false\n"//
        + "   }\n"//
        + "}";

    final JSON object = new JSON(schema, values);

    assertEquals(object.getValue().toString(), expectedObject.getValue()
        .toString());
  }

  @Test
  public void test_enforce_nested_object_with_an_array_schema()
      throws BusinessException {
    final String schema = "{\n" + "  \"id\":0,  \n" + "  \"price2\":{\n"
        + "     \"type\":\"number\",\n" + "     \"value\":0,\n"
        + "     \"unique\":{\n" + "        \"value\":\"true\",\n"
        + "        \"mode\":\"distinct\",\n" + "        \"scope\":[\n"
        + "           {\n" + "              \"field\":\"a\"\n"
        + "           },\n" + "           {\n"
        + "              \"field\":\"b\"\n" + "           }\n" + "        ]\n"
        + "     }\n" + "  }\n" + "}";

    final JSON expectedObject = new JSON("{\n" + "  \"id\":1,  \n"
        + "  \"price2\":{\n" + "     \"type\":\"number\",\n"
        + "     \"value\":1,\n" + "     \"unique\":{\n"
        + "        \"value\":\"true\",\n" + "        \"mode\":\"distinct\",\n"
        + "        \"scope\":[\n" + "           {\n"
        + "              \"field\":\"a\"\n" + "           },\n"
        + "           {\n" + "              \"field\":\"b\"\n"
        + "           }\n" + "        ]\n" + "     }\n" + "  }\n" + "}");

    final String values = "{\n" + "  \"id\":1,  \n" + "  \"price2\":{\n"
        + "     \"type\":\"number\",\n" + "     \"value\":1,\n"
        + "     \"unique\":{\n" + "        \"value\":\"true\",\n"
        + "        \"mode\":\"distinct\"\n" + "     }\n" + "  }\n" + "}";

    final JSON object = new JSON(schema, values);

    assertEquals(object.getValue().toString(), expectedObject.getValue()
        .toString());
  }

  @Test
  public void test_serialization() throws IOException, BusinessException {
    final String values = "{\"num1\":{\"type\":\"number\", \"value\":111, \"mandatory\":\"false\", \"xxx\":[{\"a\":1}, {\"b\":2}] }}";

    final JSON expectedObject = new JSON(values);

    final ObjectMapper mapper = new ObjectMapper();

    final JSON resultObject = mapper.readValue(
        mapper.writeValueAsString(expectedObject), JSON.class);

    assertEquals(resultObject.toString(), expectedObject.toString());
  }

  @Test(expectedExceptions = Exceptions.NumberPrecisionExpectationFailedException.class)
  public void test_fail_double_number_precision() throws BusinessException {
    final JSONObject schema = new JSONObject("{\n" + "   \"id\":0,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99.99,\n"
        + "      \"min\":0,\n" + "      \"value\":0\n" + "   }\n" + "}");

    final JSONObject values = new JSONObject("{\n" + "   \"id\":1,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99.99,\n"
        + "      \"min\":0,\n" + "      \"value\":1.111\n" + "   }\n" + "}");

    final JSON object = new JSON(schema, values);
  }

  @Test()
  public void test_enforce_double_number_precision() throws BusinessException {
    final JSONObject schema = new JSONObject("{\n"
        + "                \"id\": 0,\n" + "                \"price\": {\n"
        + "                    \"min\": 0,\n"
        + "                    \"max\": 9999.99,\n"
        + "                    \"mandatory\": \"true\",\n"
        + "                    \"value\": 0,\n"
        + "                    \"type\": \"number\"\n" + "                }\n"
        + "            }");

    final JSONObject values = new JSONObject("{\n"
        + "                    \"id\": 1,\n"
        + "                    \"price\": {\n"
        + "                        \"min\": 0,\n"
        + "                        \"max\": 9999.99,\n"
        + "                        \"mandatory\": \"true\",\n"
        + "                        \"value\": 0,\n"
        + "                        \"type\": \"number\"\n"
        + "                    }\n" + "                }");

    final JSON object = new JSON(schema, values);
  }

  @Test(expectedExceptions = Exceptions.NumberPrecisionExpectationFailedException.class)
  public void test_fail_integer_number_precision() throws BusinessException {
    final JSONObject schema = new JSONObject("{\n" + "   \"id\":0,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99,\n"
        + "      \"min\":0,\n" + "      \"value\":0\n" + "   }\n" + "}");

    final JSONObject values = new JSONObject("{\n" + "   \"id\":1,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99,\n"
        + "      \"min\":0,\n" + "      \"value\":1.1\n" + "   }\n" + "}");

    final JSON object = new JSON(schema, values);
  }

  @Test
  public void test_enforce_integer_number_precision() throws BusinessException {
    final JSONObject schema = new JSONObject("{\n" + "   \"id\":0,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99,\n"
        + "      \"min\":0,\n" + "      \"value\":0\n" + "   }\n" + "}");

    final JSONObject values = new JSONObject("{\n" + "   \"id\":1,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99,\n"
        + "      \"min\":0,\n" + "      \"value\":1\n" + "   }\n" + "}");

    final JSON object = new JSON(schema, values);
  }
  
  
  @Test
  public void test_decimal_with_big_precision() throws BusinessException {
    
    BigDecimal teste = new BigDecimal("1.0E-5");
    
    final JSONObject schema = new JSONObject("{\n" + "   \"id\":0,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99,\n"
        + "      \"min\":0.00000001,\n" + "      \"value\":0\n" + "   }\n" + "}");

    final JSONObject values = new JSONObject("{\n" + "   \"id\":0,\n"
        + "   \"minAge\":{\n" + "      \"mandatory\":\"true\",\n"
        + "      \"type\":\"number\",\n" + "      \"max\":99,\n"
        + "      \"min\":0.00000001,\n" + "      \"value\":0.0000001\n" + "   }\n" + "}");

    final JSON object = new JSON(schema, values);
  }

}
