package com.mkl.mkltest.streamer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Streamer {
	String value();
}
