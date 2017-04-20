package com.mshvdvskgmail.technoparkmessenger.helpers;

/**
 * Created by andrey on 20.04.2017.
 */
public interface ICommand<T> {
    public void exec(T data);
}
