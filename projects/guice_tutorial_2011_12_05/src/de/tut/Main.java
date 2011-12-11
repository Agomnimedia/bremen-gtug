package de.tut;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.tut.guice.MyModule;

public class Main {

  /**
   * @param args
   */
  public static void main(String[] args) {

    String firstArgument = "";
    if (args.length > 0) {
      firstArgument = args[0];
    }

    Injector injector = Guice.createInjector(new MyModule(firstArgument));
    Interpreter inter = injector.getInstance(Interpreter.class);
    System.out.println(inter.interpretHello());

  }

}
