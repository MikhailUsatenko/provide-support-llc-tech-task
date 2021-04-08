package com.usatenko.demo.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeUtils {

  public static OffsetDateTime currentTime() {
    return OffsetDateTime.now(ZoneOffset.UTC);
  }
   }
