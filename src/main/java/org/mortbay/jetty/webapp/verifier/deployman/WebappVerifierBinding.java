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
package org.mortbay.jetty.webapp.verifier.deployman;

import java.io.File;
import java.net.URI;
import java.util.Collection;

import org.eclipse.jetty.deploy.App;
import org.eclipse.jetty.deploy.AppLifeCycle;
import org.eclipse.jetty.deploy.graph.Node;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.webapp.WebAppContext;
import org.mortbay.jetty.webapp.verifier.RuleSet;
import org.mortbay.jetty.webapp.verifier.Severity;
import org.mortbay.jetty.webapp.verifier.Violation;
import org.mortbay.jetty.webapp.verifier.WebappVerifier;

/**
 * Deploy Binding for the Webapp Verifier.
 * 
 * Will verify the webapp being deployed, to ensure that it satisfies the ruleset configured.
 */
public class WebappVerifierBinding implements AppLifeCycle.Binding
{
    private static final Logger LOG = Log.getLogger(WebappVerifierBinding.class);

    private String rulesetPath;

    @Override
    public String[] getBindingTargets()
    {
        return new String[]
        { "deploying" };
    }

    public String getRulesetPath()
    {
        return rulesetPath;
    }

    @Override
    public void processBinding(Node node, App app) throws Exception
    {
        ContextHandler context = app.getContextHandler();

        if (!(context instanceof WebAppContext))
        {
            LOG.info("Webapp Verifier - " + app.getContextHandler().getClass().getName() + " is not an instance of " + WebAppContext.class.getName());
            return;
        }

        WebAppContext wac = (WebAppContext)context;
        URI warURI = new URI(wac.getWar());

        File rulesetFile = new File(this.rulesetPath);

        RuleSet ruleset = RuleSet.load(rulesetFile);

        WebappVerifier verifier = ruleset.createWebappVerifier(warURI);

        verifier.visitAll();

        Collection<Violation> violations = verifier.getViolations();
        if (violations.size() <= 0)
        {
            // Nothing to report.
            LOG.info("Webapp Verifier - All Rules Passed - No Violations");
            return;
        }

        boolean haltWebapp = false;
        LOG.info("Webapp Verifier Found " + violations.size() + " violations.");

        for (Violation violation : violations)
        {
            if (violation.getSeverity() == Severity.ERROR)
            {
                haltWebapp = true;
            }

            LOG.info(violation.toString());
        }

        if (haltWebapp)
        {
            throw new IllegalStateException("Webapp Failed Webapp Verification");
        }
    }

    public void setRulesetPath(String rulesetPath)
    {
        this.rulesetPath = rulesetPath;
    }
}
