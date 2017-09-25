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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URL;

/** Configuration properties for ektorp
 *
 * @author Paul-Christian Volkmer
 * @since 0.1
 */
@ConfigurationProperties(prefix = "spring.ektorp")
public class EktorpProperties {

    private ClientProperties client = new ClientProperties();

    private String defaultDatabase = "db";

    public ClientProperties getClient() {
        return client;
    }

    public void setClient(ClientProperties clientProperties) {
        this.client = clientProperties;
    }

    public String getDefaultDatabase() {
        return defaultDatabase;
    }

    public void setDefaultDatabase(String defaultDatabase) {
        this.defaultDatabase = defaultDatabase;
    }

    public static class ClientProperties {

        private URL url;

        private String username;

        private String password;

        private int maxConnections = 20;

        private int connectionTimeout = 1000;

        private int socketTimeout = 10000;

        private boolean chaching = true;

        private int maxCacheEntries = 1000;

        private int maxObjectSizeBytes = 8192;

        public URL getUrl() {
            return url;
        }

        public void setUrl(URL url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getMaxConnections() {
            return maxConnections;
        }

        public void setMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getSocketTimeout() {
            return socketTimeout;
        }

        public void setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
        }

        public boolean isChaching() {
            return chaching;
        }

        public void setChaching(boolean chaching) {
            this.chaching = chaching;
        }

        public int getMaxCacheEntries() {
            return maxCacheEntries;
        }

        public void setMaxCacheEntries(int maxCacheEntries) {
            this.maxCacheEntries = maxCacheEntries;
        }

        public int getMaxObjectSizeBytes() {
            return maxObjectSizeBytes;
        }

        public void setMaxObjectSizeBytes(int maxObjectSizeBytes) {
            this.maxObjectSizeBytes = maxObjectSizeBytes;
        }

    }

}
