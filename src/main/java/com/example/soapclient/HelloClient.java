package com.example.soapclient;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.example.soapclient.Service.*;



// JAX-WS  rt.jar@Java8
//https://www.codejava.net/java-ee/web-services/java-web-services-binary-data-transfer-example-base64-encoding

@Component
public class HelloClient {

    @PostConstruct
    public void init() {
        try {
            URL url = new URL("http://192.168.51.112:8080/LigerMessageWeb?wsdl");
            QName qQNAME1 = new QName("http://tempuri.org", "LigerMessageWebService");
            LigerMessageWeb_Service service = new LigerMessageWeb_Service(url, qQNAME1);
            QName qQNAME2 = new QName("http://tempuri.org", "LigerMessageWebPort");
            LigerMessageWeb ligerMessageWeb = service.getLigerMessageWeb(qQNAME2);
            System.out.println(ligerMessageWeb.sayHello("wadasadamu"));

//            // downloads another file
//            String fileName = "camera.png";
//            String filePath = "D:\\tmp\\DownloadClient\\" + fileName;
//            byte[] fileBytes = hello.download(fileName);
//            try (FileOutputStream fos = new FileOutputStream(filePath);
//                 BufferedOutputStream outputStream = new BufferedOutputStream(fos);) {
//                outputStream.write(fileBytes);
//                outputStream.close();
//                System.out.println("File downloaded: " + filePath);
//            } catch (IOException ex) {
//                System.err.println(ex);
//            }

            byte[] fileBytes2 = ligerMessageWeb.getFixListRangeData();
            try (
                    ByteArrayInputStream output = new ByteArrayInputStream(fileBytes2);
                    ObjectInputStream obj = new ObjectInputStream(output);
            ) {
                Object result = obj.readObject();
                if(result instanceof String[]){
                    String[] array = (String[])result;
                    List<String> list = Arrays.asList(array);
                    list.forEach(System.out::println);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SOAPException_Exception e) {
            throw new RuntimeException(e);
        }

    }
}
