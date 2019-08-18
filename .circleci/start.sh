#!/bin/bash

nohup java -jar booktracker.jar > book.log 2> book.err &
echo $! > PID
