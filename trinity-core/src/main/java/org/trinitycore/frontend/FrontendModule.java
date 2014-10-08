package org.trinitycore.frontend;

import com.google.inject.AbstractModule;
import org.trinitycore.frontend.controllers.FrontendControllerModule;

public class FrontendModule extends AbstractModule {
    protected void configure() {
        install(new FrontendControllerModule());
    }
}
