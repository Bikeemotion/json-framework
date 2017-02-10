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

import com.bikeemotion.core.exception.BusinessException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests for {@link JSONCollection}.
 */
public class JSONCollectionTest {

  @Test
  public void test_create_JSON_collection_by_string_conforming_to_schema()
    throws BusinessException {

    String atomicSchema = "{\"id\":-1, \"money\":{\"value\":0, \"type\":\"number\"}, \"name\":{\"type\":sstring, \"value\":\"\"}}";

    JSONCollection resultCollection = new JSONCollection(
        atomicSchema,
        "[{\"id\":1, \"money\":{\"value123123\":1, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola1\"}}, {\"id\":2, \"money\":{\"value\":1, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola2\"}}]");

    JSONCollection expectedCollection = new JSONCollection(
        "[{\"id\":1, \"money\":{\"value\":0, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola1\"}}, {\"id\":2, \"money\":{\"value\":1, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola2\"}}]");

    assertEquals(resultCollection.getValue().toString(), expectedCollection
        .getValue().toString());
  }

  @Test
  public void test_create_JSON_collection_by_object_conforming_to_schema()
    throws BusinessException {

    JSONObject atomicSchemaObject = new JSONObject(
        "{\"id\":-1, \"money\":{\"value\":0, \"type\":\"number\"}, \"name\":{\"type\":sstring, \"value\":\"\"}}");

    JSONArray valuesCollectionObject = new JSONArray(
        "[{\"id\":1, \"money\":{\"value123123\":1, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola1\"}}, {\"id\":2, \"money\":{\"value\":1, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola2\"}}]");

    JSONCollection resultCollection = new JSONCollection(atomicSchemaObject,
        valuesCollectionObject);

    JSONCollection expectedCollection = new JSONCollection(
        "[{\"id\":1, \"money\":{\"value\":0, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola1\"}}, {\"id\":2, \"money\":{\"value\":1, \"type\":\"number\"}, \"name\":{\"type\":\"sstring\", \"value\":\"ola2\"}}]");

    assertEquals(resultCollection.getValue().toString(), expectedCollection
        .getValue().toString());
  }

  @Test
  public void test_enforcement_of_datatypes() {
    final String values = "[{\"num1\":{\"type\":\"sstring\",\"value\":\"12345\", \"min\":0, \"max\":5}}, {\"num1\":{\"type\":\"number\",\"value\":1, \"min\":1.0, \"max\":5}}]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e) {
      Assert.fail(e.getStackTrace()[0].toString(), e);
    }
  }

  @Test
  public void test_enforcement_of_mandatory_fields() {
    final String values = "[{\"num1\":{\"type\":\"sstring\",\"value\":\"some stuff\", \"mandatory\":\"true\"}}]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e) {
      Assert.fail(e.getStackTrace()[0].toString(), e);
    }
  }

  @Test
  public void test_enforcement_of_string_min_and_max() {
    String values;

    values = "[{\"num1\":{\"type\":\"sstring\",\"value\":\"12345\", \"min\":5, \"max\":5}}]";
    try {
      new JSONCollection(values);
    } catch (BusinessException e) {

      Assert.fail(e.getStackTrace()[0].toString(), e);
    }
  }

  @Test
  public void test_enforcement_of_string_max_not_satisfied() {
    final String values = "[{\"x\":{\"type\":\"sstring\",\"value\":\"12345\", \"min\":0, \"max\":4}}]";
    try {
      new JSONCollection(values);
    } catch (Exceptions.MaxValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_string_min_not_satisfied() {
    final String values = "[{\"x\":{\"type\":\"sstring\",\"value\":\"12345\", \"min\":6, \"max\":10}}]";
    try {
      new JSONCollection(values);
    } catch (Exceptions.MinValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_int_min_and_max() {
    String values;

    values = "[{\"num1\":{\"type\":\"number\",\"value\":5, \"min\":5, \"max\":5}}]";
    try {
      new JSONCollection(values);
    } catch (BusinessException e) {
      Assert.fail(e.getStackTrace()[0].toString(), e);
    }
  }

  @Test
  public void test_enforcement_of_int_min_not_satisfied() {
    final String values = "[{\"x\":{\"type\":\"number\",\"value\":3, \"min\":4, \"max\":10}}]";
    try {
      new JSONCollection(values);
    } catch (Exceptions.MinValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_int_max_not_satisfied() {
    final String values = "[{\"x\":{\"type\":\"number\",\"value\":3, \"min\":0, \"max\":2}}]";
    try {
      new JSONCollection(values);
    } catch (Exceptions.MaxValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_unique_values_from_property() {
    final String values = "" + "[\n"//
        + "   {\n"//
        + "      \"id\":\"05d9cb4d-fe9c-49cf-b3ad-053ddf7b5322\",\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\"\n"//
        + "         }\n"//
        + "      }\n"//
        + "   },\n"//
        + "   {\n"//
        + "      \"id\":\"7779b143-0caf-40a1-94a5-2e594d64dc80\",\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":885,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\"\n"//
        + "         }\n"//
        + "      }\n"//
        + "   }\n"//
        + "]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
    }
  }

  @Test
  public void test_enforcement_of_unique_values_from_property_not_satisfied() {
    final String values = ""//
        + "[\n"//
        + "   {\n"//
        + "      \"id\":\"3b0d4221-966e-43b1-ba2d-d54044cbf5a4\",\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\"\n"//
        + "         }\n"//
        + "      }\n"//
        + "   },\n"//
        + "   {\n"//
        + "      \"id\":\"19e42862-f671-427f-b25b-34dc6b61fb8c\",\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\"\n"//
        + "         }\n"//
        + "      }\n"//
        + "   }\n"//
        + "]";

    try {
      new JSONCollection(values);
    } catch (Exceptions.UniqueValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_unique_values_from_object_property() {
    final String values = "[\n" +
        "  {\n" +
        "      \"id\": \"258fd859-f2df-4a0e-8321-ca4d96831dd7\",\n" +
        "      \"price\": {\n" +
        "          \"min\": 0,\n" +
        "          \"max\": 9999.99,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": 10,\n" +
        "          \"type\": \"number\"\n" +
        "      },\n" +
        "      \"description\": {\n" +
        "          \"min\": 1,\n" +
        "          \"unique\": {\n" +
        "              \"value\": \"true\"\n" +
        "          },\n" +
        "          \"max\": 128,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": {\n" +
        "              \"pt-pt\": \"Passe\",\n" +
        "              \"en-us\": \"Pass\",\n" +
        "              \"es-es\": \"Passe\"\n" +
        "          },\n" +
        "          \"type\": \"string\"\n" +
        "      }\n" +
        "  },\n" +
        "  {\n" +
        "      \"id\": \"22565b63-da97-4412-9730-1219801d1e54\",\n" +
        "      \"price\": {\n" +
        "          \"min\": 0,\n" +
        "          \"max\": 9999.99,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": 50,\n" +
        "          \"type\": \"number\"\n" +
        "      },\n" +
        "      \"description\": {\n" +
        "          \"min\": 1,\n" +
        "          \"unique\": {\n" +
        "              \"value\": \"true\"\n" +
        "          },\n" +
        "          \"max\": 128,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": {\n" +
        "              \"pt-pt\": \"Chave\",\n" +
        "              \"es-es\": \"Llave\",\n" +
        "              \"en-us\": \"Key\"\n" +
        "          },\n" +
        "          \"type\": \"string\"\n" +
        "      }\n" +
        "  }\n" +
        "]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
  }

  @Test(expectedExceptions = Exceptions.UniqueValueExpectationFailedException.class)
  public void test_enforcement_of_unique_values_from_object_property_not_satisfied()
    throws BusinessException {
    final String values = "[\n" +
        "  {\n" +
        "      \"id\": \"258fd859-f2df-4a0e-8321-ca4d96831dd7\",\n" +
        "      \"price\": {\n" +
        "          \"min\": 0,\n" +
        "          \"max\": 9999.99,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": 10,\n" +
        "          \"type\": \"number\"\n" +
        "      },\n" +
        "      \"description\": {\n" +
        "          \"min\": 1,\n" +
        "          \"unique\": {\n" +
        "              \"value\": \"true\"\n" +
        "          },\n" +
        "          \"max\": 128,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": {\n" +
        "              \"pt-pt\": \"Passe\",\n" +
        "              \"en-us\": \"Pass\",\n" +
        "              \"es-es\": \"Passe\"\n" +
        "          },\n" +
        "          \"type\": \"string\"\n" +
        "      }\n" +
        "  },\n" +
        "  {\n" +
        "      \"id\": \"22565b63-da97-4412-9730-1219801d1e54\",\n" +
        "      \"price\": {\n" +
        "          \"min\": 0,\n" +
        "          \"max\": 9999.99,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": 50,\n" +
        "          \"type\": \"number\"\n" +
        "      },\n" +
        "      \"description\": {\n" +
        "          \"min\": 1,\n" +
        "          \"unique\": {\n" +
        "              \"value\": \"true\"\n" +
        "          },\n" +
        "          \"max\": 128,\n" +
        "          \"mandatory\": \"true\",\n" +
        "          \"value\": {\n" +
        "              \"pt-pt\": \"Passe\",\n" +
        "              \"en-us\": \"Pass\",\n" +
        "              \"es-es\": \"Passe\"\n" +
        "          },\n" +
        "          \"type\": \"string\"\n" +
        "      }\n" +
        "  }\n" +
        "]";

    new JSONCollection(values);

  }

  @Test
  public void test_enforcement_of_unique_object_values_from_property_and_scope_has_at_least_one_distinct_value() {
    final String values = ""
        + "[\n"
        + "   {\n"
        + "      \"id\":\"589ad7f4-4648-43a7-8f80-4eaf27e45816\",\n"
        + "      \"attr2\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe\",\n"
        + "              \"en-us\": \"Pass\",\n"
        + "              \"es-es\": \"Passe\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe\",\n"
        + "              \"en-us\": \"Pass\",\n"
        + "              \"es-es\": \"Passe\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{\n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{\n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"distinct\",\n"
        + "            \"scope\":[\n"
        + "               {\n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {\n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   },\n"
        + "   {\n"
        + "      \"id\":\"d7b55f37-c8e7-4911-8307-6df004220657\",\n"
        + "      \"attr2\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passes\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe\",\n"
        + "              \"en-us\": \"Pass\",\n"
        + "              \"es-es\": \"Passe\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{\n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{\n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"distinct\",\n"
        + "            \"scope\":[\n"
        + "               {\n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {\n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   }\n"
        + "]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
  }

  @Test
  public void test_enforcement_of_unique_object_values_from_property_and_scope_has_at_least_one_distinct_value_not_satisfied() {

    final String values = ""
        + "[\n"
        + "   {\n"
        + "      \"id\":\"d55df211-9c3c-43f8-aca3-d8f0a7c52691\",\n"
        + "      \"attr2\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe\",\n"
        + "              \"en-us\": \"Pass\",\n"
        + "              \"es-es\": \"Passe\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe\",\n"
        + "              \"en-us\": \"Pass\",\n"
        + "              \"es-es\": \"Passe\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{\n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{\n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"distinct\",\n"
        + "            \"scope\":[\n"
        + "               {\n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {\n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   },\n"
        + "   {\n"
        + "      \"id\":\"81fc4e5a-ccc7-4b7f-a3db-22b21d378f0c\",\n"
        + "      \"attr2\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe\",\n"
        + "              \"en-us\": \"Pass\",\n"
        + "              \"es-es\": \"Passe\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe\",\n"
        + "              \"en-us\": \"Pass\",\n"
        + "              \"es-es\": \"Passe\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{\n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{\n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"distinct\",\n"
        + "            \"scope\":[\n"
        + "               {\n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {\n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   }\n"
        + "]";

    try {
      new JSONCollection(values);
    } catch (Exceptions.UniqueValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_unique_object_values_in_all_the_scope_items() {
    final String values = ""
        + "[\n"
        + "   {\n"
        + "      \"id\":\"905d39ff-3062-4032-8905-3ff3025da898\",\n"
        + "      \"attr2\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{\n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{\n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"pkey\",\n"
        + "            \"scope\":[\n"
        + "               {\n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {\n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   },\n"
        + "   {\n"
        + "      \"id\":\"356eb178-6f2a-4827-8b62-f5dd3f25eeff\",\n"
        + "      \"attr2\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe2\",\n"
        + "              \"en-us\": \"Pass2\",\n"
        + "              \"es-es\": \"Passe2\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{\n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{\n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"pkey\",\n"
        + "            \"scope\":[\n"
        + "               {\n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {\n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   },\n"
        + "   {\n"
        + "      \"id\":\"e2ac8e87-5c86-4f16-92f0-5c68a9006efd\",\n"
        + "      \"attr2\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe2\",\n"
        + "              \"en-us\": \"Pass2\",\n"
        + "              \"es-es\": \"Passe2\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{\n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{\n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{\n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"pkey\",\n"
        + "            \"scope\":[\n"
        + "               {\n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {\n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   }"
        + "]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
  }

  @Test
  public void test_enforcement_of_unique_object_values_in_all_the_scope_items_not_satisfied() {
    final String values = ""
        + "[  \n"
        + "   {  \n"
        + "      \"id\":\"d173cb97-8e1f-4931-b0a6-acf4940be296\",\n"
        + "      \"attr2\":{  \n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{  \n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{  \n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{  \n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"pkey\",\n"
        + "            \"scope\":[  \n"
        + "               {  \n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {  \n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   },\n"
        + "   {  \n"
        + "      \"id\":\"26061275-f623-4ded-a307-e5b596c16e60\",\n"
        + "      \"attr2\":{  \n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"attr3\":{  \n"
        + "         \"type\":\"string\",\n"
        + "          \"value\": {\n"
        + "              \"pt-pt\": \"Passe1\",\n"
        + "              \"en-us\": \"Pass1\",\n"
        + "              \"es-es\": \"Passe1\"\n"
        + "          }\n"
        + "      },\n"
        + "      \"price2\":{  \n"
        + "         \"type\":\"number\",\n"
        + "         \"value\":888,\n"
        + "         \"unique\":{  \n"
        + "            \"value\":\"true\",\n"
        + "            \"mode\":\"pkey\",\n"
        + "            \"scope\":[  \n"
        + "               {  \n"
        + "                  \"field\":\"attr2\"\n"
        + "               },\n"
        + "               {  \n"
        + "                  \"field\":\"attr3\"\n"
        + "               }\n"
        + "            ]\n"
        + "         }\n"
        + "      }\n"
        + "   }\n"
        + "]";

    try {
      new JSONCollection(values);
    } catch (Exceptions.UniqueValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_unique_object_values_from_property_with_deleted_values() {
    final String values = ""
        + "[\n" +
        "        {\n" +
        "          \"text\": {\n" +
        "            \"min\": 1,\n" +
        "            \"max\": 64,\n" +
        "            \"unique\": {\n" +
        "              \"value\": \"true\"\n" +
        "            },\n" +
        "            \"type\": \"string\",\n" +
        "            \"value\": {\n" +
        "              \"en-us\": \"Facebook\",\n" +
        "              \"es-es\": \"Facebook\"\n" +
        "            },\n" +
        "            \"mandatory\": \"true\"\n" +
        "          },\n" +
        "          \"id\": \"7aa0aa1b-65bf-4b51-bacd-bb7db9be5cfd\",\n" +
        "          \"state\": -1\n" +
        "        },\n" +
        "        {\n" +
        "          \"text\": {\n" +
        "            \"min\": 1,\n" +
        "            \"max\": 64,\n" +
        "            \"unique\": {\n" +
        "              \"value\": \"true\"\n" +
        "            },\n" +
        "            \"type\": \"string\",\n" +
        "            \"value\": {\n" +
        "              \"en-us\": \"Facebook\",\n" +
        "              \"es-es\": \"Facebook\"\n" +
        "            },\n" +
        "            \"mandatory\": \"true\"\n" +
        "          },\n" +
        "          \"id\": \"7aa0aa1b-65bf-4b51-bacd-bb7db9be5cfe\",\n" +
        "          \"state\": 0\n" +
        "        }\n" +
        "      ]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
  }

  @Test
  public void test_enforcement_of_unique_values_from_property_and_scope_has_at_least_one_distinct_value() {
    final String values = "" //
        + "[\n"//
        + "   {\n"//
        + "      \"id\":\"589ad7f4-4648-43a7-8f80-4eaf27e45816\",\n"//
        + "      \"attr2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"attr3\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"distinct\",\n"//
        + "            \"scope\":[\n"//
        + "               {\n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {\n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   },\n"//
        + "   {\n"//
        + "      \"id\":\"d7b55f37-c8e7-4911-8307-6df004220657\",\n"//
        + "      \"attr2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":2\n"//
        + "      },\n"//
        + "      \"attr3\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"distinct\",\n"//
        + "            \"scope\":[\n"//
        + "               {\n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {\n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   }\n"//
        + "]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
  }

  @Test
  public void test_enforcement_of_unique_values_from_property_and_scope_has_at_least_one_distinct_value_not_satisfied() {

    final String values = ""//
        + "[\n"//
        + "   {\n"//
        + "      \"id\":\"d55df211-9c3c-43f8-aca3-d8f0a7c52691\",\n"//
        + "      \"attr2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"attr3\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"distinct\",\n"//
        + "            \"scope\":[\n"//
        + "               {\n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {\n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   },\n"//
        + "   {\n"//
        + "      \"id\":\"81fc4e5a-ccc7-4b7f-a3db-22b21d378f0c\",\n"//
        + "      \"attr2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"attr3\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"distinct\",\n"//
        + "            \"scope\":[\n"//
        + "               {\n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {\n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   }\n"//
        + "]";

    try {
      new JSONCollection(values);
    } catch (Exceptions.UniqueValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_enforcement_of_unique_values_in_all_the_scope_items() {
    final String values = ""//
        + "[\n"//
        + "   {\n"//
        + "      \"id\":\"905d39ff-3062-4032-8905-3ff3025da898\",\n"//
        + "      \"attr2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"attr3\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"pkey\",\n"//
        + "            \"scope\":[\n"//
        + "               {\n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {\n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   },\n"//
        + "   {\n"//
        + "      \"id\":\"356eb178-6f2a-4827-8b62-f5dd3f25eeff\",\n"//
        + "      \"attr2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"attr3\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":2\n"//
        + "      },\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"pkey\",\n"//
        + "            \"scope\":[\n"//
        + "               {\n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {\n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   },\n"//
        + "   {\n"//
        + "      \"id\":\"e2ac8e87-5c86-4f16-92f0-5c68a9006efd\",\n"//
        + "      \"attr2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":2\n"//
        + "      },\n"//
        + "      \"attr3\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{\n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"pkey\",\n"//
        + "            \"scope\":[\n"//
        + "               {\n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {\n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   }"//
        + "]";

    try {
      new JSONCollection(values);
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
  }

  @Test
  public void test_enforcement_of_unique_values_in_all_the_scope_items_not_satisfied() {
    final String values = ""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"id\":\"d173cb97-8e1f-4931-b0a6-acf4940be296\",\n"//
        + "      \"attr2\":{  \n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"attr3\":{  \n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{  \n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{  \n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"pkey\",\n"//
        + "            \"scope\":[  \n"//
        + "               {  \n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {  \n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"26061275-f623-4ded-a307-e5b596c16e60\",\n"//
        + "      \"attr2\":{  \n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"attr3\":{  \n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1\n"//
        + "      },\n"//
        + "      \"price2\":{  \n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":888,\n"//
        + "         \"unique\":{  \n"//
        + "            \"value\":\"true\",\n"//
        + "            \"mode\":\"pkey\",\n"//
        + "            \"scope\":[  \n"//
        + "               {  \n"//
        + "                  \"field\":\"attr2\"\n"//
        + "               },\n"//
        + "               {  \n"//
        + "                  \"field\":\"attr3\"\n"//
        + "               }\n"//
        + "            ]\n"//
        + "         }\n"//
        + "      }\n"//
        + "   }\n"//
        + "]";

    try {
      new JSONCollection(values);
    } catch (Exceptions.UniqueValueExpectationFailedException e1) {
      Assert.assertTrue(true);
      return;
    } catch (BusinessException e2) {
      Assert.fail(e2.getStackTrace()[0].toString(), e2);
      return;
    }
    Assert.fail("not supposed to get here");
  }

  @Test
  public void test_serialize_and_deserialize_and_stay_the_same()
    throws IOException, BusinessException {

    String values = ""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"money\":{  \n"//
        + "         \"value\":0,\n"//
        + "         \"type\":\"number\"\n"//
        + "      },\n"//
        + "      \"name\":{  \n"//
        + "         \"type\":\"sstring\",\n"//
        + "         \"value\":\"\"\n"//
        + "      },\n"//
        + "      \"xxx\":[  \n"//
        + "         {  \n"//
        + "            \"a\":1\n"//
        + "         },\n"//
        + "         {  \n"//
        + "            \"b\":2\n"//
        + "         }\n"//
        + "      ]\n"//
        + "   },\n"//
        + "   {  \n"//
        + "      \"money\":{  \n"//
        + "         \"value\":1,\n"//
        + "         \"type\":\"number\"\n"//
        + "      },\n"//
        + "      \"name\":{  \n"//
        + "         \"type\":\"sstring\",\n"//
        + "         \"value\":\"\"\n"//
        + "      },\n"//
        + "      \"xxx\":[  \n"//
        + "         {  \n"//
        + "            \"a\":1\n"//
        + "         },\n"//
        + "         {  \n"//
        + "            \"b\":2\n"//
        + "         }\n"//
        + "      ]\n"//
        + "   }\n"//
        + "]";

    JSONCollection expectedObject1 = new JSONCollection(values);

    ObjectMapper mapper = new ObjectMapper();

    String serialized = mapper.writeValueAsString(expectedObject1);
    JSONCollection deserialized = mapper.readValue(serialized,
        JSONCollection.class);

    assertEquals(deserialized.getValue().toString(), expectedObject1.getValue()
        .toString());
  }

  @Test
  public void test_suppress_not_active()
    throws Exception {
    final String values = "" //
        + "[  \n"//
        + "   {  \n" //
        + "      \"id\":1,\n" //
        + "      \"active\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n" //
        + "      \"id\":3,\n" //
        + "      \"dummy\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":2,\n"//
        + "      \"active\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":0,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      }\n" //
        + "   }\n"//
        + "]";

    JSONCollection res = new JSONCollection(values).enforceActive();
    Assert.assertEquals(res.getValue().length(), 2);
  }

  @Test
  public void test_suppress_not_active_except_ignores()
    throws Exception {
    final String values = "" //
        + "[  \n"//
        + "   {  \n" //
        + "      \"id\":1,\n" //
        + "      \"active\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n" //
        + "      \"id\":3,\n" //
        + "      \"dummy\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":2,\n"//
        + "      \"active\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":0,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      }\n" //
        + "   }\n"//
        + "]";

    JSONCollection res = new JSONCollection(values).enforceActive("2");
    Assert.assertEquals(res.getValue().length(), 3);
  }

  @Test
  public void test_suppress_not_visible()
    throws Exception {
    final String values = "" //
        + "[  \n"//
        + "   {  \n" //
        + "      \"id\":1,\n" //
        + "      \"visible\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n" //
        + "      \"id\":3,\n" //
        + "      \"dummy\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":2,\n"//
        + "      \"visible\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":0,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      }\n" //
        + "   }\n"//
        + "]";

    JSONCollection res = new JSONCollection(values).enforceVisible();
    Assert.assertEquals(res.getValue().length(), 2);
  }

  @Test
  public void test_suppress_not_visible_except_ignores()
    throws Exception {
    final String values = "" //
        + "[  \n"//
        + "   {  \n" //
        + "      \"id\":1,\n" //
        + "      \"visible\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n" //
        + "      \"id\":3,\n" //
        + "      \"dummy\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":1,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      },\n" //
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":2,\n"//
        + "      \"visible\":{  \n"//
        + "         \"mandatory\":\"true\",\n"//
        + "         \"type\":\"number\",\n"//
        + "         \"value\":0,\n"//
        + "         \"min\":0,\n"//
        + "         \"max\":1\n"//
        + "      }\n" //
        + "   }\n"//
        + "]";

    JSONCollection res = new JSONCollection(values).enforceVisible("2");
    Assert.assertEquals(res.getValue().length(), 3);
  }

  @Test
  public void test_suppress_deleted()
    throws Exception {
    final String values = "" //
        + "[  \n"//
        + "   {  \n" //
        + "      \"id\":1,\n" //
        + "      \"state\":-1\n" //
        + "   },\n"//
        + "   {  \n" //
        + "      \"id\":2,\n" //
        + "      \"state\":-1\n" //
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":3,\n"//
        + "      \"state\":0\n" //
        + "   }\n"//
        + "]";

    JSONCollection res = new JSONCollection(values).enforceNotDeleted();
    Assert.assertEquals(res.getValue().length(), 1);
  }

  @Test
  public void test_suppress_deleted_expect_ignores()
    throws Exception {
    final String values = "" //
        + "[  \n"//
        + "   {  \n" //
        + "      \"id\":1,\n" //
        + "      \"state\":-1\n" //
        + "   },\n"//
        + "   {  \n" //
        + "      \"id\":2,\n" //
        + "      \"state\":-1\n" //
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":3,\n"//
        + "      \"state\":0\n" //
        + "   }\n"//
        + "]";

    JSONCollection res = new JSONCollection(values).enforceNotDeleted("1");
    Assert.assertEquals(res.getValue().length(), 2);
  }

  @Test
  public void test_find_items_with_property_not_available()
    throws Exception {

    JSONCollection object = new JSONCollection(""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"id\":1"//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":2,"//
        + "      \"str\":\"ola\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":3"//
        + "   }\n"//
        + "]");

    assertEquals(object.findItemsWithoutProperty("str").getValue().length(), 2);
  }

  @Test
  public void test_generate_identifiers()
    throws Exception {

    JSONCollection object = new JSONCollection(""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"id\":1"//
        + "   },\n"//
        + "   {  \n"//
        + "      \"str\":\"ola\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":3"//
        + "   }\n"//
        + "]");

    JSONCollection c = object.generateNewItemsIdentifier();
    assertEquals(c.getValue().length(), 3);
    assertEquals(c.findItemsWithoutProperty("id").getValue().length(), 0);

  }

  @Test
  public void test_append_removed_items()
    throws Exception {

    JSONCollection oldItems = new JSONCollection(""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"id\":\"1\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"2\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"3\""//
        + "   }\n"//
        + "]");

    JSONCollection newItems = new JSONCollection(""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"id\":\"1\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"3\""//
        + "   }\n"//
        + "]");

    JSONCollection jc = newItems.appendRemovedItems(oldItems);
    assertEquals(jc.getValue().length(), 3);
  }

  @Test
  public void test_purge_invalid_items()
    throws Exception {

    JSONCollection oldItems = new JSONCollection(""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"id\":\"1\","//
        + "      \"name\":\"1\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"2\","//
        + "      \"name\":\"2\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"3\","//
        + "      \"name\":\"3\""//
        + "   }\n"//
        + "]");

    JSONCollection newItems = new JSONCollection(""//
        + "[  \n"//
        + "   {  \n"//
        + "      \"id\":\"1\","//
        + "      \"name\":\"1\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"2\","//
        + "      \"name\":\"2\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"3\","//
        + "      \"name\":\"3\""//
        + "   },\n"//
        + "   {  \n"//        
        + "      \"name\":\"4\""//
        + "   },\n"//
        + "   {  \n"//
        + "      \"id\":\"9999999999\","//
        + "      \"name\":\"99999999999\""//
        + "   }\n"//
        + "]");

    JSONCollection jc = newItems.purgeInvalidItems(oldItems);
    assertEquals(jc.getValue().length(), 4);
  }

  @Test
  public void test_create_JSON_collection_with_array_properties()
    throws BusinessException {

    JSONObject atomicSchemaObject = new JSONObject(
        "{\n" +
            "    \"id\": \"00000000-0000-0000-0000-00000000\",\n" +
            "    \"state\": \"0\",\n" +
            "    \"checklist\": [\n" +
            "        {\n" +
            "            \"min\": 0,\n" +
            "            \"max\": 256,\n" +
            "            \"type\": \"sstring\",\n" +
            "            \"mandatory\": \"true\",\n" +
            "            \"value\": \"\"\n" +
            "        }\n" +
            "    ]\n" +
            "}");

    JSONArray valuesCollectionObject = new JSONArray(
        "[\n" +
            "    {\n" +
            "        \"id\": \"7c889624-021a-4b38-a1ee-29a4dd590455\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": [\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 2\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"4ca2c405-0f05-4ca9-b1df-3e2a733cc352\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": [\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 2\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]");

    JSONCollection resultCollection = new JSONCollection(atomicSchemaObject,
        valuesCollectionObject);

    JSONCollection expectedCollection = new JSONCollection(
        "[\n" +
            "    {\n" +
            "        \"id\": \"7c889624-021a-4b38-a1ee-29a4dd590455\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": [\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 2\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"4ca2c405-0f05-4ca9-b1df-3e2a733cc352\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": [\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 1\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"min\": 0,\n" +
            "                \"max\": 256,\n" +
            "                \"type\": \"sstring\",\n" +
            "                \"mandatory\": \"true\",\n" +
            "                \"value\": \"Check 2\"\n" +
            "            }\n" +
            "        ]\n" +
            "    }\n" +
            "]");

    assertEquals(resultCollection.getValue().toString(), expectedCollection
        .getValue().toString());
  }

  @Test
  public void test_create_JSON_collection_with_empty_array()
    throws BusinessException {

    JSONObject atomicSchemaObject = new JSONObject(
        "{\n" +
            "    \"id\": \"00000000-0000-0000-0000-00000000\",\n" +
            "    \"state\": \"0\",\n" +
            "    \"checklist\": []\n" +
            "}");

    JSONArray valuesCollectionObject = new JSONArray(
        "[\n" +
            "    {\n" +
            "        \"id\": \"7c889624-021a-4b38-a1ee-29a4dd590455\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": []\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"4ca2c405-0f05-4ca9-b1df-3e2a733cc352\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": []\n" +
            "    }\n" +
            "]");

    JSONCollection resultCollection = new JSONCollection(atomicSchemaObject, valuesCollectionObject);

    JSONCollection expectedCollection = new JSONCollection(
        "[\n" +
            "    {\n" +
            "        \"id\": \"7c889624-021a-4b38-a1ee-29a4dd590455\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": []\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"4ca2c405-0f05-4ca9-b1df-3e2a733cc352\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": []\n" +
            "    }\n" +
            "]");

    assertEquals(resultCollection.getValue().toString(), expectedCollection.getValue().toString());
  }

  @Test
  public void test_create_JSON_collection_with_simple_object()
    throws BusinessException {

    JSONObject atomicSchemaObject = new JSONObject(
        "{\n" +
            "    \"id\": \"00000000-0000-0000-0000-00000000\",\n" +
            "    \"state\": \"0\",\n" +
            "    \"checklist\": []\n" +
            "}");

    JSONArray valuesCollectionObject = new JSONArray(
        "[\n" +
            "    {\n" +
            "        \"id\": \"7c889624-021a-4b38-a1ee-29a4dd590455\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": [\"val1\", \"val3\"]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"4ca2c405-0f05-4ca9-b1df-3e2a733cc352\",\n" +
            "        \"state\": \"0\",\n" +
            "    }\n" +
            "]");

    JSONCollection resultCollection = new JSONCollection(atomicSchemaObject, valuesCollectionObject);

    JSONCollection expectedCollection = new JSONCollection(
        "[\n" +
            "    {\n" +
            "        \"id\": \"7c889624-021a-4b38-a1ee-29a4dd590455\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": [\"val1\", \"val3\"]\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": \"4ca2c405-0f05-4ca9-b1df-3e2a733cc352\",\n" +
            "        \"state\": \"0\",\n" +
            "        \"checklist\": []\n" +
            "    }\n" +
            "]");
    
    assertEquals(resultCollection.getValue().toString(), expectedCollection.getValue().toString());
  }

}
