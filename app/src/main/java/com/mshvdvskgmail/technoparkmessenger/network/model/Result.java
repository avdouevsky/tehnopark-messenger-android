package com.mshvdvskgmail.technoparkmessenger.network.model;

import java.util.Map;

/**
 * Created by andrey on 23.01.2017.
 */

public class Result<T> {
    public int status;
    public T data;
    public Token token;
    public String error;
    public Map<String, String> profiler;
}
