package de.tut.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import de.tut.accent.BremenAccent;
import de.tut.accent.StuttgartAccent;
import de.tut.api.Accent;

public class AccentProvider implements Provider<Accent> {

  @Inject
  @Named("access")
  private String accent;

  @Inject
  Provider<BremenAccent> bac;

  @Inject
  Provider<StuttgartAccent> sac;

  @Override
  public Accent get() {
    switch (this.accent) {
    case "BremenAccent":
      return this.bac.get();
    case "StuttgartAccent":
      return this.sac.get();
    default:
      return this.bac.get();
    }
  }

}
