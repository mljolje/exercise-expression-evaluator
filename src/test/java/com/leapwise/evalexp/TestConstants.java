package com.leapwise.evalexp;

public class TestConstants {
    public static final String EXPRESSION_DEFAULT_VALUE = "(customer.firstName == \"JOHN\" && customer.salary < 100) OR (customer.address != null && customer.address.city == \"Washington\")";
    public static final String EXPRESSION_UPDATED_VALUE = "(customer.firstName == \"JOHN\" && customer.salary < 100.5) AND (customer.address != null && customer.address.city == \"Washington\")";
    public static final String EXPRESSION_COMPLEX_VALUE = "(customer.firstName == \"JOHN\" && (customer.salary <= 50 OR customer.salary <= 60.56 OR customer.salary > 200 || customer.unknown > 134 || customer.unknown < 134 || customer.unknown >= 134 || customer.unknown <= 134 OR customer.salary >= 300 OR customer.salary == 99)) AND (customer.address != null && customer.address.city == \"Washington\")";
    public static final String EXPRESSION_NEGATIVE_VALUE = "(customer.firstName == \"JOHN\" && customer.salary < 50) AND (customer.address != null && customer.address.city == \"Washington\")";
    public static final String JSON_OBJECT = """
            {
              "customer":
              {
                "firstName": "JOHN",
                "lastName": "DOE",
                "address":
                {
                  "city": "Chicago",
                  "zipCode": 1234,
                  "street": "56th",
                  "houseNumber": 2345
                },
                "salary": 99,
                "type": "BUSINESS"
              }
            }""";
}
