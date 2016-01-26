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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONCollection implements Serializable {

  // members
  private static final long serialVersionUID = -6627431426960774864L;
  private transient JSONArray value;

  // getters & setters
  /**
   * @param atomicSchema
   * @param jsonCollection
   */
  public void setValue(JSONObject atomicSchema, JSONArray jsonCollection)//
      throws Exceptions.UniqueValueUnknownScopeTypeException,//
      Exceptions.MalformedSearchableNodeException, //
      Exceptions.UniqueValueExpectationFailedException,//
      Exceptions.UnknownDataTypeException, //
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException, //
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException, //
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {

    if (jsonCollection != null) {
      this.value = new JSONArray();

      JSONObject object;
      for (int i = 0; i < jsonCollection.length(); i++) {
        object = jsonCollection.getJSONObject(i);

        if (atomicSchema != null) {
          JSON.enforceSchema(atomicSchema, object);
        }

        JSON.checkTypeConstraints(object);

        JSON.enforceUniqueness(object, jsonCollection);

        this.value.put(object);
      }
    }
  }

  /**
   * @param atomicSchema
   * @param jsonCollection
   */
  public void setValue(String atomicSchema, String jsonCollection)//
      throws Exceptions.UniqueValueUnknownScopeTypeException,//
      Exceptions.MalformedSearchableNodeException, //
      Exceptions.UniqueValueExpectationFailedException,//
      Exceptions.UnknownDataTypeException, //
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException, //
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException, //
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {

    if (atomicSchema != null && !atomicSchema.isEmpty()
        && jsonCollection != null && !jsonCollection.isEmpty()) {
      setValue(new JSONObject(atomicSchema), new JSONArray(jsonCollection));
    } else if ((atomicSchema == null || atomicSchema.isEmpty())
        && jsonCollection != null && !jsonCollection.isEmpty()) {
      setValue(null, new JSONArray(jsonCollection));
    }

  }

  /**
   * @param value
   */
  @JsonDeserialize(using = JSONArrayDeserializer.class)
  public void setValue(JSONArray value)//
      throws Exceptions.UniqueValueUnknownScopeTypeException,//
      Exceptions.MalformedSearchableNodeException, //
      Exceptions.UniqueValueExpectationFailedException,//
      Exceptions.UnknownDataTypeException,//
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException, //
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException, //
      Exceptions.MaxValueExpectationFailedException {

    this.value = value;
  }

  @JsonSerialize(using = JSONArraySerializer.class)
  public JSONArray getValue() throws JSONException {

    return this.value;
  }

  // public API
  public JSONCollection() {
    this.value = new JSONArray();
  }

  /**
   * Creates a new instance with no schema enforcement.
   * 
   * @param jsonCollection
   *          String with a valid json array
   */
  public JSONCollection(String jsonCollection) //
      throws Exceptions.UniqueValueUnknownScopeTypeException,//
      Exceptions.MalformedSearchableNodeException, //
      Exceptions.UniqueValueExpectationFailedException,//
      Exceptions.UnknownDataTypeException,//
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException, //
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException, //
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {
    setValue(null, jsonCollection);
  }

  /**
   * Creates a new instance.<br/>
   * If <code>atomicSchema</code> is null, the schema is not enforced.<br/>
   * If any problem occurs when enforcing the <code>atomicSchema</code> on
   * <code>jsonCollection</code>, the instance value defaults to the
   * <code>atomicSchema</code>.<br/>
   * 
   * @param atomicSchema
   *          String with a valid json object. Value will be enforced as a
   *          schema to the collection.
   * @param jsonCollection
   *          String with a valid json array. A new collection of json objects
   */
  public JSONCollection(String atomicSchema, String jsonCollection)//
      throws Exceptions.UniqueValueUnknownScopeTypeException,//
      Exceptions.MalformedSearchableNodeException, //
      Exceptions.UniqueValueExpectationFailedException,//
      Exceptions.UnknownDataTypeException, //
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException, //
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException, //
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {
    setValue(atomicSchema, jsonCollection);
  }

  /**
   * * Creates a new instance.<br/>
   * If <code>atomicSchema</code> is null, the schema is not enforced.<br/>
   * If any problem occurs when enforcing the <code>atomicSchema</code> on
   * <code>jsonCollection</code>, the instance value defaults to the
   * <code>atomicSchema</code>.<br/>
   * 
   * @param atomicSchema
   *          Value will be enforced as a schema to the collection
   * @param jsonCollection
   *          A new collection of json objects
   */
  public JSONCollection(JSONObject atomicSchema, JSONArray jsonCollection)//
      throws Exceptions.UniqueValueUnknownScopeTypeException,//
      Exceptions.MalformedSearchableNodeException, //
      Exceptions.UniqueValueExpectationFailedException,//
      Exceptions.UnknownDataTypeException, //
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException, //
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException, //
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {
    setValue(atomicSchema, jsonCollection);
  }

  @Override
  public String toString() {
    return this.value.toString();
  }

  @Override
  public boolean equals(Object obj) {
    return this.toString().equals(obj.toString());
  }

  public JSONCollection appendRemovedItems(JSONCollection oldValues)//
      throws Exceptions.MalformedStrongTypedNodeException {

    JSONCollection removedItems = new JSONCollection();
    boolean found;

    for (int i = 0; i < oldValues.value.length(); i++) {
      found = false;
      for (int j = 0; j < this.value.length(); j++) {
        // trying to find the old item in the new items collection
        if (JSON.getId(oldValues.value.getJSONObject(i)).equals(
            JSON.getId(this.value.getJSONObject(j)))) {
          found = true;
          break;
        }
      }

      // if its not there, it was removed and must be appended in the new
      // collection
      if (!found) {
        removedItems.value
            .put(JSON.setDeleted(oldValues.value.getJSONObject(i)));

      }
    }

    for (int i = 0; i < removedItems.value.length(); i++) {
      this.value.put(removedItems.value.get(i));
    }

    return this;
  }

  public JSONCollection purgeInvalidItems(JSONCollection oldValues)//
      throws Exceptions.MalformedStrongTypedNodeException {

    List<Integer> invalidItems = new ArrayList<>();
    boolean found;

    for (int i = 0; i < this.value.length(); i++) {

      found = false;
      for (int j = 0; j < oldValues.value.length(); j++) {
        // trying to find an existing item in the old collection with a matching
        // id
        if (!JSON.isPropertyAvailable(this.value.getJSONObject(i),
            Constants.NODE_ID_PROPERTY)
            || (JSON.isPropertyAvailable(this.value.getJSONObject(i),
                Constants.NODE_ID_PROPERTY) && (JSON.getId(oldValues.value
                .getJSONObject(j)).equals(JSON.getId(this.value
                .getJSONObject(i)))))) {
          found = true;
          break;
        }
      }

      // if its not there, it's invalid and i will need the current object
      // invalid item id
      if (!found) {
        invalidItems.add(i);
      }
    }

    for (int i = 0; i < invalidItems.size(); i++) {
      this.value.remove(invalidItems.get(i));
    }

    return this;
  }

  public JSONCollection generateNewItemsIdentifier() {
    JSONCollection items = this
        .findItemsWithoutProperty(Constants.NODE_ID_PROPERTY);

    for (int i = 0; i < items.value.length(); i++) {
      items.value.getJSONObject(i).put(Constants.NODE_ID_PROPERTY,
          UUID.randomUUID());
    }

    return this;
  }

  /**
   * 
   * @param objectId
   * @return boolean
   */
  public boolean exists(String objectId)//
      throws Exceptions.MalformedSearchableNodeException {
    JSONObject currentObject;
    String currentObjectId = null;

    for (int i = 0; i < this.value.length(); i++) {
      currentObject = this.value.getJSONObject(i);

      try {
        if (currentObject.has(Constants.NODE_ID_PROPERTY)) {
          currentObjectId = JSON.getId(currentObject);
        }
      } catch (Exceptions.MalformedStrongTypedNodeException e) {
        throw new Exceptions.MalformedSearchableNodeException(
            "Searchable Object's <" + Constants.NODE_ID_PROPERTY
                + "> property value must be valid");
      }

      if (currentObjectId == null || currentObjectId.isEmpty()) {
        throw new Exceptions.MalformedSearchableNodeException(
            "Searchable Object's <" + Constants.NODE_ID_PROPERTY
                + "> property value must be valid");
      }

      if (objectId.equals(currentObjectId)) {
        return true;
      }
    }

    return false;
  }

  public JSONCollection enforceActive()//
      throws Exceptions.InvalidDataTypeException,//
      Exceptions.MalformedStrongTypedNodeException {
    return enforceActive(null);
  }

  /**
   * Updates current value (JSONArray) suppressing inactive objects.
   * 
   * @param ignores
   *          items where restriction is not enforced
   * @return
   * @throws Exceptions.InvalidDataTypeException
   * @throws Exceptions.MalformedStrongTypedNodeException
   */
  public JSONCollection enforceActive(String... ignores)//
      throws Exceptions.InvalidDataTypeException,//
      Exceptions.MalformedStrongTypedNodeException {

    JSONArray result = new JSONArray();

    for (int i = 0; i < this.value.length(); i++) {
      {
        if ((ignores != null && Arrays.asList(ignores).contains(
            JSON.getId(this.value.getJSONObject(i))))
            || JSON.isActive(this.value.getJSONObject(i))) {
          result.put(this.value.getJSONObject(i));
        }
      }

    }

    this.value = result;
    return this;
  }

  public JSONCollection enforceNotDeleted(String... ignores)//
      throws Exceptions.InvalidDataTypeException,//
      Exceptions.MalformedStrongTypedNodeException {
    JSONArray result = new JSONArray();

    for (int i = 0; i < this.value.length(); i++) {
      if ((ignores != null && Arrays.asList(ignores).contains(
          JSON.getId(this.value.getJSONObject(i))))
          || !JSON.isDeleted(this.value.getJSONObject(i))) {
        result.put(this.value.getJSONObject(i));
      }
    }

    this.value = result;
    return this;
  }

  /**
   * Updates current value (JSONArray) suppressing invisible objects.
   * 
   * @return
   * @throws Exceptions.InvalidDataTypeException
   */
  public JSONCollection enforceVisible(String... ignores)//
      throws Exceptions.InvalidDataTypeException,//
      Exceptions.MalformedStrongTypedNodeException {

    JSONArray result = new JSONArray();

    for (int i = 0; i < this.value.length(); i++) {
      if ((ignores != null && Arrays.asList(ignores).contains(
          JSON.getId(this.value.getJSONObject(i))))
          || JSON.isVisible(this.value.getJSONObject(i))) {
        result.put(this.value.getJSONObject(i));
      }
    }

    this.value = result;
    return this;
  }

  /**
   * Enforce schema in the current collection
   * 
   * @param atomicSchema
   *          The schema to be enforced in the current collection
   * @return JSONCollection
   */
  public JSONCollection enforceSchema(JSONObject atomicSchema)//
      throws Exceptions.UnknownDataTypeException, //
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException,//
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException,//
      Exceptions.MaxValueExpectationFailedException {

    try {
      for (int i = 0; i < this.value.length(); i++) {
        JSON.enforceSchema(atomicSchema, this.value.getJSONObject(i));
      }
    } catch (JSONException e) {
      // default to an array of one element with an empty schema
      this.value = new JSONArray();
      this.value.put(atomicSchema);
    }

    return this;
  }

  /**
   * Check constraints in the current collection
   */
  public JSONCollection checkConstraints()//
      throws Exceptions.UnknownDataTypeException,//
      Exceptions.MandatoryValueExpectationFailedException,//
      Exceptions.InvalidDataTypeException, //
      Exceptions.MalformedStrongTypedNodeException,//
      Exceptions.NumberPrecisionExpectationFailedException,//
      Exceptions.MinValueExpectationFailedException, //
      Exceptions.MaxValueExpectationFailedException,
      Exceptions.InvalidEmptyObjectException {

    for (int i = 0; i < this.value.length(); i++) {
      JSON.checkTypeConstraints(this.value.getJSONObject(i));
    }
    return this;
  }

  // internal API
  JSONCollection findItemsWithoutProperty(String property) {
    JSONCollection result = new JSONCollection();

    JSONObject object;
    for (int i = 0; i < this.value.length(); i++) {
      object = this.value.getJSONObject(i);
      if (!JSON.isPropertyAvailable(object, property)) {
        result.value.put(object);
      }
    }

    return result;
  }

  private void writeObject(ObjectOutputStream oos)//
      throws IOException {
    oos.defaultWriteObject();
    oos.writeObject(this.value.toString());
  }

  private void readObject(ObjectInputStream ois)//
      throws ClassNotFoundException, //
      IOException, //
      JSONException {
    ois.defaultReadObject();
    this.value = new JSONArray((String) ois.readObject());
  }
}
