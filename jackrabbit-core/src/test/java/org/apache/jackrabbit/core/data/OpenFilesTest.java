/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.core.data;

import java.io.ByteArrayInputStream;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.ValueFactory;

import org.apache.jackrabbit.test.AbstractJCRTest;

/**
 * Test if getting the value from a binary property opens the file.
 */
public class OpenFilesTest extends AbstractJCRTest {
    
    /**
     * Test opening a large number of streams.
     */
    public void testStreams() throws RepositoryException {
        Session session = getHelper().getReadWriteSession();
        try {
            Node test = session.getRootNode().addNode("test");
            ValueFactory vf = session.getValueFactory();
            test.setProperty("data", vf.createBinary(new ByteArrayInputStream(new byte[10 * 1024])));
            session.save();
            for (int i = 0; i < 10000; i++) {
                test.getProperty("data").getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertFalse(e.getMessage(), true);
        } finally {
            session.logout();
        }
    }

}