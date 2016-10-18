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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON implements Serializable {

  private static final long serialVersionUID = -8198588262782226020L;

  private transient JSONObject value;

  /**
   * Updates the instance value If <code>schema</code> is not provided (null),
   * the schema is not enforced If any problem occurs when enforcing the <code>schema</code> on <code>newValue</code>,
   * the instance value defaults
   * to the <code>schema</code>
   * 
   * @param schema
   *          The validation and default schema
   * @param newValue
   *          The new value intended for this instance
   */
  public void setValue(JSONObject schema, JSONObject newValue)
    throws Exceptions.UnknownDataTypeException,
    Exceptions.MandatoryValueExpectationFailedException,
    Exceptions.InvalidDataTypeException,
    Exceptions.MalformedStrongTypedNodeException,
    Exceptions.NumberPrecisionExpectationFailedException,
    Exceptions.MinValueExpectationFailedException,
    Exceptions.MaxValueExpectationFailedException,
    Exceptions.InvalidEmptyObjectException {

    if (newValue != null) {
      try {

        if (schema != null) {
          this.value = enforceSchema(schema, newValue);
        }

        this.value = checkTypeConstraints(newValue);

      } catch (JSONException e) {
        this.value = schema;
      }
    }
  }

  /**
   * Updates the instance value.<br/>
   * If <code>schema</code> is not provided (null), the schema is not enforced.<br/>
   * If any problem occurs when enforcing the <code>schema</code> on <code>newValue</code>, the instance value defaults
   * to the <code>schema</code>.
   * 
   * @param schema
   *          The validation and default schema
   * @param newValue
   *          The new value intended for this instance
   */
  public void setValue(String schema, String newValue)
    throws Exceptions.UnknownDataTypeException,
    Exceptions.MandatoryValueExpectationFailedException,
    Exceptions.InvalidDataTypeException,
    Exceptions.MalformedStrongTypedNodeException,
    Exceptions.NumberPrecisionExpectationFailedException,
    Exceptions.MinValueExpectationFailedException,
    Exceptions.MaxValueExpectationFailedException,
    Exceptions.InvalidEmptyObjectException {

    if (schema != null && !schema.isEmpty() && newValue != null
        && !newValue.isEmpty()) {
      setValue(new JSONObject(schema), new JSONObject(newValue));
    } else if ((schema == null || schema.isEmpty()) && newValue != null
        && !newValue.isEmpty()) {
      setValue(null, new JSONObject(newValue));
    }
  }

  /**
   * Updates the instance value with no schema enforcement
   * 
   * @param value
   *          The value intended for this instance
   */
  @JsonDeserialize(using = JSONObjectDeserializer.class)
  public void setValue(JSONObject value)
    throws Exceptions.UnknownDataTypeException,
    Exceptions.MandatoryValueExpectationFailedException,
    Exceptions.InvalidDataTypeException,
    Exceptions.MalformedStrongTypedNodeException,
    Exceptions.NumberPrecisionExpectationFailedException,
    Exceptions.MinValueExpectationFailedException,
    Exceptions.MaxValueExpectationFailedException,
    Exceptions.InvalidEmptyObjectException {

    setValue(null, value);
  }

  @JsonSerialize(using = JSONObjectSerializer.class)
  public JSONObject getValue() {

    return this.value;
  }

  public JSON() {

  }

  /**
   * Creates a new instance with no schema enforcement.
   * 
   * @param value
   *          The value intended for this instance
   */
  public JSON(String value) throws Exceptions.UnknownDataTypeException,
      Exceptions.MandatoryValueExpectationFailedException,
      Exceptions.InvalidDataTypeException,
      Exceptions.MalformedStrongTypedNodeException,
      Exceptions.NumberPrecisionExpectationFailedException,
      Exceptions.MinValueExpectationFailedException,
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {

    setValue(null, value);
  }

  /**
   * Creates a new instance with no schema enforcement.
   * 
   * @param value
   *          The value intended for this instance
   */
  public JSON(JSONObject value) throws Exceptions.UnknownDataTypeException,
      Exceptions.MandatoryValueExpectationFailedException,
      Exceptions.InvalidDataTypeException,
      Exceptions.MalformedStrongTypedNodeException,
      Exceptions.NumberPrecisionExpectationFailedException,
      Exceptions.MinValueExpectationFailedException,
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {

    setValue(null, value);
  }

  /**
   * Creates a new instance.<br/>
   * If <code>schema</code> is not provided (null), the schema is not enforced.<br/>
   * If any problem occurs when enforcing the <code>schema</code> on <code>newValue</code>, the instance value defaults
   * to the <code>schema</code>.<br/>
   * 
   * @param schema
   *          The validation and default schema
   * @param value
   *          The value intended for this instance
   * @throws Exceptions.UnknownDataTypeException
   * @throws Exceptions.MalformedStrongTypedNodeException
   * @throws Exceptions.MinValueExpectationFailedException
   * @throws Exceptions.MaxValueExpectationFailedException
   * @throws Exceptions.InvalidDataTypeException
   * @throws Exceptions.MandatoryValueExpectationFailedException
   */
  public JSON(String schema, String value)
      throws Exceptions.UnknownDataTypeException,
      Exceptions.MandatoryValueExpectationFailedException,
      Exceptions.InvalidDataTypeException,
      Exceptions.MalformedStrongTypedNodeException,
      Exceptions.NumberPrecisionExpectationFailedException,
      Exceptions.MinValueExpectationFailedException,
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {

    setValue(schema, value);
  }

  /**
   * Creates a new instance.<br/>
   * If <code>schema</code> is not provided (null), the schema is not enforced.<br/>
   * If any problem occurs when enforcing the <code>schema</code> on <code>newValue</code>, the instance value defaults
   * to the <code>schema</code>.<br/>
   * 
   * @param schema
   *          The validation and default schema
   * @param value
   *          The value intended for this instance
   */
  public JSON(JSONObject schema, JSONObject value)
      throws Exceptions.UnknownDataTypeException,
      Exceptions.MandatoryValueExpectationFailedException,
      Exceptions.InvalidDataTypeException,
      Exceptions.MalformedStrongTypedNodeException,
      Exceptions.NumberPrecisionExpectationFailedException,
      Exceptions.MinValueExpectationFailedException,
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {

    setValue(schema, value);
  }

  // public API
  @Override
  public String toString() {

    return this.value.toString();
  }

  @Override
  public boolean equals(Object obj) {

    return this.toString().equals(obj.toString());
  }

  /***
   *
   * @param object
   */
  public static JSONObject checkTypeConstraints(JSONObject object)
    throws Exceptions.UnknownDataTypeException,
    Exceptions.MandatoryValueExpectationFailedException,
    Exceptions.InvalidDataTypeException,
    Exceptions.MalformedStrongTypedNodeException,
    Exceptions.NumberPrecisionExpectationFailedException,
    Exceptions.MinValueExpectationFailedException,
    Exceptions.MaxValueExpectationFailedException,
    Exceptions.InvalidEmptyObjectException {

    String errorMessage = ":" + object.toString();

    if (JSONObject.getNames(object) == null)
      throw new Exceptions.InvalidEmptyObjectException(errorMessage);

    for (String key : JSONObject.getNames(object)) {
      errorMessage = key + errorMessage;
      Object schemaKeyValue = object.get(key);

      if (schemaKeyValue instanceof JSONObject) {
        checkTypeConstraints((JSONObject) schemaKeyValue);
      } else if (key.equals(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG)) {

        // if <NODE_CUSTOM_PROPERTY_TYPE_TAG> is present, node is considered
        // strong-typed
        // and its value should conform to the announced data-type

        // <NODE_CUSTOM_PROPERTY_VALUE_TAG>is mandatory for strong-typed nodes
        // <NODE_CUSTOM_PROPERTY_MANDATORY_TAG>, <NODE_CUSTOM_PROPERTY_MIN_TAG>
        // and
        // <NODE_CUSTOM_PROPERTY_MAX_TAG> are optional for strong-typed nodes
        String dataType;
        Object value;
        Boolean mandatory;
        Integer precision;
        try {
          dataType = object.getString(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG);
          value = object.get(Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG);
          mandatory = object.has(Constants.NODE_CUSTOM_PROPERTY_MANDATORY_TAG) ? object
              .getBoolean(Constants.NODE_CUSTOM_PROPERTY_MANDATORY_TAG) : false;

          precision = getPrecision(object);

        } catch (JSONException e) {
          throw new Exceptions.MalformedStrongTypedNodeException(errorMessage);
        }

        // check for data-type consistency and constraints validation:
        // - mandatory fields
        // - min & max values
        switch (dataType) {
        case Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_NUMBER:
          if (value instanceof Integer || value instanceof Long || value instanceof Double) {

            final BigDecimal number = new BigDecimal(value.toString());

            if (precision != null && number.scale() > precision) {
              throw new Exceptions.NumberPrecisionExpectationFailedException(errorMessage);
            }

            final BigDecimal min = object.has(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG) ?
                new BigDecimal(object.getDouble(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG)) : null;

            final BigDecimal max = object.has(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG) ?
                new BigDecimal(object.getDouble(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG)) : null;

            // min value
            if (min != null && number.compareTo(min) < 0) {
              throw new Exceptions.MinValueExpectationFailedException(
                  errorMessage);
            }

            // max value
            if (max != null && number.compareTo(max) > 0) {
              throw new Exceptions.MaxValueExpectationFailedException(
                  errorMessage);
            }

          } else {
            throw new Exceptions.InvalidDataTypeException(errorMessage);
          }

          break;

        case Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_SIMPLE_STRING:
          if (!(value instanceof String)) {
            throw new Exceptions.InvalidDataTypeException(errorMessage);
          } else {
            String valueAsString = (String) value;
            final Integer min = object
                .has(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG) ? object
                .getInt(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG) : null;
            final Integer max = object
                .has(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG) ? object
                .getInt(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG) : null;

            // mandatory
            if (mandatory && valueAsString.isEmpty()) {
              throw new Exceptions.MandatoryValueExpectationFailedException(
                  errorMessage);
            }

            // min value
            if (min != null && valueAsString.length() < min) {
              throw new Exceptions.MinValueExpectationFailedException(
                  errorMessage);
            }

            // max value
            if (max != null && valueAsString.length() > max) {
              throw new Exceptions.MaxValueExpectationFailedException(
                  errorMessage);
            }
          }
          break;

        case Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_STRING:
          if (!(value instanceof JSONObject)) {
            throw new Exceptions.InvalidDataTypeException(errorMessage);
          } else {
            JSONObject valueAsObj = (JSONObject) value;
            final Integer min = object
                .has(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG) ? object
                .getInt(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG) : null;
            final Integer max = object
                .has(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG) ? object
                .getInt(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG) : null;

            String[] namesStringObj = JSONObject.getNames(valueAsObj);
            if (namesStringObj != null) {
              for (String keyVal : namesStringObj) {
                errorMessage = keyVal + errorMessage;
                Object schemaKeyValueVal = valueAsObj.get(keyVal);
                String valueAsString = (String) schemaKeyValueVal;
                // mandatory
                if (mandatory && valueAsString.isEmpty()) {
                  throw new Exceptions.MandatoryValueExpectationFailedException(
                      errorMessage);
                }

                // min value
                if (min != null && valueAsString.length() < min) {
                  throw new Exceptions.MinValueExpectationFailedException(
                      errorMessage);
                }

                // max value
                if (max != null && valueAsString.length() > max) {
                  throw new Exceptions.MaxValueExpectationFailedException(
                      errorMessage);
                }
              }
            }
          }
          break;

        default:
          throw new Exceptions.UnknownDataTypeException(errorMessage);
        }

      }
    }
    return object;
  }

  /**
   * Ensure that the JSONObject is conform to the schema <br/>
   * 
   * @param schema
   * @param newValue
   * @return JSONObject
   */
  public static JSONObject enforceSchema(JSONObject schema, JSONObject newValue)
    throws Exceptions.MalformedStrongTypedNodeException {

    // operations order matter here
    deepRemove(schema, newValue);
    deepAdd(schema, newValue);

    return newValue;
  }

  /**
   * This enforces uniqueness for 1st level properties.<br/>
   * It DOES NOT WORK in nested objects
   * 
   * @param object
   *          Origin object that is going to be compared to every other object
   *          in the collection
   * @param jsonCollection
   *          Collection in with the search will be conducted
   */
  public static JSONObject enforceUniqueness(JSONObject object, JSONArray jsonCollection)
    throws Exceptions.UniqueValueUnknownScopeTypeException,
    Exceptions.MalformedSearchableNodeException,
    Exceptions.UniqueValueExpectationFailedException {

    return enforceUniqueness(object, jsonCollection, null, null);
  }

  /**
   * This enforces uniqueness for 1st level properties.<br/>
   * It DOES NOT WORK in nested objects
   * 
   * @param object
   *          Origin object that is going to be compared to every other object
   *          in the collection
   * @param jsonCollection
   *          Collection in with the search will be conducted
   * @param refObject
   *          Used internally in recursive calls to maintain a reference to the
   *          context object
   * @param refObjectPropertyName
   *          Used internally in recursive calls to maintain a reference to the
   *          context object
   */
  public static JSONObject enforceUniqueness(JSONObject object, JSONArray jsonCollection, JSONObject refObject,
      String refObjectPropertyName)
    throws Exceptions.UniqueValueUnknownScopeTypeException,
    Exceptions.MalformedSearchableNodeException,
    Exceptions.UniqueValueExpectationFailedException {

    for (String key : JSONObject.getNames(object)) {
      Object currentObject = object.get(key);

      if (refObjectPropertyName == null && currentObject instanceof JSONObject) {
        if (((JSONObject) currentObject).has(Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG)) {
          // only interested in going further if unique meta-tag is present
          enforceUniqueness((JSONObject) currentObject, jsonCollection, object, key);
        }

      } else if (key.equals(Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG)) {
        // so the property has the unique meta-tag. it probably really wants to
        // be unique,
        // for the property represented by the current JSONObject, extract
        // the values for the meta-tags we care about
        // make sure the root object has a valid NODE_ID_PROPERTY and check
        // the dam thing already
        Object propertyValue;
        Boolean propertyUniqueTagValue;
        try {
          propertyValue = object.get(Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG);
          propertyUniqueTagValue = object.getJSONObject(
              Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG).getBoolean(
              Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_VALUE_TAG);
        } catch (JSONException e) {
          throw new Exceptions.MalformedSearchableNodeException(e);
        }

        if (propertyUniqueTagValue) {

          String refObjectId;

          try {
            refObjectId = getId(refObject);
          } catch (Exceptions.MalformedStrongTypedNodeException e) {
            throw new Exceptions.MalformedSearchableNodeException(
                "To enforce uniqueness, the Object must also contain the property <"
                    + Constants.NODE_ID_PROPERTY + "> : " + e.getMessage());
          }

          if (refObjectId == null || refObjectId.isEmpty()) {
            throw new Exceptions.MalformedSearchableNodeException(
                "Searchable Object's <" + Constants.NODE_ID_PROPERTY
                    + "> property value must be valid");
          }

          boolean bIsDeleted = false;
          if (refObject != null && refObject.has(Constants.NODE_STATE_PROPERTY)) {
            if (((Integer) refObject.get(Constants.NODE_STATE_PROPERTY)).intValue() == Constants.NODE_STATE_PROPERTY_INSTANCE_DELETED) {
              bIsDeleted = true;
            }
          }

          if (!bIsDeleted && !isPropertyValueUnique(refObject, refObjectPropertyName, jsonCollection)) {
            throw new Exceptions.UniqueValueExpectationFailedException(
                "Property <" + refObjectPropertyName + "> from object <"
                    + refObjectId + "> has value <" + propertyValue
                    + "> witch is not unique in the collection");
          }

        }
      }
    }

    return object;
  }

  public static String getId(JSONObject object)
    throws Exceptions.MalformedStrongTypedNodeException {

    String result;
    try {
      result = object.get(Constants.NODE_ID_PROPERTY).toString();
    } catch (JSONException e) {
      throw new Exceptions.MalformedStrongTypedNodeException(
          "Unable to get item Id");
    }

    return result;
  }

  public static JSONObject setDeleted(JSONObject object) {

    object.put(Constants.NODE_STATE_PROPERTY,
        Constants.NODE_STATE_PROPERTY_INSTANCE_DELETED);
    return object;
  }

  public static boolean isActive(JSONObject object)
    throws Exceptions.InvalidDataTypeException {

    return isBooleanPropertyTrue(object, Constants.NODE_ACTIVE_PROPERTY,
        Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG);
  }

  public static boolean isVisible(JSONObject object)
    throws Exceptions.InvalidDataTypeException {

    return isBooleanPropertyTrue(object, Constants.NODE_VISIBLE_PROPERTY,
        Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG);
  }

  public static boolean isDeleted(JSONObject object)
    throws Exceptions.InvalidDataTypeException {

    boolean result = false;
    for (String key : JSONObject.getNames(object)) {
      if (key.equals(Constants.NODE_STATE_PROPERTY)) {
        int value;
        try {
          value = object.getInt(Constants.NODE_STATE_PROPERTY);
        } catch (JSONException e) {
          throw new Exceptions.InvalidDataTypeException(e);
        }

        result = value == Constants.NODE_STATE_PROPERTY_INSTANCE_DELETED;
        break;
      }
    }

    return result;
  }

  public static boolean isPropertyAvailable(JSONObject object,
      String nodePropertyName) {

    boolean result = false;
    for (String key : JSONObject.getNames(object)) {
      if (key.equals(nodePropertyName)) {
        result = true;
        break;
      }
    }
    return result;
  }

  // internal API
  private static boolean isBooleanPropertyTrue(JSONObject object,
      String nodePropertyName, String nodePropertyValue)
    throws Exceptions.InvalidDataTypeException {

    boolean result = true;

    for (String key : JSONObject.getNames(object)) {
      if (key.equals(nodePropertyName)) {
        int value;

        try {
          value = object.getJSONObject(key).getInt(nodePropertyValue);
        } catch (JSONException e) {
          throw new Exceptions.InvalidDataTypeException(e);
        }

        result = value > 0;
        break;
      }
    }

    return result;
  }

  /**
   * This method searches for uniqueness on the Collection object's 1st level
   * properties.<br/>
   * It DOES NOT WORK in nested objects
   * 
   * @param searchedObject
   *          Origin object that is going to be compared to every other object
   *          in the collection
   * @param searchedPropertyName
   *          Property that wil be searched
   * @param jsonCollection
   *          Collection in with the search will be conducted
   * @return boolean
   */
  private static boolean isPropertyValueUnique(JSONObject searchedObject, String searchedPropertyName,
      JSONArray jsonCollection)
    throws Exceptions.MalformedSearchableNodeException, Exceptions.UniqueValueUnknownScopeTypeException {

    Boolean result = true;

    String searchedObjectId;
    JSONObject searchedPropertyObject;
    Object searchedPropertyValue;
    JSONObject searchedPropertyUniqueObject;

    // will need all this info from the original object, so try to get it right
    // of the bat
    try {
      searchedObjectId = getId(searchedObject);
      searchedPropertyObject = searchedObject.getJSONObject(searchedPropertyName);
      searchedPropertyValue = searchedPropertyObject.get(Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG);
      searchedPropertyUniqueObject = searchedPropertyObject.getJSONObject(Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG);

    } catch (JSONException | Exceptions.MalformedStrongTypedNodeException e) {
      throw new Exceptions.MalformedSearchableNodeException(e);
    }

    // need to search in all items from the collection
    JSONObject currentObject;
    String currentObjectId;
    for (int i = 0; i < jsonCollection.length(); i++) {
      currentObject = jsonCollection.getJSONObject(i);

      if (currentObject.has(Constants.NODE_STATE_PROPERTY)) {
        if (((Integer) currentObject.get(Constants.NODE_STATE_PROPERTY)).intValue() == Constants.NODE_STATE_PROPERTY_INSTANCE_DELETED) {
          continue;
        }
      }

      try {
        currentObjectId = getId(currentObject);
      } catch (JSONException | Exceptions.MalformedStrongTypedNodeException e) {
        throw new Exceptions.MalformedSearchableNodeException(
            "To enforce uniqueness, all Objects in the collection must must also contain the property <"
                + Constants.NODE_ID_PROPERTY + "> : " + e.getMessage());
      }

      if (!searchedObjectId.equals(currentObjectId) && currentObject.has(searchedPropertyName)) {

        Object currentPropertyValue;
        try {
          currentPropertyValue = currentObject.getJSONObject(searchedPropertyName).get(
              Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG);
        } catch (JSONException e) {
          throw new Exceptions.MalformedSearchableNodeException();
        }

        result = arePropertiesDifferent(searchedPropertyValue, currentPropertyValue);

        // so if scope is provided, to make it scope aware, for each
        // property referenced by the
        // search object's property>unique>scope,
        // we need to compare the value in the original object with the
        // collection current object taking in consideration the type of
        // uniqueness.
        if (searchedPropertyUniqueObject.has(Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_SCOPE_TAG)) {

          final JSONArray searchedPropertyUniqueObjectScope = searchedPropertyUniqueObject
              .getJSONArray(Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_SCOPE_TAG);
          final String scopeType = searchedPropertyUniqueObject.has(Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG)
              ? searchedPropertyUniqueObject.getString(Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG)
              : Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG_INSTANCE_DISTINCT;

          String scopeItemValue;
          switch (scopeType) {
          case Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG_INSTANCE_PKEY:

            // don't care about if the property value is equal or not
            result = true;
            boolean allScopeItemsAreEqual = true;
            for (int j = 0; j < searchedPropertyUniqueObjectScope.length(); j++) {

              scopeItemValue = searchedPropertyUniqueObjectScope.getJSONObject(j).getString(
                  Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_SCOPE_TAG_VALUE_TAG);

              /*allScopeItemsAreEqual = allScopeItemsAreEqual
                      && (currentObject.getJSONObject(scopeItemValue)
                      .get(Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG)
                      .equals(searchedObject.getJSONObject(scopeItemValue).get(Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG)));*/

              allScopeItemsAreEqual = allScopeItemsAreEqual
                  && !arePropertiesDifferent(currentObject.getJSONObject(scopeItemValue)
                      .get(Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG),
                      searchedObject.getJSONObject(scopeItemValue).get(Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG));
            }
            result = result && !allScopeItemsAreEqual;
            break;

          case Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG_INSTANCE_DISTINCT:
            for (int j = 0; j < searchedPropertyUniqueObjectScope.length(); j++) {

              scopeItemValue = searchedPropertyUniqueObjectScope
                  .getJSONObject(j)
                  .getString(
                      Constants.NODE_CUSTOM_PROPERTY_UNIQUE_TAG_SCOPE_TAG_VALUE_TAG);

              /*result = result
                  || !(currentObject.getJSONObject(scopeItemValue).get(
                      Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG)
                      .equals(searchedObject.getJSONObject(scopeItemValue).get(
                          Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG)));*/

              result = result || arePropertiesDifferent(currentObject.getJSONObject(scopeItemValue).get(
                  Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG), searchedObject.getJSONObject(scopeItemValue).get(
                  Constants.NODE_CUSTOM_PROPERTY_VALUE_TAG));
            }
            break;

          default:
            throw new Exceptions.UniqueValueUnknownScopeTypeException();
          }
        }
      }

      // only need to find one object in the collection that makes the original
      // one not unique
      if (!result) {
        break;
      }

    }

    return result;
  }

  private static Boolean arePropertiesDifferent(Object searchedPropertyValue, Object currentPropertyValue) {

    Boolean result = true;
    if (currentPropertyValue instanceof JSONObject) {

      if (searchedPropertyValue instanceof JSONObject) {

        String[] namesStringObj = JSONObject.getNames((JSONObject) currentPropertyValue);
        String[] namesStringObjCurrent = JSONObject.getNames((JSONObject) currentPropertyValue);

        if (namesStringObj != null && namesStringObjCurrent != null) {

          if (namesStringObj.length != 1 || !namesStringObj[0].equals("empty")) {

            for (String key : namesStringObj) {

              if (((JSONObject) searchedPropertyValue).has(key)) {
                result = result
                    && !((JSONObject) currentPropertyValue).get(key).equals(
                        ((JSONObject) searchedPropertyValue).get(key));
              }
            }
          }
        } else if (namesStringObj == null && namesStringObjCurrent == null) {
          result = false;
        }
      }
    } else {

      result = !searchedPropertyValue.equals(currentPropertyValue);
    }

    return result;
  }

  private static JSONObject deepRemove(JSONObject schemaObject,
      JSONObject newValueObject) {

    // remove newValue keys absent in schema
    for (String key : JSONObject.getNames(newValueObject)) {
      if (!schemaObject.has(key)) {
        // remove "key":
        newValueObject.remove(key);
      } else {
        if (newValueObject.get(key) instanceof JSONObject) {
          if (!schemaObject.has(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG)
              || !((String) schemaObject.get(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG))
                  .equals(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_STRING)) {
            deepRemove(schemaObject.getJSONObject(key),
                newValueObject.getJSONObject(key));
          }
        }
      }
    }

    return newValueObject;
  }

  private static JSONObject deepAdd(JSONObject schemaObject,
      JSONObject newValueObject) {

    // add schema keys missing in newValue
    for (String key : JSONObject.getNames(schemaObject)) {
      Object schemaKeyValue = schemaObject.get(key);

      if (!newValueObject.has(key)) {
        // new value for "key":
        newValueObject.put(key, schemaKeyValue);
      } else if (schemaKeyValue instanceof JSONObject) {
        // existing value for "key" - recursively deep merge:
        if (!schemaObject.has(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG)
            || !((String) schemaObject.get(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG))
                .equals(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_STRING)) {
          deepAdd((JSONObject) schemaKeyValue, newValueObject.getJSONObject(key));
        }
      } else if (schemaKeyValue instanceof JSONArray) {
        JSONArray schemaKeyValueCollection = (JSONArray) schemaKeyValue;
        if (schemaKeyValueCollection.length() > 0 && 
            (!schemaObject.has(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG) || 
            !((String) schemaObject.get(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG))
                .equals(Constants.NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_STRING))) {
          deepAdd((JSONObject) schemaKeyValueCollection.getJSONObject(0),
              (JSONObject) newValueObject.getJSONArray(key).get(0));
        }
      }
    }

    return newValueObject;
  }

  private void writeObject(ObjectOutputStream oos)
    throws IOException {

    oos.defaultWriteObject();
    oos.writeObject(this.value.toString());
  }

  private void readObject(ObjectInputStream ois)
    throws ClassNotFoundException,
    IOException, JSONException {

    ois.defaultReadObject();
    this.value = new JSONObject((String) ois.readObject());
  }

  private static Integer getPrecision(JSONObject object) {

    Integer precision = null;

    // to avoid to put precision in two places
    final String precisionArrayMax = object
        .has(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG) ? object
        .get(Constants.NODE_CUSTOM_PROPERTY_MAX_TAG).toString() : null;
    final String precisionArrayMin = object
        .has(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG) ? object
        .get(Constants.NODE_CUSTOM_PROPERTY_MIN_TAG).toString() : null;

    if (precisionArrayMax != null && precisionArrayMin == null) {
      precision = new BigDecimal(precisionArrayMax).scale();
    } else if (precisionArrayMax == null && precisionArrayMin != null) {
      precision = new BigDecimal(precisionArrayMin).scale();
    } else if (precisionArrayMax != null && precisionArrayMin != null) {
      precision = Math.max(new BigDecimal(precisionArrayMin).scale(), new BigDecimal(precisionArrayMax).scale());
    }
    return precision;
  }
}
