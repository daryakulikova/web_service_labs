
package generated;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PersonWebService", targetNamespace = "http://service/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PersonWebService {


    /**
     * 
     * @param surname
     * @param sex
     * @param name
     * @param position
     * @param age
     * @return
     *     returns java.util.List<generated.Person>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getPersons", targetNamespace = "http://service/", className = "generated.GetPersons")
    @ResponseWrapper(localName = "getPersonsResponse", targetNamespace = "http://service/", className = "generated.GetPersonsResponse")
    @Action(input = "http://service/PersonWebService/getPersonsRequest", output = "http://service/PersonWebService/getPersonsResponse")
    public List<Person> getPersons(
        @WebParam(name = "name", targetNamespace = "")
        String name,
        @WebParam(name = "surname", targetNamespace = "")
        String surname,
        @WebParam(name = "age", targetNamespace = "")
        Integer age,
        @WebParam(name = "sex", targetNamespace = "")
        String sex,
        @WebParam(name = "position", targetNamespace = "")
        String position);

}
