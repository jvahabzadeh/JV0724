# JV0724
Programming Demo JV

- The magic works as follows:
  - Use `RentalRequest.createRequest(String toolCode, int rentalDays, int DiscountPercent, LocalDate checkoutDate)` to create a RentalRequest object, which encapsulates the four items of a checkout.  It will throw an exception if there is any problem with the incoming data.
  - Pass the RentalRequest object as a parameter into `RentalAgreementService.checkout(RentalRequest rentalRequest)`, which will return a RentalAgreement object.
  - The RentalAgreement object can simply be printed with `System.out.println`, which will result in a formatted output.