package io.quarkus.infinispan.client.deployment.devservices;

import java.util.*;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigItem;

@ConfigGroup
public class InfinispanDevServicesConfig {

    /**
     * If DevServices has been explicitly enabled or disabled. DevServices is generally enabled
     * by default, unless there is an existing configuration present.
     * <p>
     * When DevServices is enabled Quarkus will attempt to automatically configure and start
     * a database when running in Dev or Test mode and when Docker is running.
     */
    @ConfigItem(defaultValue = "true")
    public boolean enabled;

    /**
     * Optional fixed port the dev service will listen to.
     * <p>
     * If not defined, the port will be chosen randomly.
     */
    @ConfigItem
    public OptionalInt port;

    /**
     * Indicates if the Infinispan server managed by Quarkus Dev Services is shared.
     * When shared, Quarkus looks for running containers using label-based service discovery.
     * If a matching container is found, it is used, and so a second one is not started.
     * Otherwise, Dev Services for Infinispan starts a new container.
     * <p>
     * The discovery uses the {@code quarkus-dev-service-infinispan} label.
     * The value is configured using the {@code service-name} property.
     * <p>
     * Container sharing is only used in dev mode.
     */
    @ConfigItem(defaultValue = "true")
    public boolean shared;

    /**
     * The value of the {@code quarkus-dev-service-infinispan} label attached to the started container.
     * This property is used when {@code shared} is set to {@code true}.
     * In this case, before starting a container, Dev Services for Infinispan looks for a container with the
     * {@code quarkus-dev-service-infinispan} label
     * set to the configured value. If found, it will use this container instead of starting a new one. Otherwise, it
     * starts a new container with the {@code quarkus-dev-service-infinispan} label set to the specified value.
     * <p>
     * This property is used when you need multiple shared Infinispan servers.
     */
    @ConfigItem(defaultValue = "infinispan")
    public String serviceName;

    /**
     * List of the artifacts to automatically download and add to the Infinispan server libraries.
     * <p>
     * For example a Maven coordinate
     * (org.postgresql:postgresql:42.3.1) or a dependency location url.
     * <p>
     * If an invalid value is passed, the Infinispan server will throw an error when trying to start.
     */
    @ConfigItem
    public Optional<List<String>> artifacts;

    /**
     * List of the caches to be created and their associated cache templates.
     * <p>
     * To configure a cache, you would use one of the enum values from {@code org.infinispan.client.hotrod.DefaultTemplate}
     * <p>
     * quarkus.infinispan-client.devservices.caches.cache1=DIST_SYNC
     * quarkus.infinispan-client.devservices.caches.cache2=REPL_SYNC
     * <p>
     * If an invalid cache template is passed, the Infinispan server will throw an error when trying to start.
     */
    @ConfigItem
    public Map<String, String> caches;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        InfinispanDevServicesConfig that = (InfinispanDevServicesConfig) o;
        return enabled == that.enabled &&
                Objects.equals(port, that.port) &&
                Objects.equals(shared, that.shared) &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(artifacts, this.artifacts) &&
                Objects.equals(caches, this.caches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enabled, port, shared, serviceName, artifacts, caches);
    }
}
