/*
 * Naturheilpraxis
 * Copyright (c) 2023 Falko Schumann <falko.schumann@muspellheim.de>
 */

package de.muspellheim.naturheilpraxis.ui.util;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventEmitter<T> {
  private final CopyOnWriteArrayList<Consumer<T>> listeners = new CopyOnWriteArrayList<>();

  public EventEmitter() {}

  public void addListener(Consumer<T> listener) {
    listeners.add(listener);
  }

  public void removeListener(Consumer<T> listener) {
    listeners.remove(listener);
  }

  public void emit(T event) {
    listeners.forEach(l -> l.accept(event));
  }
}
