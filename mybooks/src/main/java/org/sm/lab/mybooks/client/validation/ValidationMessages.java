package org.sm.lab.mybooks.client.validation;

/**
 * Custom validation messages. Interface to represent the constants contained in
 * resource bundle:
 * 'com/google/gwt/sample/validation/client/ValidationMessages.properties'
 * TODO move this to the root package so client and server can share
 * the same properties files.
 */
public interface ValidationMessages extends
    com.google.gwt.i18n.client.ConstantsWithLookup {

  @DefaultStringValue("Name must be at least {min} characters long.")
  @Key("name_size_validation_message")
  String name_size_validation_message();
  
  @DefaultStringValue("Code must be at least {min} characters long.")
  @Key("code_size_validation_message")
  String code_size_validation_message();

  @DefaultStringValue("Title must be at least {min} characters long.")
  @Key("title_size_validation_message")
  String title_size_validation_message();

  @DefaultStringValue("Username must be at least {min} characters long.")
  @Key("username_size_validation_message")
  String username_size_validation_message();

  @DefaultStringValue("Password must be at least {min} characters long.")
  @Key("password_size_validation_message")
  String password_size_validation_message();

  
  
}
