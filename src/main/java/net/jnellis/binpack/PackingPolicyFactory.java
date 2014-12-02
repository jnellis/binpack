/*
 * PackingPolicyFactory.java
 *
 * Created on August 20, 2006, 8:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.jnellis.binpack;

import net.jnellis.binpack.packing.PackingPolicy;

/**
 *
 * @author Joe Nellis
 */
public class PackingPolicyFactory {
    /** Creates a new instance of PackingPolicyFactory */
    public PackingPolicyFactory() {
    }
    
    public static PackingPolicy getInstanceByClassName(String classname){
        Class packingPolicyClass = null;
        try {
            packingPolicyClass = Class.forName(classname);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        PackingPolicy policy = null;
        try {
            policy = (PackingPolicy)( packingPolicyClass.newInstance());
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
      return policy;
    }
    
}
