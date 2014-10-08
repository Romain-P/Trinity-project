package org.trinitycore.frontend.controllers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.trinity.api.controllers.Controller;

public class FrontendControllerModule extends AbstractModule {
    protected void configure() {
        Multibinder<Controller> controllers = Multibinder.newSetBinder(binder(), Controller.class);
        controllers.addBinding().toInstance(ClientController.create());
    }
}
