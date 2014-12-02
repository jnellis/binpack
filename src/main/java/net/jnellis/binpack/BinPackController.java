/*
 * BinPackController.java
 *
 * Created on August 20, 2006, 6:21 PM
 */

package net.jnellis.binpack;

/**
 * @author Joe Nellis
 */
public class BinPackController{
//    extends HttpServlet {
//  private ArrayList<PackingPolicy> packingPolicies = new ArrayList<>();
//  private ArrayList<PreOrderPolicy> preOrderPolicies = new ArrayList<>();
//
//  private static final double defaultStockLength = 204;
//  private static final String defaultLengths = "76, 109, 76, 80, 12, 29, 12," +
//      " 56, 134, 87, 134, 56, 45, 42, 143, 145, 183, 76, 86, 99, 34, " +
//      "46,56,18,20";
//
//
//  /**
//   * Processes requests for both HTTP <code>GET</code> and
//   * <code>POST</code> methods.
//   *
//   * @param request  servlet request
//   * @param response servlet response
//   */
//  protected void processRequest(HttpServletRequest request,
//                                HttpServletResponse response)
//      throws ServletException, IOException {
//    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher
//        ("/welcome.jsp");
//    String binpack = request.getParameter("binpack");
//    PackInput packInput = validateInput(request);
//    if (binpack == null) {
//      dispatcher.forward(request, response);
//      return;
//    }
//    BinPacker binPacker = new BinPacker();
//    binPacker.setPackInput(packInput);
//    binPacker.pack();
//    PackResults packResults = binPacker.getPackResults();
//    request.setAttribute("packResults", packResults);
//    dispatcher.forward(request, response);
//  }
//
//  /**
//   * This takes any input from a form request and places it in a PackInput
//   * bean.
//   * Empty fields are filled with default values.
//   *
//   * @param request The request object to pull form data from if it exists.
//   * @return Returns a PackInput bean ready for inclusion into the request
//   * scope for jsps
//   */
//  public PackInput validateInput(HttpServletRequest request) {
//    // create a new packInput object
//    PackInput packInput = new PackInput();
//    // assert that init picked up some polices that we can use
//    assert (packingPolicies.size() > 0) : "No PackingPolicies found.";
//    assert (preOrderPolicies.size() > 0) : "No PreOrderPolices found.";
//    // set some defaults.
//    packInput.setStockLength(defaultStockLength);
//    packInput.setLengths(defaultLengths);
//    packInput.setPackingPolicy(packingPolicies.get(0));
//    packInput.setPreOrderPolicy(preOrderPolicies.get(0));
//
//    // convert stocklength string to double
//    try {
//      String val = request.getParameter("stockLength");
//      if (val != null) {
//        double d = Double.parseDouble(val);
//        // crash prevention
//        d = (d > 1000 || d < 10) ? defaultStockLength : d;
//        packInput.setStockLength(d);
//      }
//    } catch (NumberFormatException ex) {
//      ex.printStackTrace();
//    }
//    // set string of lengths to bin pack.
//    String lengths = request.getParameter("lengths");
//    if (lengths != null) {
//      packInput.setLengths(lengths);
//    }
//
//    // assign the packing policy
//    String packpol = request.getParameter("packingPolicy");
//    for (PackingPolicy p : packingPolicies) {
//      if (p.getName().equals(packpol)) {
//        packInput.setPackingPolicy(p);
//        break;
//      }
//    }
//
//    // assign the preOrderPolicy
//    String preordpol = request.getParameter("preOrderPolicy");
//    for (PreOrderPolicy p : preOrderPolicies) {
//      if (p.getName().equals(preordpol)) {
//        packInput.setPreOrderPolicy(p);
//        break;
//      }
//    }
//
//    // throw this all into request scope so it can be used by jsps
//    request.setAttribute("packingPolicies", packingPolicies);
//    request.setAttribute("preOrderPolicies", preOrderPolicies);
//    request.setAttribute("packInput", packInput);
//    return packInput;
//  }
//
//  public void init() {
//    String[] packingPolicyNames = getInitParameter("packingPolicies").split
//        ("[,\\s]+");
//    for (String name : packingPolicyNames) {
//      if (!"".equals(name)) {
//        PackingPolicy p = PackingPolicyFactory.getInstanceByClassName(name);
//        packingPolicies.add(p);
//      }
//    }
//    String[] preOrderPolicyNames = getInitParameter("preOrderPolicies").split
//        ("[,\\s]+");
//    for (String name : preOrderPolicyNames) {
//      if (!"".equals(name)) {
//        PreOrderPolicy p = PreOrderPolicyFactory.getInstanceByClassName(name);
//        preOrderPolicies.add(p);
//      }
//    }
//
//  }
//
//
//  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click
//  // on the + sign on the left to edit the code.">
//
//  /**
//   * Handles the HTTP <code>GET</code> method.
//   *
//   * @param request  servlet request
//   * @param response servlet response
//   */
//  protected void doGet(HttpServletRequest request, HttpServletResponse response)
//      throws ServletException, IOException {
//    processRequest(request, response);
//  }
//
//  /**
//   * Handles the HTTP <code>POST</code> method.
//   *
//   * @param request  servlet request
//   * @param response servlet response
//   */
//  protected void doPost(HttpServletRequest request,
//                        HttpServletResponse response)
//      throws ServletException, IOException {
//    processRequest(request, response);
//  }
//
//  /**
//   * Returns a short description of the servlet.
//   */
//  public String getServletInfo() {
//    return "Short description";
//  }
  // </editor-fold>
}
