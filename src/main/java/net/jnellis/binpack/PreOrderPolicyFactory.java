/*
 * PreOrderPolicyFactory.java
 *
 * Created on August 26, 2006, 10:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package  net.jnellis.binpack;

import net.jnellis.binpack.preorder.PreOrderPolicy;

/**
 * @author Joe Nellis
 */
public class PreOrderPolicyFactory {

  /**
   * Creates a new instance of PreOrderPolicyFactory
   */
  public PreOrderPolicyFactory() {
  }

  public static PreOrderPolicy getInstanceByClassName(String classname) {
    Class preOrderPolicyClass = null;
    try {
      preOrderPolicyClass = Class.forName(classname);
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    PreOrderPolicy policy = null;
    try {
      policy = (PreOrderPolicy) (preOrderPolicyClass.newInstance());
    } catch (InstantiationException | IllegalAccessException ex) {
      ex.printStackTrace();
    }
    return policy;
  }
}
