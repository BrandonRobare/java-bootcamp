package com.northstar.crm;

import com.northstar.crm.controller.CustomerController;
import com.northstar.crm.dto.CustomerRequest;
import com.northstar.crm.dto.CustomerResponse;
import com.northstar.crm.repository.CustomerRepository;
import com.northstar.crm.repository.InMemoryCustomerRepository;
import com.northstar.crm.service.CustomerService;

// This class plays the CLIENT (the browser / Postman / the Angular app).
// Run it and watch a request fall through every layer and come back.
public class App {

    public static void main(String[] args) {

        // Building the layers by hand, bottom to top.
        // Spring does exactly this for you later -- that is all @Autowired means.
        CustomerRepository repository = new InMemoryCustomerRepository();
        CustomerService service = new CustomerService(repository);
        CustomerController controller = new CustomerController(service);

        // ---------- HAPPY PATH ----------
        System.out.println("--- create a customer ---");

        // Step 1-2: client sends data, it arrives as a request DTO
        CustomerRequest request = new CustomerRequest("Amina Khan", "amina@example.test");

        // Step 3-8: controller -> service -> repository -> database and all the way back
        CustomerResponse created = controller.create(request);
        System.out.println("      [out]  " + created);

        // ---------- READ IT BACK ----------
        System.out.println("--- fetch it again ---");
        System.out.println("      [out]  " + controller.getById(created.getCustomerId()));

        // ---------- FAILURE PATH ----------
        // A blank name never reaches the repository. The service stops it.
        System.out.println("--- create with a blank name ---");
        try {
            controller.create(new CustomerRequest("", "nobody@example.test"));
        } catch (IllegalArgumentException e) {
            // Later, one @RestControllerAdvice class catches this for every endpoint
            // and turns it into a 400 response. For now, print it.
            System.out.println("      [out]  rejected: " + e.getMessage());
        }
    }
}
