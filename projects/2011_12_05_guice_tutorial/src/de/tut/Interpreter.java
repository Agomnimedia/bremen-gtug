package de.tut;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.tut.api.Language;

public class Interpreter {

  private Language lang1;

  private Language lang2;

  @Inject
  public void setLang1(@Named("lang1") Language lang) {
    this.lang1 = lang;
  }

  @Inject
  public void setLang2(@Named("lang2") Language lang) {
    this.lang2 = lang;
  }

  public String interpretHello() {
    return this.lang1.sayHello() + " - " + this.lang2.sayHello();
  }
}
