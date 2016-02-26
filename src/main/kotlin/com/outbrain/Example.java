package com.outbrain;


public class Example {


  public static void main(String[] args) {

    AmplifyApiClient api = new AmplifyApiClient("42");
                          // /marketers/{id}.get
    Marketer marketer = api.marketers().id("id").get();

    System.out.println(marketer);
  }
}
