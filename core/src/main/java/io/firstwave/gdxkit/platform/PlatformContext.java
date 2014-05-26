package io.firstwave.gdxkit.platform;

import com.badlogic.gdx.Application;

/**
 * Created by obartley on 5/26/14.
 */
public interface PlatformContext {

    public static final String UNKNOWN = "unknown";

    public static class Capabilities {
        public String osName = UNKNOWN;
        public String osArch = UNKNOWN;
        public boolean hasTouchscreen;
    }

    public Application.ApplicationType getType();
    public Capabilities getCapabilities();

}
