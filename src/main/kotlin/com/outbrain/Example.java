package com.outbrain;


public class Example {


  public static void main(String[] args) {

    AmplifyApiClient api = new AmplifyApiClient();
                          // /marketers/{id}.get
    Marketer marketer = api.marketers().id("id").get();

    System.out.println(marketer.getName());
  }
}
