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

import java.io.File;

import org.mortbay.jetty.webapp.verifier.AbstractRule;

/**
 * Perform a JSP pre-compile to ensure all JSPs are valid.
 */
public class JspPrecompileRule extends AbstractRule
{
    @Override
    public String getDescription()
    {
        return "Perform JSP precompile to ensure all jsps are valid";
    }

    @Override
    public String getName()
    {
        return "jsp-precompile";
    }

    @Override
    public void visitWebappStart(String path, File dir)
    {
        // TODO: implement rule.
        error(path,"Rule [" + getClass().getName() + "] not yet implemented");
    }
}
