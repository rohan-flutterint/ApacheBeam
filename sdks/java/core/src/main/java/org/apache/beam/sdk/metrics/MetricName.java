/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.sdk.metrics;

import static org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import java.io.Serializable;
import java.util.Map;
import org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.base.Strings;
import org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.collect.ImmutableMap;

/**
 * The name of a metric consists of a {@link #getNamespace} and a {@link #getName}. The {@link
 * #getNamespace} allows grouping related metrics together and also prevents collisions between
 * multiple metrics with the same name.
 */
@SuppressWarnings("AutoValueFinalMethods")
@AutoValue
public abstract class MetricName implements Serializable {

  /** The namespace associated with this metric. */
  public abstract String getNamespace();

  /** The name of this metric. */
  public abstract String getName();

  /** Associated labels for the metric. */
  public abstract Map<String, String> getLabels();

  @Override
  @Memoized
  public String toString() {
    return getNamespace() + ":" + getName();
  }

  public static MetricName named(String namespace, String name) {
    checkArgument(!Strings.isNullOrEmpty(namespace), "Metric namespace must be non-empty");
    checkArgument(!Strings.isNullOrEmpty(name), "Metric name must be non-empty");
    return new AutoValue_MetricName(namespace, name, ImmutableMap.of());
  }

  public static MetricName named(String namespace, String name, Map<String, String> labels) {
    checkArgument(!Strings.isNullOrEmpty(namespace), "Metric namespace must be non-empty");
    checkArgument(!Strings.isNullOrEmpty(name), "Metric name must be non-empty");
    return new AutoValue_MetricName(namespace, name, ImmutableMap.copyOf(labels));
  }

  public static MetricName named(Class<?> namespace, String name) {
    checkArgument(namespace != null, "Metric namespace must be non-null");
    checkArgument(!Strings.isNullOrEmpty(name), "Metric name must be non-empty");
    return new AutoValue_MetricName(namespace.getName(), name, ImmutableMap.of());
  }

  public static MetricName named(Class<?> namespace, String name, Map<String, String> labels) {
    checkArgument(namespace != null, "Metric namespace must be non-null");
    checkArgument(!Strings.isNullOrEmpty(name), "Metric name must be non-empty");
    return new AutoValue_MetricName(namespace.getName(), name, ImmutableMap.copyOf(labels));
  }
}
