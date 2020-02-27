package it.artmistech.pathfinder.interfaces;

import java.util.List;

public interface GetConfig {
    String configString(String path);
    List<String> configStringList(String path);
    int configInt(String path);
    double configDouble(String path);
    boolean configBoolean(String path);
    boolean isSet(String path);
}
