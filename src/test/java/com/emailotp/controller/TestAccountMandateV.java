package com.emailotp.controller;

import static io.unlogged.UnloggedTestUtils.*;
import static org.mockito.ArgumentMatchers.*;

import com.emailotp.model.EmailResponse;
import com.emailotp.service.AccountMandateService;
import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public final class TestAccountMandateV {
  private AccountMandate accountMandate;

  private AccountMandateService accountMandateService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setup() throws Exception {
    accountMandateService = Mockito.mock(AccountMandateService.class);
    accountMandate = new AccountMandate();
    accountMandate = new AccountMandate();
    injectField(accountMandate, "accountMandateService", accountMandateService);
  }

  @Test
  public void testMethodSavingsAccountMandate() throws Exception {
    // 
    EmailResponse SavingsAccountMandateResult = objectMapper.readValue("{\"status\": \"000\", \"message\": \"Success\"}", EmailResponse.class);
    Mockito.when(accountMandateService.SavingsAccountMandate(any(HashMap.class))).thenReturn(SavingsAccountMandateResult);
    // 
    HashMap<String, Object> request1 = objectMapper.readValue("{\"uname\":\"as400user\",\"pword\":\"passOTP#400\",\"date\":\"20241009\",\"existingAcc\":\"15000306916\",\"cif\":\"0012719641\",\"title\":\"Mr\",\"initials\":\"'S P\",\"lastName\":\"Kumara\",\"nameDenotedInitials\":\"Saliya Padma\",\"nic\":\"199614609999\",\"dob\":\"1997304     \",\"martialStatus\":\"2\",\"address1\":\"123 Main St\",\"address2\":\"123 Main St\",\"address3\":\"123 Main St\",\"city\":\"Colombo 02\",\"telephone\":\"788888800    \",\"maidenName\":\"\",\"emailCustomer\":\"piumiw@cbctechsol.com\",\"accounntNo\":\"'8017063971'\",\"productNo\":\"266\",\"productDesc\":\"E-Savings Statement Account\",\"nickname\":\"ss\",\"accOpenningdate\":\"20241003\",\"branchEmail\":\"piumiw@cbctechsol.com\",\"ccBranchEmail\":\"piumiw@cbctechsol.com\",\"subjectFlag\":\"wait\",\"purposeOfAccount\":\"\"}", new TypeReference<HashMap<String, Object>>(){});
    ResponseEntity<EmailResponse> responseEntity = accountMandate.SavingsAccountMandate(request1);

    EmailResponse responseEntityExpected = objectMapper.readValue("{\"status\":\"000\",\"message\":\"Success\"}", EmailResponse.class);

    // Debugging information
    System.out.println("Actual Response: " + responseEntity.getBody() );
    System.out.println("Expected Response: " + responseEntityExpected);

    Assert.assertNotNull( responseEntity.getBody() );
    Assert.assertNotNull( responseEntityExpected );
    Assert.assertEquals( responseEntityExpected.getStatus(), responseEntity.getBody().getStatus() );

  }
}
