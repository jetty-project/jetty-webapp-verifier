// ========================================================================
// Copyright (c) Webtide LLC
// ------------------------------------------------------------------------
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// and Apache License v2.0 which accompanies this distribution.
//
// The Eclipse Public License is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// The Apache License v2.0 is available at
// http://www.apache.org/licenses/LICENSE-2.0.txt
//
// You may elect to redistribute this code under either of these licenses.
// ========================================================================
package org.mortbay.jetty.webapp.verifier.rules;

import junit.framework.TestCase;

import org.junit.Test;
import org.mortbay.jetty.webapp.verifier.RuleAssert;

public class ServletSupportLevelRuleTest extends TestCase
{
    @Test
    public void testServlet23Rule() throws Exception
    {
        RuleAssert.assertIntegration("servlet_level_2.3");
    }

    @Test
    public void testServlet24Rule() throws Exception
    {
        RuleAssert.assertIntegration("servlet_level_2.4");
    }

    @Test
    public void testServlet25Rule() throws Exception
    {
        RuleAssert.assertIntegration("servlet_level_2.5");
    }

    @Test
    public void testServletMixed23n24Rule() throws Exception
    {
        RuleAssert.assertIntegration("servlet_level_mixed_2.3_2.4");
    }

    @Test
    public void testServletMixed23n25Rule() throws Exception
    {
        RuleAssert.assertIntegration("servlet_level_mixed_2.3_2.5");
    }
}
