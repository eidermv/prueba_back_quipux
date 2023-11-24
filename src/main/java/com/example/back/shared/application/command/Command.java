package com.example.back.shared.application.command;

public interface Command<T> {
   public T execute();
}
