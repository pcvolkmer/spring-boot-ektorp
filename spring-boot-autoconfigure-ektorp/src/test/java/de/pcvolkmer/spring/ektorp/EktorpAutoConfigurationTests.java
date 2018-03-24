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
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Paul-Christian Volkmer
 */
public class EktorpAutoConfigurationTests {

    private AnnotationConfigApplicationContext context;

    @After
    public void tearDown() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void testShouldCreateHttpClient() {
        load("spring.ektorp.client.url=http://localhost:5984");
        HttpClient httpClient = this.context.getBean(HttpClient.class);
        assertNotNull(httpClient);
    }

    @Test
    public void testShouldCreateCouchDbInstance() {
        load("spring.ektorp.client.url=http://localhost:5984");
        CouchDbInstance instance = this.context.getBean(CouchDbInstance.class);
        assertNotNull(instance);
    }

    @Test
    public void testShouldCreateCouchDbConnector() {
        load("spring.ektorp.client.url=http://localhost:5984");
        CouchDbConnector connector = this.context.getBean(CouchDbConnector.class);
        assertNotNull(connector);
    }

    @Test
    public void testShouldThrowExceptionOnMissingClientUrl() throws Exception {
        try {
            load();
            this.context.getBean(CouchDbInstance.class);
        } catch (Exception e) {
            Throwable rootCause = e.getCause().getCause();
            assertNotNull(rootCause);
            assertEquals(IllegalArgumentException.class, rootCause.getClass());
            assertEquals("CouchDB URL must not be null", rootCause.getMessage());
        }
    }

    @Test
    public void testShouldThrowExceptionOnEmptyDefaultDatabaseName() throws Exception {
        try {
            load(
                    "spring.ektorp.client.url=http://localhost:5984",
                    "spring.ektorp.defaultDatabase="
            );
            this.context.getBean(CouchDbInstance.class);
        } catch (Exception e) {
            Throwable rootCause = e.getCause().getCause();
            assertNotNull(rootCause);
            assertEquals(IllegalArgumentException.class, rootCause.getClass());
            assertEquals("Default database name must not be empty", rootCause.getMessage());
        }
    }

    private void load(String... environment) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        EnvironmentTestUtils.addEnvironment(applicationContext, environment);
        applicationContext.register(EktorpAutoConfiguration.class);
        applicationContext.refresh();
        this.context = applicationContext;
    }

}
