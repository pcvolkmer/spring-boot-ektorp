/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.pcvolkmer.spring.ektorp;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * {@link EnableAutoConfiguration Auto-Configuration} for Couchbase.
 *
 * @author Paul-Christian Volkmer
 * @since 0.1
 */
@Configuration
@EnableConfigurationProperties(EktorpProperties.class)
public class EktorpAutoConfiguration {

    private EktorpProperties ektorpProperties;

    public EktorpAutoConfiguration(final EktorpProperties ektorpProperties) {
        this.ektorpProperties = ektorpProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpClient httpClient() {
        Assert.notNull(ektorpProperties.getClient().getUrl(), "CouchDB URL must not be null");
        Assert.hasLength(ektorpProperties.getDefaultDatabase(), "Default database name must not be empty");

        return new StdHttpClient.Builder()
                .url(ektorpProperties.getClient().getUrl())
                .username(ektorpProperties.getClient().getUsername())
                .password(ektorpProperties.getClient().getPassword())
                .maxConnections(ektorpProperties.getClient().getMaxConnections())
                .connectionTimeout(ektorpProperties.getClient().getConnectionTimeout())
                .socketTimeout(ektorpProperties.getClient().getSocketTimeout())
                .caching(ektorpProperties.getClient().isChaching())
                .maxCacheEntries(ektorpProperties.getClient().getMaxCacheEntries())
                .maxObjectSizeBytes(ektorpProperties.getClient().getMaxObjectSizeBytes())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public CouchDbInstance couchDbInstance() {
        return new StdCouchDbInstance(httpClient());
    }

    @Bean
    @ConditionalOnMissingBean
    public CouchDbConnector couchDbConnector() {
        return new StdCouchDbConnector(ektorpProperties.getDefaultDatabase(), couchDbInstance());
    }

}
