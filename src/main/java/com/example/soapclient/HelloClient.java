package com.example.soapclient;

import org.springframework.stereotype.Component;
import swa.Download;
import swa.Hello;
import swa.Hello_Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


//https://www.codejava.net/java-ee/web-services/java-web-services-binary-data-transfer-example-base64-encoding

@Component
public class HelloClient {

    @PostConstruct
    public void init() {
        try {
            URL url = new URL("http://localhost:8080/hello?wsdl");
            QName qQNAME1 = new QName("http://tempuri.org", "HelloService");
//            QName qQNAME1 = new QName("http://soapservice2.example.com/", "HelloService");
            Hello_Service service = new Hello_Service(url, qQNAME1);
            QName qQNAME2 = new QName("http://tempuri.org", "HelloPort");
            Hello hello = service.getHello(qQNAME2);
            System.out.println(hello.sayHello("wadasadamu"));

            // downloads another file
            String fileName = "camera.png";
            String filePath = "D:\\tmp\\DownloadClient\\" + fileName;
            byte[] fileBytes = hello.download(fileName);
            try (FileOutputStream fos = new FileOutputStream(filePath);
                 BufferedOutputStream outputStream = new BufferedOutputStream(fos);) {
                outputStream.write(fileBytes);
                outputStream.close();
                System.out.println("File downloaded: " + filePath);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }
}
