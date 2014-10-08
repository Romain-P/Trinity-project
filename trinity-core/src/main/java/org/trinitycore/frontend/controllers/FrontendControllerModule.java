package org.trinitycore.frontend.controllers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.trinity.api.controllers.Controller;
import org.trinity.api.controllers.PullableController;

public class FrontendControllerModule extends AbstractModule {
    protected void configure() {
        Multibinder<PullableController> controllers = Multibinder.newSetBinder(binder(), PullableController.class);
        controllers.addBinding().toInstance(ClientController.create());
    }
}
