package com.orangehrm.utils;

import java.util.Optional;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v124.network.Network;
import org.openqa.selenium.devtools.v124.network.model.ConnectionType;

public class NetworkUtils {
  private static final Logger log = LoggerUtils.getLogger(NetworkUtils.class);

  public static void applyNetworkProfile(WebDriver driver) {
    String profile = ConfigReader.getProperty("network.profile", "").toLowerCase();
    if (profile.isEmpty() || profile.equals("default")) {
      return;
    }

    if (!(driver instanceof HasDevTools)) {
      log.warn(
          "Network throttling is only supported on Chromium-based local drivers (Chrome/Edge).");
      return;
    }

    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

    log.info("Applying network profile: {}", profile);

    switch (profile) {
      case "offline":
        devTools.send(
            Network.emulateNetworkConditions(
                true,
                0,
                0,
                0,
                Optional.of(ConnectionType.NONE),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        break;
      case "slow_3g":
        devTools.send(
            Network.emulateNetworkConditions(
                false,
                100, // latency
                400000, // download throughput
                400000, // upload throughput
                Optional.of(ConnectionType.CELLULAR3G),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        break;
      case "fast_3g":
        devTools.send(
            Network.emulateNetworkConditions(
                false,
                40, // latency
                1600000, // download throughput
                750000, // upload throughput
                Optional.of(ConnectionType.CELLULAR3G),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));
        break;
      default:
        log.warn("Unknown network profile: {}. No throttling applied.", profile);
    }
  }
}
