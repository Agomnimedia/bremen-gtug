package de.tut.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import de.tut.api.Accent;
import de.tut.api.Language;
import de.tut.lang.English;
import de.tut.lang.German;

public class MyModule extends AbstractModule {

  private final String accent;

  public MyModule(String accent) {
    this.accent = accent;
  }

  @Override
  protected void configure() {
    this.bind(Language.class).annotatedWith(Names.named("lang1"))
        .to(English.class);
    this.bind(Language.class).annotatedWith(Names.named("lang2"))
        .to(German.class);
    this.bind(String.class).annotatedWith(Names.named("access"))
        .toInstance(this.accent);
    this.bind(Accent.class).toProvider(AccentProvider.class);
  }
}
