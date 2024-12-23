package com.emailotp.service;

import com.emailotp.Responce.Response;
import com.emailotp.model.EmailResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public final class TestAccountMandateServiceImplV {
  private AccountMandateServiceImpl accountMandateServiceImpl;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    accountMandateServiceImpl = new AccountMandateServiceImpl();
  }

  @Test
  public void testMethodSavingsAccountMandate() throws Exception {
    // Test candidate method [SavingsAccountMandate] [290,1] - took 22ms
    HashMap<String, Object> request = objectMapper.readValue("{\"uname\":\"as400user\",\"pword\":\"passOTP#400\",\"date\":\"20241009\",\"existingAcc\":\"15000306916\",\"cif\":\"0012719641\",\"title\":\"Mr\",\"initials\":\"'S P\",\"lastName\":\"Kumara\",\"nameDenotedInitials\":\"Saliya Padma\",\"nic\":\"199614609999\",\"dob\":\"1997304     \",\"martialStatus\":\"2\",\"address1\":\"123 Main St\",\"address2\":\"123 Main St\",\"address3\":\"123 Main St\",\"city\":\"Colombo 02\",\"telephone\":\"788888800    \",\"maidenName\":\"\",\"emailCustomer\":\"piumiw@cbctechsol.com\",\"accounntNo\":\"'8017063971'\",\"productNo\":\"169\",\"productDesc\":\"E-VibeYouth Savings Statement Account\",\"nickname\":\"ss\",\"accOpenningdate\":\"20241003\",\"branchEmail\":\"piumiw@cbctechsol.com\",\"ccBranchEmail\":\"piumiw@cbctechsol.com\",\"subjectFlag\":\"wait\",\"purposeOfAccount\":\"\"}", new TypeReference<HashMap<String, Object>>(){});
    EmailResponse emailResponse = accountMandateServiceImpl.SavingsAccountMandate(request);
    Response emailResponseExpected = objectMapper.readValue(
            "{\"status\":\"004\",\"message\":\"Invalid Product code.\"}",
            Response.class
    );

    // Debugging information
    System.out.println("Actual Response: " + emailResponse);
    System.out.println("Expected Response: " + emailResponseExpected);

    Assert.assertNotNull(emailResponse);
    Assert.assertNotNull(emailResponseExpected);
    Assert.assertEquals(emailResponseExpected.getStatus(), emailResponse.getStatus());


  }
}
