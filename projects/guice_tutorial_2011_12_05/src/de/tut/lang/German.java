package de.tut.lang;

import com.google.inject.Inject;

import de.tut.api.Accent;
import de.tut.api.Language;

public class German implements Language {

  private final Accent accent;

  @Inject
  public German(Accent accent) {
    this.accent = accent;
  }

  @Override
  public String sayHello() {
    return this.accent.sayHelloWithAccent();
  }

  @Override
  public String sayBye() {
    return "Tschüss";
  }

}
