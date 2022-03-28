# Java Microservices with spring boot and spring cloud

This example is a very basic implementation of microservices architecture built with Spring Boot, Spring Cloud, Eureka Server.

The repository consists of three services:

1. The Eureka Server (aka `discovery-service`) that scans for the attached clients (the other microservices) that can easily communicate with one another.
2. The `queue-service` with JPA, H2 for database persistence, that uses `Cipher` for decrypting the transaction data we receive from `api-gateway` and
3. The `api-gateway` which is responsible for the main interaction, i.e. it will be used to encrypt and send transactions data / retrieve the decrypted data from the `queue-service`.

## Running the project

The project basically consists of three different micro services which are individual spring boot projects which depend on each other (linked using `eureka`). 

1. Create a security key using `AES` algorithm and save it to a file called `security.key`, the same file needs to exist in `queue-service` and `api-gateway`. The contents of the file have to be string.

   ```java
   import java.io.FileWriter;
   import java.util.Base64;
   import javax.crypto.KeyGenerator;   
   import javax.crypto.SecretKey;
   
   public class SomeClass {
       public static void main (String[] args) {
           KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
           keyGenerator.init(128); // block size is 128bits
           SecretKey secretKey = keyGenerator.generateKey();
           byte[] encoded = secretKey.getEncoded();
           String output = Base64.getEncoder().withoutPadding().encodeToString(encoded);
           FileWriter fw = new FileWriter("security.key");
           fw.append(output);
           fw.flush();
           fw.close();
       }
   }
   ```

   

2. Go to each folder and run `mvn spring-boot:run`

