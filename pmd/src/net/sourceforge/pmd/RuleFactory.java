/*
 * User: tom
 * Date: Jun 10, 2002
 * Time: 11:27:51 AM
 */
package net.sourceforge.pmd;

import net.sourceforge.pmd.rules.*;
import net.sourceforge.pmd.rules.design.UseSingletonRule;

import java.util.*;

public class RuleFactory {

    public static final String ALL = "all";
    public static final String GENERAL = "general";
    public static final String COUGAAR = "cougaar";
    public static final String DESIGN = "design";

    private static Set ruleSets = new HashSet();

    public RuleFactory() {
        ruleSets.add(ALL);
        ruleSets.add(GENERAL);
        ruleSets.add(COUGAAR);
        ruleSets.add(DESIGN);
    }

    public String getConcatenatedRuleSetList() {
        StringBuffer buf = new StringBuffer();
        for (Iterator i = ruleSets.iterator(); i.hasNext();) {
            if (buf.length() != 0) {
                buf.append(",");
            }
            buf.append(i.next());
        }
        return buf.toString();
    }

    public boolean containsRuleSet(String ruleSet) {
        return ruleSets.contains(ruleSet);
    }

    public List createRules(String ruleSetType) {
        if (ruleSetType.equals(ALL)) {
            return createAllRules();
        } else if (ruleSetType.equals(GENERAL)) {
            return createGeneralRules();
        } else if (ruleSetType.equals(DESIGN)) {
            return createDesignRules();
        } else if (ruleSetType.equals(COUGAAR)) {
            return createCougaarRules();
        }
        throw new RuntimeException("Unknown rule set type " + ruleSetType);
    }

    private List createAllRules() {
        List list = new ArrayList();
        list.addAll(createCougaarRules());
        list.addAll(createGeneralRules());
        list.addAll(createDesignRules());
        return list;
    }

    private List createCougaarRules() {
        List list = new ArrayList();
        list.add(new DontCreateThreadsRule());
        list.add(new DontCreateTimersRule());
        list.add(new SystemOutRule());
        list.add(new SystemPropsRule());
        return list;
    }

    private List createGeneralRules() {
        List list = new ArrayList();
        list.add(new EmptyCatchBlockRule());
        list.add(new EmptyIfStmtRule());
        list.add(new EmptyWhileStmtRule());
        list.add(new UnnecessaryConversionTemporaryRule());
        list.add(new UnusedLocalVariableRule());
        list.add(new UnusedPrivateInstanceVariableRule());
        list.add(new IfElseStmtsMustUseBracesRule());
        list.addAll(createDesignRules());
        return list;
    }

    private List createDesignRules() {
        List list = new ArrayList();
        list.add(new UseSingletonRule());
        return list;
    }
}
