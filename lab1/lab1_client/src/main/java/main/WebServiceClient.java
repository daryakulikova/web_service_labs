package main;

import generated.Person;
import generated.PersonService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class WebServiceClient {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/PersonService?wsdl");
        PersonService personService = new PersonService(url);
        String position = "HR";
        String surname = "Ivanov";
        List<Person> persons = personService.getPersonWebServicePort().getPersons(null, surname, null, null, position);
        for (Person person : persons) {
            System.out.println("name: " + person.getName() +
                    ", surname: " + person.getSurname() +
                    ", age: " + person.getAge() +
                    ", sex: " + person.getSex() +
                    ", position: " + person.getPosition());
        }
        System.out.println("Total persons: " + persons.size());
    }
}
