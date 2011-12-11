package de.tut.lang;

import de.tut.api.Language;

public class English implements Language {

  @Override
  public String sayHello() {
    return "Hello";
  }

  @Override
  public String sayBye() {
    return "bye";
  }

}
