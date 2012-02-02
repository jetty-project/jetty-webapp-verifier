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
package org.mortbay.jetty.webapp.verifier;

import java.io.File;

public class WebappVerifierCLI
{
    private static final String ARG_RULESET = "ruleset";
    private static final String ARG_WEBARCHIVE = "webarchive";

    public static void main(String[] args)
    {
        File webarchiveFile = null;
        File rulesetFile = null;

        for (String arg : args)
        {
            if (arg.startsWith("--" + ARG_WEBARCHIVE + "="))
            {
                webarchiveFile = new File(arg.substring(3 + ARG_WEBARCHIVE.length()));
                continue;
            }
            if (arg.startsWith("--" + ARG_RULESET + "="))
            {
                rulesetFile = new File(arg.substring(3 + ARG_RULESET.length()));
                continue;
            }
        }

        boolean argsValid = true;

        if (webarchiveFile == null)
        {
            argsValid = false;
            System.err.println("ERROR: no " + ARG_WEBARCHIVE + " provided.");
        }
        else if (!webarchiveFile.exists())
        {
            argsValid = false;
            System.err.println("ERROR: File [" + ARG_WEBARCHIVE + "] Not Found: " + webarchiveFile.getAbsolutePath());
        }

        if (rulesetFile == null)
        {
            argsValid = false;
            System.err.println("ERROR: no " + ARG_RULESET + " provided.");
        }
        else if (!rulesetFile.exists())
        {
            argsValid = false;
            System.err.println("ERROR: File [" + ARG_RULESET + "] Not Found: " + rulesetFile.getAbsolutePath());
        }

        if (!argsValid)
        {
            System.out.println("Usage: java -jar jetty-webapp-verifier.jar --" + ARG_WEBARCHIVE + "=<path_to_archive> " + "--" + ARG_RULESET
                    + "=<path_to_ruleset>");
            System.exit(-1);
        }

        try
        {
            // load ruleset
            System.out.println("Loading Ruleset: " + rulesetFile);
            RuleSet ruleset = RuleSet.load(rulesetFile);

            // load webarchive
            System.out.println("Loading Web Archive: " + webarchiveFile);
            WebappVerifier verifier = ruleset.createWebappVerifier(webarchiveFile.toURI());
            // verifier.setWorkDir(tempDir);

            // submit for verification
            System.out.println("Analyzing ...");
            verifier.visitAll();

            // show report
            for (Violation violation : verifier.getViolations())
            {
                System.out.println(violation);
            }
            System.out.println("Done.");
        }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
}
