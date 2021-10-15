#!/bin/sh

compile_and_run() {
  javac -d bin --source-path src src/server/main.java && java --class-path bin Server &
}

compile_and_run

while inotifywait -r -e create,modify,moved_from,delete src; do
  pkill java
  compile_and_run
done
