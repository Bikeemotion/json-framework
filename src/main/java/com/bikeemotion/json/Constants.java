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

public class Constants {
  public static final String NODE_ID_PROPERTY = "id";
  public static final String NODE_ACTIVE_PROPERTY = "active";
  public static final String NODE_VISIBLE_PROPERTY = "visible";
  public static final String NODE_STATE_PROPERTY = "state";
  public static final Integer NODE_STATE_PROPERTY_INSTANCE_DELETED = -1;
  public static final Integer NODE_STATE_PROPERTY_INSTANCE_NORMAL = 0;

  public static final String NODE_CUSTOM_PROPERTY_TYPE_TAG = "type";
  public static final String NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_NUMBER = "number";
  public static final String NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_STRING = "string";
  public static final String NODE_CUSTOM_PROPERTY_TYPE_TAG_INSTANCE_SIMPLE_STRING = "sstring";

  public static final String NODE_CUSTOM_PROPERTY_VALUE_TAG = "value";
  public static final String NODE_CUSTOM_PROPERTY_MANDATORY_TAG = "mandatory";
  public static final String NODE_CUSTOM_PROPERTY_UNIQUE_TAG = "unique";
  public static final String NODE_CUSTOM_PROPERTY_UNIQUE_TAG_VALUE_TAG = "value";
  public static final String NODE_CUSTOM_PROPERTY_UNIQUE_TAG_SCOPE_TAG = "scope";
  public static final String NODE_CUSTOM_PROPERTY_UNIQUE_TAG_SCOPE_TAG_VALUE_TAG = "field";
  public static final String NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG = "mode";
  public static final String NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG_INSTANCE_DISTINCT = "distinct";
  public static final String NODE_CUSTOM_PROPERTY_UNIQUE_TAG_MODE_TAG_INSTANCE_PKEY = "pkey";

  public static final String NODE_CUSTOM_PROPERTY_MIN_TAG = "min";
  public static final String NODE_CUSTOM_PROPERTY_MAX_TAG = "max";
}
