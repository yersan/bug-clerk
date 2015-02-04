/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.jbossset.bugclerk.bugzilla;

import static org.jboss.jbossset.bugclerk.utils.StringUtils.ITEM_ID_SEPARATOR;
import static org.jboss.jbossset.bugclerk.utils.StringUtils.twoEOLs;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.jbossset.bugclerk.Violation;
public class ReportViolationToBzEngine {

    private final String header;
    private final String footer;

    public ReportViolationToBzEngine(String header, String footer) {
        this.header = header;
        this.footer = footer;
    }

    public void reportViolationToBZ(Map<Integer, List<Violation>> violationByBugId) {
        BugzillaClient bugzillaClient = new BugzillaClient();
        for (Entry<Integer, List<Violation>> bugViolation : violationByBugId.entrySet())
            bugzillaClient.addPrivateCommentTo(bugViolation.getKey(),
                    messageBody(bugViolation.getValue(), new StringBuffer(header)).append(footer).toString());
    }

    private StringBuffer messageBody(List<Violation> violations, StringBuffer text) {
        int violationId = 1;
        for (Violation violation : violations)
            text.append(violationId++).append(ITEM_ID_SEPARATOR).append(violation.getMessage()).append(twoEOLs());
        return text;
    }
}