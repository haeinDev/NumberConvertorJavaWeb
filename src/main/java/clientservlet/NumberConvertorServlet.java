/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientservlet;

import com.dataaccess.webservicesserver.NumberConversion;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
/**
 *
 * @author Haein.Kim
 */
@WebServlet(name = "NumberConvertorServlet", urlPatterns = { "/NumberConvertorServlet"})
public class NumberConvertorServlet extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/www.dataaccess.com/webservicesserver/NumberConversion.wso.wsdl")
    private NumberConversion service;
    private Object java;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    //insert the code number to dollar 

    TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

                public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }

                public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }
        }
    };

    // Install the all-trusting trust manager
    try {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    } catch (Exception e) {
    }

    // Now you can access an https URL without having the certificate in the truststore
    try {
        URL url = new URL("https://hostname/index.html");
    } catch (MalformedURLException e) {
    }

    //number to the words
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {

        RequestDispatcher rdObj = null;
        rdObj = request.getRequestDispatcher("/index.jsp");
        String txtNumber = request.getParameter("inputValue");
        
        //calling the method and save the reuslt into each variable 
        java.math.BigInteger ubiNum = new java.math.BigInteger(txtNumber);
        java.math.BigDecimal dNum = new java.math.BigDecimal(txtNumber);
        try{
        //Process result
       com.dataaccess.webservicesserver.NumberConversionSoapType port = service.getNumberConversionSoap();
            String result;
            java.lang.String result2;
            if(txtNumber.equals("0")){
            result = "The result of converting the number to words is "+port.numberToWords(ubiNum);
            result2= "The result of converting the number to a dollar is 0 dollar";
            
            }else{
            result = "The result of converting the number to words is "+port.numberToWords(ubiNum);
            result2 = "The result of converting the number to a dollar is "+port.numberToDollars(dNum);
            }
            //send the result value into result element in index.jsp
            request.setAttribute("result", result);
            request.setAttribute("result2", result2);
            //publish the request
            rdObj.include(request, response);
        }catch (IOException | ServletException e) {
            out.println("Exception: " + e);
        }

    }catch (Exception e) {
//            out.println("Exception: " + e);
        }
}

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {

}

/**
 * Handles the HTTP <code>POST</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    processRequest(request, response);

}


}




