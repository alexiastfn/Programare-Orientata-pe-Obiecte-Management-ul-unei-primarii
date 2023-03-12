package org.example.Users;

import org.example.Enums.RequestType;
import org.example.Exceptions.WrongTypeExeption;
import org.example.Models.Request;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.example.Utils.Methods.getAvailableRequestTypes;
import static org.example.Utils.Methods.getRequestName;

public class Person extends User {

    public Person(String name) {
        super(name);
    }

    public Person() {
        super("");
    }

    @Override
    public String writeRequest(RequestType requestType) throws WrongTypeExeption {
        ArrayList<RequestType> availableRequestTypes = getAvailableRequestTypes(new Person());

        if (!availableRequestTypes.contains(requestType))
            throw new WrongTypeExeption("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + getRequestName(requestType));

        return "Subsemnatul " + this.getName() + ", va rog " +
                "sa-mi aprobati urmatoarea " +
                "solicitare: " + getRequestName(requestType);
    }

    @Override
    public Request createRequest(RequestType requestType, Integer priority, LocalDateTime localDateTime, String fileName) throws IOException {
        Request request = null;
        try {
            String requestText = writeRequest(requestType);
            request = new Request(requestText, priority, requestType, localDateTime);

        } catch (WrongTypeExeption exeption) {
            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            bufferedWriter.write("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + getRequestName(requestType)+"\n");
            bufferedWriter.close();
            return null;
        }

        ArrayList<Request> requests = getPendingRequests();
        requests.add(request);
        setPendingRequests(requests);
        return request;
    }
}
